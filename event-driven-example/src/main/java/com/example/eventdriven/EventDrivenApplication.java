package com.example.eventdriven;

import com.example.eventdriven.core.EventBus;
import com.example.eventdriven.service.EmailNotificationService;
import com.example.eventdriven.service.InventoryService;
import com.example.eventdriven.service.OrderService;

/**
 * Applicazione principale che dimostra l'architettura event-driven
 */
public class EventDrivenApplication {
    
    public static void main(String[] args) {
        EventBus eventBus = EventBus.getInstance();
        
        // Inizializzazione dei servizi
        new EmailNotificationService(eventBus);
        new InventoryService(eventBus);
        OrderService orderService = new OrderService(eventBus);
        
        System.out.println("Starting Event-Driven Application...");
        
        // Creazione di un ordine che innesca eventi
        orderService.createOrder("Mario Rossi", 99.90);
        
        // Attendi un po' per permettere l'elaborazione degli eventi
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Secondo ordine di esempio
        orderService.createOrder("Giulia Bianchi", 149.50);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Shutting down Event-Driven Application...");
        eventBus.shutdown();
    }
}