package com.bank.manager_dashboard.bankservice.in.queue;

import com.bank.manager_dashboard.bankservice.core.domain.AmountTransferDTO;
import com.bank.manager_dashboard.bankservice.core.port.AccountTransactionPort;
import com.bank.manager_dashboard.bankservice.core.usecase.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
@RabbitListener(queues = "transactionQueue")
public class TransactionConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    AmountTransferDTO amountTransferDTO;

    @Autowired
    TransactionService transactionService;

   @RabbitHandler
    public void fetchTransaction(byte[] message){

           String str = new String(message, StandardCharsets.UTF_8);
       Map data = null;
       try {
           data = objectMapper.readValue(str, Map.class);
       } catch (JsonProcessingException e) {
           throw new RuntimeException(e);
       }

           amountTransferDTO.setTransactionId((UUID) data.get("transactionId"));
           amountTransferDTO.setName((String) data.get("name"));
           amountTransferDTO.setFromAccount((String) data.get("fromAccount"));
           amountTransferDTO.setToAccount((String) data.get("toAccount"));
           amountTransferDTO.setIfscCode((String) data.get("ifscCode"));
           amountTransferDTO.setAmount(new BigDecimal(String.valueOf(data.get("amount"))));
           amountTransferDTO.setTransactionDate(((String) data.get("transactionDate")));

           transactionService.saveTransaction(amountTransferDTO);

   }
}
