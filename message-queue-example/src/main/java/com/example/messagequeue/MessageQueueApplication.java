package com.example.messagequeue;

import java.util.concurrent.CompletableFuture;

import com.example.messagequeue.broker.MessageBroker;
import com.example.messagequeue.queue.MessageQueue;
import com.example.messagequeue.service.consumer.MessageConsumer;
import com.example.messagequeue.service.producer.MessageProducer;

/**
 * Applicazione dimostrativa del sistema di code di messaggi.
 */
public class MessageQueueApplication {
    public static void main(String[] args) {
        System.out.println("Starting Message Queue Application");
        
        MessageBroker broker = new MessageBroker();
        
        // Creazione delle code
        MessageQueue orderQueue = broker.createQueue("orders", 100);
        MessageQueue notificationQueue = broker.createQueue("notifications", 50);
        
        // Creazione di producer e consumer
        MessageProducer orderProducer = broker.createProducer("OrderService", orderQueue);
        MessageConsumer orderProcessor = broker.createConsumer("OrderProcessor", orderQueue);
        
        MessageProducer notificationProducer = broker.createProducer("NotificationService", notificationQueue);
        MessageConsumer notificationConsumer = broker.createConsumer("NotificationProcessor", notificationQueue);
        
        // Avvio dei consumer
        System.out.println("Starting consumers...");
        orderProcessor.startProcessing();
        notificationConsumer.startProcessing();
        
        // Invio di messaggi di ordine
        System.out.println("Sending order messages...");
        for (int i = 1; i <= 10; i++) {
            final int orderId = i;
            CompletableFuture.runAsync(() -> {
                String payload = String.format(
                    "{\"orderId\": \"ORD-%d\", \"amount\": %.2f, \"customerName\": \"Customer %d\"}", 
                    orderId, 50.0 + orderId * 10, orderId
                );
                orderProducer.sendMessage("OrderCreated", payload);
                
                // Simulazione di un ritardo tra messaggi
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        // Invio di messaggi di notifica
        System.out.println("Sending notification messages...");
        for (int i = 1; i <= 5; i++) {
            final int notificationId = i;
            CompletableFuture.runAsync(() -> {
                String payload = String.format(
                    "{\"notificationId\": \"NOTIF-%d\", \"recipient\": \"user%d@example.com\", \"message\": \"Notification %d\"}", 
                    notificationId, notificationId, notificationId
                );
                notificationProducer.sendMessage("EmailNotification", payload);
                
                // Simulazione di un ritardo tra messaggi
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        // Attesa per il completamento dell'elaborazione
        System.out.println("Waiting for message processing to complete...");
        try {
            // Attendi abbastanza per permettere a tutti i messaggi di essere elaborati
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Main thread interrupted while waiting for processing to complete");
        }
        
        // Arresto dei componenti
        System.out.println("Shutting down consumers and broker...");
        orderProcessor.stop();
        notificationConsumer.stop();
        broker.shutdown();
        
        System.out.println("Message Queue Application terminated");
    }
}