package com.debejium.chage_data_capture.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.debejium.chage_data_capture.domain.OutboxMessage;

@Repository
public interface OutboxMessageRepository extends JpaRepository<OutboxMessage, Long> {

        List<OutboxMessage> findTop10BySentOrderById(boolean send);

} 