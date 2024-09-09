package com.debejium.chage_data_capture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.debejium.chage_data_capture.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}