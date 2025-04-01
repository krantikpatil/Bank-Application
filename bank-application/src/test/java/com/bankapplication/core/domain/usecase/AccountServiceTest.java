package com.bankapplication.core.domain.usecase;

import com.bankapplication.bankservice.core.domain.Account;
import com.bankapplication.bankservice.core.domain.AccountDTO;
import com.bankapplication.bankservice.core.mapper.AccountMapper;
import com.bankapplication.bankservice.core.port.AccountServicePort;
import com.bankapplication.bankservice.core.usecase.AccountService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    AccountServicePort accountServicePort;

    @Mock
    private AccountMapper accountMapper;

    private AccountDTO accountDTO;
    private Account account;

    @BeforeEach
    public void setUp(){
      accountDTO = new AccountDTO();
      accountDTO.setName("Rahul Patil");
      accountDTO.setAccountNumber("1234567891");
      accountDTO.setIfscCode("ifsc12345678");
      accountDTO.setPanNumber("Pan123456");
      accountDTO.setAadhaarNumber("adhar12121212");
      accountDTO.setBalance(new BigDecimal(50000));
      accountDTO.setLocked(false);

        account = new Account();
        account.setName("Rahul Patil");
        account.setAccountNumber("1234567891");
        account.setIfscCode("ifsc12345678");
        account.setPanNumber("Pan123456");
        account.setAadhaarNumber("adhar12121212");
        account.setBalance(new BigDecimal(50000));
        account.setLocked(false);

    }

    @Test
    public void testCreateAccount_Successful(){
        when(accountMapper.AccountDTOToAccount(accountDTO)).thenReturn(account);
        when(accountServicePort.findByPanNumber(anyString())).thenReturn(Collections.emptyList());
        when(accountServicePort.create(any(Account.class))).thenReturn(account);

        String response = accountService.createAccount(accountDTO);

        assertNotNull(response);
        assertTrue(response.contains("Congratulation John Doe"));
        verify(accountServicePort, times(1)).create(any(Account.class));
    }

}
