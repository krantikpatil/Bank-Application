package com.bank.manager_dashboard.bankservice.config;

import com.bank.manager_dashboard.bankservice.in.queue.TransactionConsumer;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {



        @Bean
        public ConnectionFactory connectionFactory() {
            CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
            connectionFactory.setUsername("guest");
            connectionFactory.setPassword("guest");
            return connectionFactory;

    }

//
//  private static final String QUEUE_NAME = "transactionQueue";
//
//    @Bean
//    public Queue transactionQueue() {
//        return new Queue (QUEUE_NAME, true);
//    }
//
//    @Bean
//    public MessageConverter jsonMessageConverter(){
//        return new Jackson2JsonMessageConverter();
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(jsonMessageConverter());
//        return rabbitTemplate;
//    }
//
//    @Bean
//    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, MessageListenerAdapter  listenerAdapter){
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueues(transactionQueue());
//        container.setMessageListener(listenerAdapter);
//        return container;
//    }
//
//    @Bean
//    public  MessageListenerAdapter listenerAdapter(TransactionConsumer listener) {
//
//        MessageListenerAdapter adapter = new MessageListenerAdapter(listener, "receiveMessage");
//        adapter.setMessageConverter(jsonMessageConverter());
//        return adapter;
//
//    }
}
