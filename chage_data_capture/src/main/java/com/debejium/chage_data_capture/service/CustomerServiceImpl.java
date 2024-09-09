package com.debejium.chage_data_capture.service;

import org.springframework.stereotype.Service;
import com.debejium.chage_data_capture.domain.Customer;
import com.debejium.chage_data_capture.domain.OutboxMessage;
import com.debejium.chage_data_capture.messaging.event.CustomerDTO;
import com.debejium.chage_data_capture.repository.CustomerRepository;
import com.debejium.chage_data_capture.repository.OutboxMessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;
    private final OutboxMessageRepository outboxMessageRepository;

    @Override
    @SneakyThrows
    @Transactional
    public Customer create(final Customer customer) {
        Customer customerCreated = customerRepository.save(customer);
        CustomerDTO customerDTO = CustomerMapper.mapToCustomerDTO(customerCreated);
        String payload = objectMapper.writeValueAsString(customerDTO);
        var outboxMessage = OutboxMessage.builder()
                .id(customerCreated.getId())
                .eventType("CustomerCreated")
                .payLoad(payload)
                .build();
        outboxMessageRepository.save(outboxMessage);
        // No direct event publishing here
        return customerCreated;
    }

    interface CustomerMapper {
        static CustomerDTO mapToCustomerDTO(final Customer customerCreated) {
            return new CustomerDTO(customerCreated.getId(),
                    customerCreated.getFirstName().getValue(),
                    customerCreated.getLastName().getValue(),
                    customerCreated.getBirthDate().getValue(),
                    customerCreated.getEmailAddress().getValue(),
                    customerCreated.getSsn().getSsn());
        }
    }
}
