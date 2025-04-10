package com.bankapplication.core.domain.usecase;

import com.bankapplication.bankservice.core.domain.Account;
import com.bankapplication.bankservice.core.domain.AccountDTO;
import com.bankapplication.bankservice.core.domain.AmountTransferDTO;
import com.bankapplication.bankservice.core.mapper.AccountMapper;
import com.bankapplication.bankservice.core.port.AccountServicePort;
import com.bankapplication.bankservice.core.usecase.AccountService;
import com.bankapplication.common.exception.AccountLimitException;
import com.bankapplication.common.exception.InsufficientBalanceException;
import com.bankapplication.common.exception.InvalidAccountNumberException;
import com.bankapplication.common.exception.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.function.StreamBridge;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    AccountServicePort accountServicePort;

    @Mock
    private StreamBridge streamBridge;

    @Mock
    private AccountMapper accountMapper;

    private AccountDTO accountDTO;

    private Account account;

    private AmountTransferDTO amountTransferDTO;

    @BeforeEach
    public void setUp(){
        accountDTO =  AccountDTO.builder().name("Kartik Patil").accountType("Saving").panNumber("JHGFS6743Y")
                .aadhaarNumber("123243567676").balance(new BigDecimal(15000)).isLocked(false).build();


        account = Account.builder().id(UUID.randomUUID()).name("Kartik Patil")
                .accountNumber(UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 10))
                        .ifscCode("MEDCOMP-"+UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 8))
                                .accountType("Saving").panNumber("JHGFS6743Y").aadhaarNumber("123243567676")
                        .balance(new BigDecimal(15000)).isLocked(false).build();


        when(accountMapper.AccountDTOToAccount(any(AccountDTO.class))).thenAnswer(i-> {
            AccountDTO dto = i.getArgument(0);
            Account account1 = new Account();
            account1.setName(dto.getName());
            account1.setPanNumber(dto.getPanNumber());
            account1.setAadhaarNumber(dto.getAadhaarNumber());
            account1.setAccountType(dto.getAccountType());
            account1.setBalance(dto.getBalance());
            return account1;
        });

    }

@Test
public void testCreateAccount_InsufficientBalanceForSaving(){
    accountDTO.setAccountType("Saving");
    accountDTO.setBalance(new BigDecimal(4000));

//    when(accountMapper.AccountDTOToAccount(accountDTO)).thenReturn(account);
    when(accountServicePort.findByPanNumber(anyString())).thenReturn(Collections.emptyList());

//    when(accountServicePort.create(any(Account.class))).thenReturn(account);

    assertThrows(InsufficientBalanceException.class, ()-> accountService.createAccount(accountDTO));
    }

    @Test
    public void testCreateAccount_InsufficientBalanceForCurrent(){

       accountDTO.setAccountType("Current");
       accountDTO.setBalance(new BigDecimal(9000));

//        when(accountMapper.AccountDTOToAccount(accountDTO)).thenReturn(account);
        when(accountServicePort.findByPanNumber(anyString())).thenReturn(Collections.emptyList());

        assertThrows(InsufficientBalanceException.class, ()-> accountService.createAccount(accountDTO));
    }

    @Test
    public void testCreateAccount_Successful(){

        accountDTO.setAccountType("Saving");
        accountDTO.setPanNumber("JHESD2530P");
        accountDTO.setBalance(new BigDecimal("15000"));


        account.setBalance(new BigDecimal("15000"));
        account.setPanNumber("JHESD2530P");

        when(accountMapper.AccountDTOToAccount(accountDTO)).thenReturn(account);

        when(accountServicePort.findByPanNumber(anyString())).thenReturn(Collections.emptyList());
        when(accountServicePort.create(any(Account.class))).thenReturn(account);

        account.setAccountNumber(UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 10));
        account.setIfscCode("MEDCOMP-"+UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 8));

        String response = accountService.createAccount(accountDTO);

        assertNotNull(response);
        assertTrue(response.contains("Congratulation"));
    }

    @Test
public void testCreateAccount_AccountLimitExceede(){

        Account existingSaving = new Account();
        existingSaving.setName("Amar Varma");

        existingSaving.setAccountType("Saving");
        existingSaving.setPanNumber("JHGFS6743Y");

        Account existingCurrent = new Account();
        existingCurrent.setName("Amar Varma");
        existingCurrent.setAccountType("Current");
        existingCurrent.setPanNumber("JHGFS6743Y");

        when(accountServicePort.findByPanNumber("JHGFS6743Y"))
                .thenReturn(Arrays.asList(existingSaving, existingCurrent));
        accountDTO.setAccountType("Saving");
        assertThrows(AccountLimitException.class, ()->accountService.createAccount(accountDTO));

    }

    @Test
