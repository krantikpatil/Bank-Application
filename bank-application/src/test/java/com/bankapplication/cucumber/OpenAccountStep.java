package com.bankapplication.cucumber;

import com.bankapplication.bankservice.core.domain.AccountDTO;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.types.DataTable;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;


public class OpenAccountStep {

    private RequestSpecification request;
    private Response response;
    private final String BASE_URL = "http://localhost:8080/banking-service/user";


    @Given("A user wants to open bank account")
    public void aUserWantsToOpenAccount(){

        RestAssured.baseURI = BASE_URL;
        request = RestAssured.given().header("Content-Type", "application/json");
    }

    @When("They send a valid account creation request with the following data")
    public void theySendValidAccountCreationRequest(AccountDTO accountDTO){

       response =  request.body(accountDTO).post("/create-account");
    }

    @Then("The account should be created successfully")
    public void theAccountShouldBeCreatedSuccessfully(){
        System.out.println("This is the response:--");
        System.out.println(response.getBody().asString());
        Assertions.assertEquals(201, response.getStatusCode());
    }

    @And("They should receive a success response")
    public void theyShouldReceiveSuccessResponse(){

        Assertions.assertTrue(response.getBody().asString().contains("Congratulation"));
    }


    @DataTableType
    public AccountDTO accountDTO(Map<String, String> tableEntry) {
       return AccountDTO.builder().name(tableEntry.get("name")).accountType(tableEntry.get("accountType"))
                .panNumber(tableEntry.get("panNumber")).aadhaarNumber(tableEntry.get("aadhaarNumber"))
                .balance(new BigDecimal(tableEntry.get("balance"))).build();

    }

}
