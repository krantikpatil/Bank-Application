package com.bankapplication.bankservice.in.rest;

import com.bankapplication.bankservice.core.domain.AccountDTO;
import com.bankapplication.bankservice.core.domain.AmountTransferDTO;
import com.bankapplication.bankservice.core.usecase.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/banking-service/user")
@Tag(name="User Bank Account", description = "APIs for managing user banking.")
public class BankServiceController {

    @Autowired
    AccountService accountService;

        @Operation(summary= "Create a new user account", description = "Allows a user to create a bank account (Saving or Current). ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid request data."),
            @ApiResponse(responseCode = "409", description = "User already has maximum allowed accounts."),
            @ApiResponse(responseCode = "500", description = "Internal server error")

    })
    @PostMapping("/create-account")
    public ResponseEntity<String> openAccount(@RequestBody AccountDTO accountDTO){

        String accountCreated = accountService.createAccount(accountDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(accountCreated);
    }

        @Operation(summary= "Transfer amount between bank accounts",
            description = "Transfer money from one bank account to another and trigger an " +
                    "event for each transaction.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction successful"),
            @ApiResponse(responseCode = "400", description = "Invalid transaction request."),
            @ApiResponse(responseCode = "409", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")

    })
    @PostMapping("/transfer-amount")
    public ResponseEntity<String> transferAmount(@RequestBody AmountTransferDTO amountTransfer){

        String message = accountService.transferAmount(amountTransfer);
//        return ResponseEntity.ok(message);
            return ResponseEntity.status(HttpStatus.CREATED).body(message);

        }
}
