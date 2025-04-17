package com.example.eventdriven.core;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Event base class
 * Class di base per tutti gli eventi nel sistema
 */
public abstract class Event {
    private final UUID id;
    private final LocalDateTime timestamp;
    
    protected Event() {
        this.id = UUID.randomUUID();
        this.timestamp = LocalDateTime.now();
    }
    
    public UUID getId() {
        return id;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}