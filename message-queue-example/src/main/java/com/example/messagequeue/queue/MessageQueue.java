package com.example.messagequeue.queue;

import java.time.Duration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.example.messagequeue.model.Message;

/**
 * Un Message Queue abilita la comunicazione asincrona tra componenti.
 */
public class MessageQueue {
    private final String name;
    private final BlockingQueue<Message> queue;
    private final int capacity;
    
    /**
     * Crea una nuova coda di messaggi.
     * 
     * @param name Il nome della coda
     * @param capacity La capacità massima della coda
     */
    public MessageQueue(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.queue = new LinkedBlockingQueue<>(capacity);
    }
    
    /**
     * @return Il nome della coda
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return La capacità massima della coda
     */
    public int getCapacity() {
        return capacity;
    }
    
    /**
     * @return Il numero attuale di messaggi nella coda
     */
    public int size() {
        return queue.size();
    }
    
    /**
     * Aggiunge un messaggio alla coda (bloccante se piena).
     * 
     * @param message Il messaggio da inviare
     * @throws InterruptedException Se il thread viene interrotto durante l'attesa
     */
    public void send(Message message) throws InterruptedException {
        queue.put(message);
        System.out.printf("[%s] Message sent: %s%n", name, message);
    }
    
    /**
     * Aggiunge un messaggio alla coda con timeout.
     * 
     * @param message Il messaggio da inviare
     * @param timeout Il tempo massimo di attesa
     * @return true se il messaggio è stato inviato, false se il timeout è scaduto
     * @throws InterruptedException Se il thread viene interrotto durante l'attesa
     */
    public boolean send(Message message, Duration timeout) throws InterruptedException {
        boolean success = queue.offer(message, timeout.toMillis(), TimeUnit.MILLISECONDS);
        if (success) {
            System.out.printf("[%s] Message sent: %s%n", name, message);
        }
        return success;
    }
    
    /**
     * Riceve un messaggio dalla coda (bloccante se vuota).
     * 
     * @return Il messaggio ricevuto
     * @throws InterruptedException Se il thread viene interrotto durante l'attesa
     */
    public Message receive() throws InterruptedException {
        Message message = queue.take();
        System.out.printf("[%s] Message received: %s%n", name, message);
        return message;
    }
    
    /**
     * Riceve un messaggio dalla coda con timeout.
     * 
     * @param timeout Il tempo massimo di attesa
     * @return Il messaggio ricevuto, o null se il timeout è scaduto
     * @throws InterruptedException Se il thread viene interrotto durante l'attesa
     */
    public Message receive(Duration timeout) throws InterruptedException {
        Message message = queue.poll(timeout.toMillis(), TimeUnit.MILLISECONDS);
        if (message != null) {
            System.out.printf("[%s] Message received: %s%n", name, message);
        }
        return message;
    }
}