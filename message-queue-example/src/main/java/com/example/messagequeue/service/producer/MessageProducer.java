package com.example.messagequeue.service.producer;

import java.util.concurrent.CompletableFuture;

import com.example.messagequeue.model.Message;
import com.example.messagequeue.queue.MessageQueue;

/**
 * Producer che invia messaggi alla coda.
 */
public class MessageProducer {
    private final MessageQueue queue;
    private final String name;
    
    /**
     * Crea un nuovo producer di messaggi.
     * 
     * @param name Il nome del producer
     * @param queue La coda su cui inviare i messaggi
     */
    public MessageProducer(String name, MessageQueue queue) {
        this.name = name;
        this.queue = queue;
    }
    
    /**
     * Invia un messaggio alla coda.
     * 
     * @param type Il tipo di messaggio
     * @param payload Il contenuto del messaggio
     */
    public void sendMessage(String type, String payload) {
        try {
            Message message = Message.of(type, payload);
            queue.send(message);
            System.out.printf("[Producer %s] Sent message: %s%n", name, message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.printf("[Producer %s] Interrupted while sending message%n", name);
        }
    }
    
    /**
     * Invia un messaggio alla coda in modo asincrono.
     * 
     * @param type Il tipo di messaggio
     * @param payload Il contenuto del messaggio
     * @return Un CompletableFuture che rappresenta l'operazione asincrona
     */
    public CompletableFuture<Void> sendMessageAsync(String type, String payload) {
        return CompletableFuture.runAsync(() -> sendMessage(type, payload));
    }
}