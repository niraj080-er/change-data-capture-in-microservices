package com.debejium.chage_data_capture.messaging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;
import org.springframework.messaging.Message;

@Configuration
public class SinksConfig {

    @Bean
    public Sinks.Many<Message<?>> customerProducer() {
        return Sinks.many().replay().latest();
    }
}