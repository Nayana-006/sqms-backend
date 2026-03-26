package com.sqms.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Queue {  // rename to match service and controller

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;       // match frontend
    private String service;    // match frontend
    private int tokenNumber;   // match frontend
    private String status;     // WAITING / SERVING / COMPLETED
    private LocalDateTime createdTime;

    public Queue() {}

    // Constructor to create new queue
    public Queue(String name, String service, int tokenNumber, String status) {
        this.name = name;
        this.service = service;
        this.tokenNumber = tokenNumber;
        this.status = status;
        this.createdTime = LocalDateTime.now();
    }

    // Getters and setters
    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getService() { return service; }
    public void setService(String service) { this.service = service; }

    public int getTokenNumber() { return tokenNumber; }
    public void setTokenNumber(int tokenNumber) { this.tokenNumber = tokenNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedTime() { return createdTime; }
    public void setCreatedTime(LocalDateTime createdTime) { this.createdTime = createdTime; }
}