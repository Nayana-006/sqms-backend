package com.sqms.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sqms.backend.model.QueueEntry;
import com.sqms.backend.repository.QueueRepository;

@Service
public class QueueService {

    private final QueueRepository queueRepository;

    public QueueService(QueueRepository queueRepository) {
        this.queueRepository = queueRepository;
    }

    // Call next waiting queue
    @Transactional
    public QueueEntry callNextQueue() {
        // First, check if there is already a SERVING entry
        QueueEntry serving = queueRepository.findFirstByStatus("SERVING");
        if (serving != null) {
            return serving; // return the currently serving queue
        }

        // Otherwise, pick next WAITING queue
        QueueEntry next = queueRepository.findFirstByStatusOrderByTokenNumberAsc("WAITING");
        if (next != null) {
            next.setStatus("SERVING");
            return queueRepository.save(next);
        }
        return null; // no waiting queues
    }

    // Get all queues
    public List<QueueEntry> getAllQueues() {
        return queueRepository.findAll();
    }

    // Add new queue entry
    public QueueEntry addQueueEntry(String name, String service) {
        // Get the highest token number currently in the system
        Optional<QueueEntry> lastEntry = queueRepository.findTopByOrderByTokenNumberDesc();
        int nextToken = 1;
        
        if (lastEntry.isPresent()) {
            nextToken = lastEntry.get().getTokenNumber() + 1;
        }

        QueueEntry entry = new QueueEntry(name, service, nextToken, "WAITING");
        return queueRepository.save(entry);
    }

    // Complete queue entry
    @Transactional
    public QueueEntry completeQueue(Long id) {
        QueueEntry entry = queueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Queue entry not found: " + id));

        // Only SERVING queues can be completed
        if (!"SERVING".equals(entry.getStatus())) {
            throw new RuntimeException("Only SERVING queues can be completed");
        }

        entry.setStatus("COMPLETED");
        return queueRepository.save(entry);
    }

    // Get queue by ID
    public QueueEntry getQueueById(Long id) {
        return queueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Queue entry not found: " + id));
    }

    // Skip queue (move to end)
    @Transactional
    public QueueEntry skipQueue(Long id) {
        QueueEntry entry = queueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Queue entry not found: " + id));

        // Only SERVING queues can be skipped
        if (!"SERVING".equals(entry.getStatus())) {
            throw new RuntimeException("Only SERVING queues can be skipped");
        }

        // Move to end of queue by updating token number
        int lastToken = queueRepository.findTopByOrderByTokenNumberDesc()
                .map(QueueEntry::getTokenNumber)
                .orElse(0);

        entry.setTokenNumber(lastToken + 1);
        return queueRepository.save(entry);
    }

    // Leave queue (remove from queue)
    @Transactional
    public void leaveQueue(Long id) {
        QueueEntry entry = queueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Queue entry not found: " + id));

        // Only WAITING queues can leave
        if (!"WAITING".equals(entry.getStatus())) {
            throw new RuntimeException("Only WAITING queues can leave");
        }

        queueRepository.delete(entry);
    }

    // Clear all queues
    @Transactional
    public void clearAllQueues() {
        queueRepository.deleteAll();
    }

    // Reset system
    @Transactional
    public void resetSystem() {
        queueRepository.deleteAll();
    }

    // Remove specific queue entry
    @Transactional
    public void removeQueueEntry(Long id) {
        QueueEntry entry = queueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Queue entry not found: " + id));

        queueRepository.delete(entry);
    }
}
