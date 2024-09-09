package com.debejium.chage_data_capture.service;

import com.debejium.chage_data_capture.domain.Customer;


public interface CustomerService {
    Customer create(Customer customer);

    // void changeEmail(Long customerId, EmailAddress emailAddress);
}