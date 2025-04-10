package com.bankapplication.in.rest;

import com.bankapplication.bankservice.core.domain.AccountDTO;
import com.bankapplication.bankservice.core.domain.AmountTransferDTO;
import com.bankapplication.bankservice.core.usecase.AccountService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import static io.restassured.RestAssured.given;


@SpringBootTest
public class BankServiceControllerTest {


@Test
    public void testTransferAmount_Success(){

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(AmountTransferDTO.builder().name("Kartik Patil")
                        .fromAccount("4673044498").toAccount("1449821784")
                        .ifscCode("MEDCOMP-18432043").amount(new BigDecimal("600")).build()
                )

                .when()
                .post("/banking-service/user/transfer-amount")
                .then()
                .statusCode(201);
    }

    @Test
    public void testTransferAmount_Failure(){

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(
                        AmountTransferDTO.builder().name("Kartik Patil")
                                .fromAccount("3532874493").toAccount("3532874493")
                                .ifscCode("MEDCOMP-30694180").amount(new BigDecimal("6000")).build()

                )
                .when()
                .post("/banking-service/user/transfer-amount")
                .then()
                .statusCode(400);
    }


    @Test
    public void testOpenAccount_Success(){

        given()
                .contentType(ContentType.JSON)
                .body(AccountDTO.builder().name("Rohit Sharma").accountType("Saving")
                        .aadhaarNumber("123456289581").panNumber("JHGHU9212J").balance(new BigDecimal(57687))
                        .isLocked(false).build()
                )
                .when()
                .post("/banking-service/user/create-account")
                .then()
                .statusCode(201);

    }

    @Test
    public void testOpenAccount_Failure() {

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(
                        AccountDTO.builder().name("Rohot Sharma").accountType("Saving")
                                .aadhaarNumber("Aadhaar1234").panNumber("PAN123").balance(new BigDecimal(57687))
                                .isLocked(false).build()
                )
                .when()
                .post("/banking-service/user/create-account")
                .then()
                .statusCode(400);

    }
}
