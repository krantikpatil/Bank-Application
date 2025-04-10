package com.bank.manager_dashboard.contract;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Interaction;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.bank.manager_dashboard.bankservice.core.domain.AmountTransaction;
import com.bank.manager_dashboard.bankservice.core.domain.AmountTransactionDTO;
import com.bank.manager_dashboard.bankservice.core.mapper.AmountTransactionMapper;
import com.bank.manager_dashboard.bankservice.core.port.AccountTransactionPort;
import com.bank.manager_dashboard.bankservice.core.usecase.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "bank-transaction", providerType = ProviderType.ASYNCH, pactVersion = PactSpecVersion.V4)
public class TransactionContractTest {

    @Autowired
    InputDestination inputDestination;

    @Autowired
    AmountTransactionMapper amountTransactionMapper;

    @MockitoBean
    AccountTransactionPort accountTransactionPort;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    TransactionService transactionService;

    @Autowired
    AmountTransactionMapper mapper;

    @Captor
    ArgumentCaptor<AmountTransactionDTO> transactionCaptor;

    @Captor
    ArgumentCaptor<AmountTransaction> transactionCaptorEntity;

    @Pact(consumer = "manager")
     public V4Pact transactionEventPact(MessagePactBuilder builder){

     PactDslJsonBody transactionBody =    new PactDslJsonBody()
             .uuid("transactionId")
                .stringType("name", "Sachin Patil")
                .stringType("fromAccount", "1212324465")
                .stringType("toAccount", "7876875665")
                .stringType("ifscCode", "JHGHG7876H")
             .numberType("amount", 5098)
             .stringType("transactionDate", "2025-04-06 17:22:33");


       return builder
               .given("Transaction available")
               .expectsToReceive("A transaction message")
               .withContent(transactionBody)
               .toPact();
    }

@Test
@PactTestFor(pactMethod = "transactionEventPact")
    void testTransactionEventPact(V4Interaction.AsynchronousMessage message) throws  Exception{

//        System.out.println(message);

//        String json = message.getContents().getContents().valueAsString();
//        System.out.println(json);

    final var content = message.getContents();

    final var springMessage =
            MessageBuilder.createMessage(
                    content.getContents().valueAsString(),
                    new MessageHeaders(content.getMetadata()));

//        AmountTransactionDTO transaction = objectMapper.readValue(json, AmountTransactionDTO.class);

//        Message<AmountTransactionDTO> springMessage = MessageBuilder.withPayload(transaction).build();

//        System.out.println(springMessage);

         inputDestination.send(springMessage, "transactionListener-in-0");

    objectMapper.registerModule(new JavaTimeModule());

    AmountTransactionDTO amountTransaction =
            objectMapper.readValue(content.getContents().getValue(), AmountTransactionDTO.class);

    System.out.println("this is the message");

    System.out.println(amountTransaction);



//        verify(transactionService).saveTransactionDetails(transactionCaptor.capture());

//    System.out.println("this is the message :---");
//
//    System.out.println(transactionCaptor.getValue());
//    System.out.println(transactionCaptor.capture());

//    MessageHeaders headers = new MessageHeaders(message.getContents().getMetadata());
//     final var sMessage = MessageBuilder
//             .withPayload(message.getContents())
//             .copyHeaders(headers)
//             .build();

//    final var content = message.getContents();
//    final var springMessage =
//            MessageBuilder.createMessage(
//                    content.getContents().valueAsString(),
//                    new MessageHeaders(content.getMetadata()));


//      this.inputDestination.send(sMessage, "transactionListener-in-0");
//      System.out.println(transactionCaptor.capture());
//        verify(this.transactionService).fetchTransaction(transactionCaptor.capture().getFromAccount());
//    verify(this.accountTransactionPort).replaceVehicle(vehicleCaptor.capture());

//    AmountTransactionDTO trDTO = mapper.amountTransactionToAmountTransactionDTO(transactionCaptor.capture());


//    verify(this.transactionService).saveTransactionDetails(transactionCaptor.capture());
//
//    AmountTransactionDTO amountTransaction = transactionCaptor.getValue();
//    System.out.println("This is the amount transaction :--");
//    System.out.println(amountTransaction);




//System.out.println("Data :--");
//System.out.println(transactionCaptor.capture());
//System.out.println(transactionCaptor.capture().getName());

//     verify(transactionService).saveTransactionDetails(any(AmountTransactionDTO.class));
//     Thread.sleep(500);
//    assertEquals("Sachin Patil", transactionCaptor.capture().getName());
//    assertEquals("1212324465", transactionCaptor.capture().getFromAccount());

    }
}
