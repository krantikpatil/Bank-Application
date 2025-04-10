//package com.bankapplication.pact;
//
//import au.com.dius.pact.core.model.messaging.Message;
//import au.com.dius.pact.provider.junit5.MessageTestTarget;
//import au.com.dius.pact.provider.junitsupport.Provider;
//import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
//import au.com.dius.pact.provider.junitsupport.target.TestTarget;
//import com.bankapplication.bankservice.core.domain.AmountTransfer;
//import com.bankapplication.bankservice.core.domain.AmountTransferDTO;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.messaging.support.MessageBuilder;
//
//import java.math.BigDecimal;
//import java.util.Map;
//
//@Provider("bank-transaction")
//@PactBroker
//@SpringBootTest
//public class TractionProviderTest {
//
//    @TestTarget
//    public final MessageTestTarget target = new MessageTestTarget();
//
//    @BeforeEach
//    void setup(){
////        target.setMessageHandlers(Map.of("A transaction message", this::productTransactionMessage));
//    }
//
//    public Message produceTransactionMessage(){
//        AmountTransferDTO trasaction = AmountTransferDTO.builder()
//                .name("Sachin Patil")
//                .fromAccount("1234567898")
//                .toAccount("9876543212")
//                .amount(new BigDecimal("12000")).build();
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = null;
//        try {
//            json = objectMapper.writeValueAsString(trasaction);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        return  new MessageBuilder().setPayload(json).build();
//    }
//}
