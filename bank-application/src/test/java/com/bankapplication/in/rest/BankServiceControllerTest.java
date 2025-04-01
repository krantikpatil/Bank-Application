package com.bankapplication.in.rest;

import com.bankapplication.bankservice.core.domain.AccountDTO;
import com.bankapplication.bankservice.core.usecase.AccountService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BankServiceControllerTest {

    @InjectMocks
    private BankServiceControllerTest bankServiceControllerTest;

    @Mock
    AccountService accountService;

    @Test
    public void testOpenAccount_Success(){
        AccountDTO accountDTO = new AccountDTO(UUID.randomUUID(),"Rahul Patil", "1234534565", "ifsc1232343454", "Saving", "PAN12323432", "Adharr12323234", new BigDecimal(500000), false);

        when(accountService.createAccount(any(AccountDTO.class))).thenReturn("Account Created Successfully");

        given().contentType(MediaType.APPLICATION_JSON_VALUE).body(accountDTO).when().post("/banking-service/user/create-account").then().statusCode(200)
                .body(equalTo("Account Created Successfully"));

        verify(accountService, times(1)).createAccount(any(AccountDTO.class));
    }

    @Test
    public void testOpenAccount_Failure(){
        AccountDTO accountDTO = new AccountDTO(UUID.randomUUID(),"Rahul Patil", "1234534565", "ifsc1232343454", "Saving", "PAN12323432", "Adharr12323234", new BigDecimal(500000), false);

        when(accountService.createAccount(any(AccountDTO.class))).thenThrow(new RuntimeException("Account Creation Failed"));

        given().contentType(MediaType.APPLICATION_JSON_VALUE).body(accountDTO)
                .when()
                .post("/banking-service/user/create-account").then()
                .statusCode(500)
                .body(containsString("Account Creation Failed"));

        verify(accountService, times(1)).createAccount(any(AccountDTO.class));
    }
}
