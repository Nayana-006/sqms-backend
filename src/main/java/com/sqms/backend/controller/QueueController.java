package com.sqms.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sqms.backend.model.QueueEntry;
import com.sqms.backend.service.QueueService;

@RestController
@RequestMapping("/api/queue")
public class QueueController {

    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @GetMapping
    public List<QueueEntry> getAllQueues() {
        return queueService.getAllQueues();
    }

    @PostMapping("/call-next")
    public QueueEntry callNextQueue() {
        return queueService.callNextQueue();
    }

    @PostMapping("/add")
    public QueueEntry addQueue(@RequestParam String name, @RequestParam String service) {
        return queueService.addQueueEntry(name, service);
    }

    // NEW: Complete queue entry
    @PostMapping("/complete/{id}")
    public QueueEntry completeQueue(@PathVariable Long id) {
        return queueService.completeQueue(id);
    }

    // Get queue by ID
    @GetMapping("/{id}")
    public QueueEntry getQueueById(@PathVariable Long id) {
        return queueService.getQueueById(id);
    }

    // Skip queue (move to end)
    @PostMapping("/skip/{id}")
    public QueueEntry skipQueue(@PathVariable Long id) {
        return queueService.skipQueue(id);
    }

    // Leave queue (remove from queue)
    @PostMapping("/leave/{id}")
    public void leaveQueue(@PathVariable Long id) {
        queueService.leaveQueue(id);
    }

    // Clear all queues
    @PostMapping("/clear")
    public void clearAllQueues() {
        queueService.clearAllQueues();
    }

    // Reset system
    @PostMapping("/reset")
    public void resetSystem() {
        queueService.resetSystem();
    }

    // Remove specific queue entry
    @DeleteMapping("/remove/{id}")
    public void removeQueueEntry(@PathVariable Long id) {
        queueService.removeQueueEntry(id);
    }
}
