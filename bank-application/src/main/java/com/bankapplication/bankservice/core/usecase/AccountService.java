package com.bankapplication.bankservice.core.usecase;

import com.bankapplication.bankservice.core.domain.Account;
import com.bankapplication.bankservice.core.domain.AccountDTO;
import com.bankapplication.bankservice.core.domain.AmountTransferDTO;
import com.bankapplication.bankservice.core.mapper.AccountMapper;
import com.bankapplication.bankservice.core.port.AccountServicePort;
import com.bankapplication.common.exception.*;
import com.bankapplication.common.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
@Slf4j
@Service
public class AccountService {

    @Autowired
    private AccountServicePort accountServicePort;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private StreamBridge streamBridge;

    private final LocalDateTime currentTime = LocalDateTime.now();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final String formattedTime = currentTime.format(formatter);


    public String createAccount(AccountDTO accountDTO){

        if(!Validator.isValidPantNumber(accountDTO.getPanNumber())){
            throw new InvalidInputException("The Pan number you entered is incorrect, Please check and retry.");
        }

        if(!Validator.isValidAadhaarNumber(accountDTO.getAadhaarNumber())){
            throw new InvalidInputException("The Aadhaar number you entered is incorrect, Please check and retry.");
        }


        Account account = accountMapper.AccountDTOToAccount(accountDTO);
       List<Account> accounts = accountServicePort.findByPanNumber(account.getPanNumber());

        if(accounts.size() < 2 && accounts.stream().noneMatch((a-> a.getAccountType().equalsIgnoreCase(account.getAccountType())))){

            if(account.getAccountType().equalsIgnoreCase("Saving") && account.getBalance().compareTo(new BigDecimal(5000)) < 0){

                log.info("Saving account minimum balance should be 5000");

                throw new InsufficientBalanceException("Account Creation Failed, Saving account should have minimum amount 5000/-");

            }else if(account.getAccountType().equalsIgnoreCase("Current") && account.getBalance().compareTo(new BigDecimal(10000)) < 0){

                log.info("Current account minimum balance should be 10000");

                throw new InsufficientBalanceException("Account Creation Failed, Current account should have minimum amount 10000/-");

            }else {
                Account accountCreated = accountServicePort.create(account);
                accountCreated.setAccountNumber(UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 10));
                accountCreated.setIfscCode("MEDCOMP-"+UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 8));
                accountCreated.setLocked(false);
                log.info("Congratulation {} your {} account has successfully created with Account Number : {} and IFSC Code : {}", accountCreated.getName(), accountCreated.getAccountType(), accountCreated.getAccountNumber(), accountCreated.getIfscCode());
                return "Congratulation "+ accountCreated.getName()+ " your " + accountCreated.getAccountType() + " account has successfully created with Account Number : "+ accountCreated.getAccountNumber() + " and IFSC Code : " + accountCreated.getIfscCode();
            }

        }else{
            throw new AccountLimitException("User account already exist");
        }
    }

    @Transactional
    public String transferAmount(AmountTransferDTO amountTransfer){

        Account sender = accountServicePort.findByAccountNumber(amountTransfer.getFromAccount());
        Account receiver = accountServicePort.findByAccountNumber(amountTransfer.getToAccount());

        System.out.println(sender);
        System.out.println(receiver);

//        log.info(sender);

        if(  sender == null ||sender.getAccountNumber().isEmpty()){
            throw new AccountNotExistException("Sender Account number is invalid, Please check and retry.");
        }

        if( receiver == null || receiver.getAccountNumber() == null || receiver.getAccountNumber().isEmpty()){
            throw new AccountNotExistException("Receiver Account number is invalid, Please check and retry.");
        }

        if(sender.isLocked()){
            log.info("Account is locked you can not transfer money ");
            throw new InsufficientBalanceException("In saving account amount should be more than 5000");
        }

        if(!amountTransfer.getName().equalsIgnoreCase(sender.getName())){
            throw new InvalidInputException("Input Details Invalid, Name is not matching with " +
                    "account Number. Please check and retry. ");
        }

        if(!amountTransfer.getIfscCode().equalsIgnoreCase(receiver.getIfscCode())){
            throw new InvalidInputException("Input Details Invalid, IFSC code is not matching with account " +
                    "Number. Please check and retry. ");
        }

        if(sender.getAccountNumber().equalsIgnoreCase(receiver.getAccountNumber())){
            throw new InvalidAccountNumberException("Sorry, Can not transfer amount to the same account.");
        }

        if(sender.getBalance().compareTo(amountTransfer.getAmount()) < 0){
            throw new InsufficientBalanceException("Account has insufficient balance.");
        }



        if(sender.getAccountType().equalsIgnoreCase("Saving") && sender.getBalance()
                .compareTo(new BigDecimal(5000)) < 0){
            sender.setLocked(true);
            throw new InsufficientBalanceException("In saving account amount should be more than 5000");
        }

        if(sender.getAccountType().equalsIgnoreCase("Current") && sender
                .getBalance().compareTo(new BigDecimal(10000)) < 0){
            sender.setLocked(true);
            throw new InsufficientBalanceException("In current account amount should be more than 10000");
        }

        sender.setBalance(sender.getBalance().subtract(amountTransfer.getAmount()));
        receiver.setBalance(receiver.getBalance().add(amountTransfer.getAmount()));

        if (receiver.isLocked() && receiver.getAccountType().equalsIgnoreCase("Saving")
                && receiver.getBalance().compareTo(new BigDecimal(5000)) < 0){
            receiver.setLocked(false);
        }
        if (receiver.isLocked() && receiver.getAccountType().equalsIgnoreCase("Current")
                && receiver.getBalance().compareTo(new BigDecimal(10000)) < 0){
            receiver.setLocked(false);
        }

        accountServicePort.saveDetails(sender);

        accountServicePort.saveDetails(receiver);

        amountTransfer.setTransactionDate(formattedTime);

        System.out.println("Transfer Amount:--");

        amountTransfer.setTransactionId(UUID.randomUUID());


//        System.out.println(amountTransfer);

        streamBridge.send("transactionListener-in-0", amountTransfer);

        log.info("Amount transferred successfully");

        return "Amount of "+ amountTransfer.getAmount() +" successfully credited to account "
                +amountTransfer.getToAccount() ;
    }

}
