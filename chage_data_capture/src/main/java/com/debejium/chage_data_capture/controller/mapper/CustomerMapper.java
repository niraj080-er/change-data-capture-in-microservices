package com.debejium.chage_data_capture.controller.mapper;

import com.debejium.chage_data_capture.controller.dto.CustomerDTO;
import com.debejium.chage_data_capture.domain.BirthDate;
import com.debejium.chage_data_capture.domain.Customer;
import com.debejium.chage_data_capture.domain.EmailAddress;
import com.debejium.chage_data_capture.domain.FirstName;
import com.debejium.chage_data_capture.domain.LastName;
import com.debejium.chage_data_capture.domain.SSN;



public interface CustomerMapper {

    static Customer mapToCustomer(final CustomerDTO customerDTO) {
        FirstName firstName = FirstName.of(customerDTO.firstName());
        LastName lastName = LastName.of(customerDTO.lastName());
        BirthDate birthDate = BirthDate.of(customerDTO.birthDate());
        EmailAddress emailAddress = EmailAddress.of(customerDTO.emailAddress());
        SSN ssn = SSN.create(customerDTO.ssn());
        return Customer.create(firstName, lastName, birthDate, emailAddress, ssn);
    }

}