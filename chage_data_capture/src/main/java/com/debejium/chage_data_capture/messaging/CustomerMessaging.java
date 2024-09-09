package com.debejium.chage_data_capture.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.debejium.chage_data_capture.messaging.event.CustomerEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
@RequiredArgsConstructor
public class CustomerMessaging {

    @Bean
    public Sinks.Many<CustomerEvent> customerProducer1(){
        return Sinks.many().replay().latest();
    }

    @Bean
    public Supplier<Flux<CustomerEvent>> customerSupplier(){
        return ()-> customerProducer1().asFlux();
    }
}