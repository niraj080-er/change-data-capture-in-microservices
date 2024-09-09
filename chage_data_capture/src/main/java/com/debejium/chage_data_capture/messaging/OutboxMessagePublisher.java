package com.debejium.chage_data_capture.messaging;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.debejium.chage_data_capture.domain.OutboxMessage;
import com.debejium.chage_data_capture.messaging.event.CustomerDTO;
import com.debejium.chage_data_capture.messaging.event.CustomerEvent;
import com.debejium.chage_data_capture.repository.OutboxMessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Sinks;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;

@Component
@Slf4j
@RequiredArgsConstructor
public class OutboxMessagePublisher {

    private final OutboxMessageRepository outboxMessageRepository;
    private final ObjectMapper objectMapper;
    private final Sinks.Many<Message<?>> customerProducer;

    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void deliver() {
        this.outboxMessageRepository.findTop10BySentOrderById(false)
                .forEach(this::deliver);
    }

    private void deliver(final OutboxMessage outboxMessage) {
        Message<CustomerEvent.CustomerCreated> customerCreatedMessage = mapToMessage(outboxMessage);
        customerProducer.tryEmitNext(customerCreatedMessage);
        outboxMessage.delivered();
    }

    @SneakyThrows
    private Message<CustomerEvent.CustomerCreated> mapToMessage(OutboxMessage outboxMessage) {
        String payLoad = outboxMessage.getPayLoad();
        Instant createdAt = outboxMessage.getCreationDate().atStartOfDay().atZone(ZoneId.of("UTC")).toInstant();
        CustomerDTO customerDTO = objectMapper.readValue(payLoad, CustomerDTO.class);
        var customerCreated = new CustomerEvent.CustomerCreated(customerDTO.id(), createdAt, customerDTO);

        byte[] byteArray = customerCreated.customerId().toString().getBytes(StandardCharsets.UTF_8);

        return MessageBuilder
                .withPayload(customerCreated)
                .setHeader("eventType", "CustomerCreated")
                .setHeader("customerId", customerCreated.customerId())
                .setHeader(KafkaHeaders.KEY, byteArray)
                .build();
    }
}
