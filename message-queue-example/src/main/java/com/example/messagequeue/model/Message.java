package com.example.messagequeue.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Rappresenta un messaggio base nel sistema di code.
 */
public record Message(
    UUID id,
    String type,
    String payload,
    LocalDateTime timestamp
) {
    /**
     * Crea un nuovo messaggio con un ID e timestamp generati automaticamente.
     * 
     * @param type Il tipo di messaggio
     * @param payload Il contenuto del messaggio
     * @return Un nuovo oggetto Message
     */
    public static Message of(String type, String payload) {
        return new Message(UUID.randomUUID(), type, payload, LocalDateTime.now());
    }
}