package com.example.messagequeue.broker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.example.messagequeue.queue.MessageQueue;
import com.example.messagequeue.service.consumer.MessageConsumer;
import com.example.messagequeue.service.producer.MessageProducer;

/**
 * Message Broker che collega più producer e consumer attraverso code.
 */
public class MessageBroker {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    
    /**
     * Crea una nuova coda di messaggi.
     * 
     * @param name Il nome della coda
     * @param capacity La capacità massima della coda
     * @return Una nuova coda di messaggi
     */
    public MessageQueue createQueue(String name, int capacity) {
        return new MessageQueue(name, capacity);
    }
    
    /**
     * Crea un nuovo producer di messaggi.
     * 
     * @param name Il nome del producer
     * @param queue La coda su cui inviare i messaggi
     * @return Un nuovo producer di messaggi
     */
    public MessageProducer createProducer(String name, MessageQueue queue) {
        return new MessageProducer(name, queue);
    }
    
    /**
     * Crea un nuovo consumer di messaggi.
     * 
     * @param name Il nome del consumer
     * @param queue La coda da cui ricevere i messaggi
     * @return Un nuovo consumer di messaggi
     */
    public MessageConsumer createConsumer(String name, MessageQueue queue) {
        return new MessageConsumer(name, queue);
    }
    
    /**
     * Arresta il broker e le sue risorse.
     */
    public void shutdown() {
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