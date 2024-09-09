package com.debejium.chage_data_capture.messaging.event;

import java.time.LocalDate;

public record CustomerDTO(Long id, String firstName, String lastName, LocalDate birthDate, String emailAddress,
        Integer ssn) {
}
