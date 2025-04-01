package com.bankapplication.bankservice.core.usecase;

import com.bankapplication.bankservice.core.domain.Account;
import com.bankapplication.bankservice.core.domain.AccountDTO;
import com.bankapplication.bankservice.core.domain.AmountTransferDTO;
import com.bankapplication.bankservice.core.mapper.AccountMapper;
import com.bankapplication.bankservice.core.port.AccountServicePort;
import com.bankapplication.common.exception.AccountLimitException;
import com.bankapplication.common.exception.InsufficientBalanceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Slf4j
@Service
public class AccountService {

    @Autowired
    AccountServicePort accountServicePort;

    @Autowired
    RabbitTemplate rabbitTemplate;

    LocalDateTime currentTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedTime = currentTime.format(formatter);

//    Date d  = LocalDate.now();

    @Transactional(propagation= Propagation.REQUIRED, readOnly=false)
    public String createAccount(AccountDTO accountDTO){
        Account account = AccountMapper.INSTANCE.AccountDTOToAccount(accountDTO);
       List<Account> accounts = accountServicePort.findByPanNumber(account.getPanNumber());

        if(accounts.size() < 2 && !accounts.contains(account.getAccountType())){

            System.out.println("Inside if block due to user contains account less than two.");

            if(account.getAccountType().equalsIgnoreCase("Saving") && account.getBalance().compareTo(new BigDecimal(5000)) < 0){
                System.out.println("Saving account minimum balance should be 5000");
                throw new InsufficientBalanceException("Saving account should have minimum amount 5000/-");

            }else if(account.getAccountType().equalsIgnoreCase("Current") && account.getBalance().compareTo(new BigDecimal(10000)) < 0){
                System.out.println("Current account minimum balance should be 10000");
                throw new InsufficientBalanceException("Current account should have minimum amount 10000/-");

            }else {
                Account accountCreated = accountServicePort.create(account);
                accountCreated.setAccountNumber(UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 10));
                accountCreated.setIfscCode("MEDCOMP-"+UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 8));


                log.info("Congratulation {} your {} account has successfully created with Account Number : {} and IFSC Code : {}", accountCreated.getName(), accountCreated.getAccountType(), accountCreated.getAccountNumber(), accountCreated.getIfscCode());
                return "Congratulation "+ accountCreated.getName()+ " your " + accountCreated.getAccountType() + " account has successfully created with Account Number : "+ accountCreated.getAccountNumber() + " and IFSC Code : " + accountCreated.getIfscCode();
            }

        }else{
            throw new AccountLimitException("User account already exist");
        }
    }

    public String transferAmount(AmountTransferDTO amountTransfer){

        Account sender = accountServicePort.findByAccountNumber(amountTransfer.getFromAccount());
        Account receiver = accountServicePort.findByAccountNumber(amountTransfer.getToAccount());

        if(sender.getAccountType().equalsIgnoreCase("Saving") && sender.getBalance().compareTo(new BigDecimal(5000)) < 0){
            sender.setLocked(true);
            throw new InsufficientBalanceException("In saving account amount should be more than 5000");
        }

        if(sender.getAccountType().equalsIgnoreCase("Current") && sender.getBalance().compareTo(new BigDecimal(10000)) < 0){
            sender.setLocked(true);
            throw new InsufficientBalanceException("In current account amount should be more than 10000");
        }

        sender.setBalance(sender.getBalance().subtract(amountTransfer.getAmount()));
        receiver.setBalance(receiver.getBalance().add(amountTransfer.getAmount()));


        accountServicePort.saveDetails(sender);

        accountServicePort.saveDetails(receiver);

        amountTransfer.setTransactionDate(formattedTime);

        rabbitTemplate.convertAndSend("transactionExchange", "transactionRoutingKey",amountTransfer);


        log.info("Amount transferred successfully");

        return "Amount of "+ amountTransfer.getAmount() +" successfully credited to account " +amountTransfer.getToAccount() ;
    }

}
