package com.sqms.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class QueueEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;          // person name
    private String service;       // optional service type
    private Integer tokenNumber;  // order in queue
    private String status;        // WAITING, SERVING, COMPLETED

    public QueueEntry() {}

    public QueueEntry(String name, String service, Integer tokenNumber, String status) {
        this.name = name;
        this.service = service;
        this.tokenNumber = tokenNumber;
        this.status = status;
    }

    // Getters & setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getService() { return service; }
    public Integer getTokenNumber() { return tokenNumber; }
    public String getStatus() { return status; }

    public void setName(String name) { this.name = name; }
    public void setService(String service) { this.service = service; }
    public void setTokenNumber(Integer tokenNumber) { this.tokenNumber = tokenNumber; }
    public void setStatus(String status) { this.status = status; }
}