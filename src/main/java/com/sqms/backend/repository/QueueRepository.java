package com.sqms.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sqms.backend.model.QueueEntry; // ← THIS IS REQUIRED

public interface QueueRepository extends JpaRepository<QueueEntry, Long> {

    List<QueueEntry> findByStatus(String status);

    QueueEntry findFirstByStatusOrderByTokenNumberAsc(String status);

    Optional<QueueEntry> findTopByOrderByTokenNumberDesc();

    QueueEntry findFirstByStatus(String string);
}