public void testTransferAmount_Success(){

        Account sender = Account.builder().name("Kartik Patil").accountNumber("3532874493").accountType("Saving").ifscCode("MEDCOMP-30694123").balance(new BigDecimal(150000)).build();
        Account receiver = Account.builder().name("Rohit Sharma").accountNumber("1234567898").accountType("Saving").ifscCode("MEDCOMP-30694180").balance(new BigDecimal(50000)).build();

        amountTransferDTO = AmountTransferDTO.builder().name("Kartik Patil").fromAccount("3532874493").toAccount("1234567898")
                .ifscCode("MEDCOMP-30694180").amount(new BigDecimal(10000)).build();

        when(accountServicePort.findByAccountNumber(amountTransferDTO.getFromAccount())).thenReturn(sender);
        when(accountServicePort.findByAccountNumber(amountTransferDTO.getToAccount())).thenReturn(receiver);

        String result = accountService.transferAmount(amountTransferDTO);

        assertEquals("Amount of "+ amountTransferDTO.getAmount()+" successfully credited to account "+ amountTransferDTO.getToAccount(), result);
        assertEquals(new BigDecimal(140000), sender.getBalance());
        assertEquals(new BigDecimal(60000), receiver.getBalance());
    }

    @Test
    public void testTransferAmount_InsufficientBalance() {

        Account sender = Account.builder().name("Kartik Patil").accountNumber("3532874493").accountType("Saving").ifscCode("MEDCOMP-30694123").balance(new BigDecimal(6000)).build();
        Account receiver = Account.builder().name("Rohit Sharma").accountNumber("1234567898").accountType("Saving").ifscCode("MEDCOMP-30694180").balance(new BigDecimal(5000)).build();

        amountTransferDTO = AmountTransferDTO.builder().name("Kartik Patil").fromAccount("3532874493").toAccount("1234567898")
                .ifscCode("MEDCOMP-30694180").amount(new BigDecimal(10000)).build();

        when(accountServicePort.findByAccountNumber(amountTransferDTO.getFromAccount())).thenReturn(sender);
        when(accountServicePort.findByAccountNumber(amountTransferDTO.getToAccount())).thenReturn(receiver);

        assertThrows(InsufficientBalanceException.class, ()-> accountService.transferAmount(amountTransferDTO));

    }

    @Test
    public void testTransferAmount_NameMismatch() {
        Account sender = Account.builder().name("Kartik Patil").accountNumber("3532874493").accountType("Saving").ifscCode("MEDCOMP-30694123").balance(new BigDecimal(60000)).build();
        Account receiver = Account.builder().name("Rohit Sharma").accountNumber("1234567898").accountType("Saving").ifscCode("MEDCOMP-30694180").balance(new BigDecimal(5000)).build();

        amountTransferDTO = AmountTransferDTO.builder().name("Rahul Patil").fromAccount("3532874493").toAccount("1234567898")
                .ifscCode("MEDCOMP-30694180").amount(new BigDecimal(1000)).build();

        when(accountServicePort.findByAccountNumber(amountTransferDTO.getFromAccount())).thenReturn(sender);
        when(accountServicePort.findByAccountNumber(amountTransferDTO.getToAccount())).thenReturn(receiver);

        assertThrows(InvalidInputException.class, ()-> accountService.transferAmount(amountTransferDTO));
    }

    @Test
    public void testTransferAmount_IfscCodeMismatch() {
        Account sender = Account.builder().name("Kartik Patil").accountNumber("3532874493").accountType("Saving").ifscCode("MEDCOMP-30694123").balance(new BigDecimal(60000)).build();
        Account receiver = Account.builder().name("Rohit Sharma").accountNumber("1234567898").accountType("Saving").ifscCode("MEDCOMP-30694180").balance(new BigDecimal(5000)).build();

        amountTransferDTO = AmountTransferDTO.builder().name("Kartik Patil").fromAccount("3532874493").toAccount("1234567898")
                .ifscCode("MEDCOMP-30694190").amount(new BigDecimal(1000)).build();

        when(accountServicePort.findByAccountNumber(amountTransferDTO.getFromAccount())).thenReturn(sender);
        when(accountServicePort.findByAccountNumber(amountTransferDTO.getToAccount())).thenReturn(receiver);

        assertThrows(InvalidInputException.class, ()-> accountService.transferAmount(amountTransferDTO));
    }

    @Test
    public void testTransferAmount_SameAccountTransfer() {
        Account sender = Account.builder().name("Kartik Patil").accountNumber("3532874493").accountType("Saving").ifscCode("MEDCOMP-30694123").balance(new BigDecimal(60000)).build();
        Account receiver = Account.builder().name("Kartik Patil").accountNumber("3532874493").accountType("Saving").ifscCode("MEDCOMP-30694123").balance(new BigDecimal(60000)).build();

        amountTransferDTO = AmountTransferDTO.builder().name("Kartik Patil").fromAccount("3532874493").toAccount("3532874493")
                .ifscCode("MEDCOMP-30694123").amount(new BigDecimal(1000)).build();

        when(accountServicePort.findByAccountNumber(amountTransferDTO.getFromAccount())).thenReturn(sender);
        when(accountServicePort.findByAccountNumber(amountTransferDTO.getToAccount())).thenReturn(receiver);

        assertThrows(InvalidAccountNumberException.class, ()-> accountService.transferAmount(amountTransferDTO));
    }

}
