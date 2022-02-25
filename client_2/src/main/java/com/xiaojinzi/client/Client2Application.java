package com.xiaojinzi.client;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Client2Application {

    public static void main(String[] args) {
        SpringApplication.run(Client2Application.class, args);
    }

    @Bean
    public DirectExchange testExchange() {
        return new DirectExchange("testExchange");
    }

    @Bean
    public Queue testQueue() {
        return new Queue("testQueue2");
    }

    @Bean
    public Binding testBinding(Queue testQueue, DirectExchange testExchange) {
        return BindingBuilder
                .bind(testQueue)
                .to(testExchange)
                .with("key2");
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @org.springframework.amqp.rabbit.annotation.Queue(
                            name = "testQueue2"
                    ),
                    exchange = @org.springframework.amqp.rabbit.annotation.Exchange(
                            name = "testExchange"
                    ),
                    key = {"key2"}
            )
    )
    public void listen(String in) throws InterruptedException {
        Thread.sleep(100);
        System.out.println("client2 listen = " + in);
    }

}
