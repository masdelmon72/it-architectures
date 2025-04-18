package com.example.messagequeue.service.consumer;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.example.messagequeue.model.Message;
import com.example.messagequeue.queue.MessageQueue;

/**
 * Consumer che elabora messaggi dalla coda.
 */
public class MessageConsumer {
    private final MessageQueue queue;
    private final String name;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    
    /**
     * Crea un nuovo consumer di messaggi.
     * 
     * @param name Il nome del consumer
     * @param queue La coda da cui ricevere i messaggi
     */
    public MessageConsumer(String name, MessageQueue queue) {
        this.name = name;
        this.queue = queue;
    }
    
    /**
     * Avvia l'elaborazione asincrona dei messaggi.
     */
    public void startProcessing() {
        CompletableFuture.runAsync(this::processMessages, executor);
    }
    
    private void processMessages() {
        System.out.printf("[Consumer %s] Started processing messages from queue %s%n", 
                         name, queue.getName());
        
        while (running.get() && !Thread.currentThread().isInterrupted()) {
            try {
                Message message = queue.receive(Duration.ofSeconds(1));
                if (message != null) {
                    processMessage(message);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.printf("[Consumer %s] Interrupted while receiving message%n", name);
                break;
            }
        }
        
        System.out.printf("[Consumer %s] Stopped processing messages%n", name);
    }
    
    private void processMessage(Message message) {
        System.out.printf("[Consumer %s] Processing message: %s%n", name, message);
        
        // Simulazione dell'elaborazione del messaggio
        try {
            // Emula un tempo di elaborazione variabile
            Thread.sleep(300 + (long) (Math.random() * 700));
            System.out.printf("[Consumer %s] Successfully processed message: %s%n", 
                             name, message.id());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.printf("[Consumer %s] Interrupted while processing message%n", name);
        }
    }
    
    /**
     * Interrompe l'elaborazione dei messaggi.
     */
    public void stop() {
        running.set(false);
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executor.shutdownNow();
        }
    }
}