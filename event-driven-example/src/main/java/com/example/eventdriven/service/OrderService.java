package com.example.eventdriven.service;

import java.util.UUID;

import com.example.eventdriven.core.EventBus;
import com.example.eventdriven.model.OrderCreatedEvent;

/**
 * Servizio che gestisce gli ordini e pubblica eventi relativi
 */
public class OrderService {
    private final EventBus eventBus;
    
    public OrderService(EventBus eventBus) {
        this.eventBus = eventBus;
    }
    
    /**
     * Crea un nuovo ordine e pubblica un evento OrderCreatedEvent
     * @param customerName Nome del cliente
     * @param amount Importo dell'ordine
     */
    public void createOrder(String customerName, double amount) {
        // Logica per creare l'ordine nel database...
        String orderId = UUID.randomUUID().toString();
        System.out.println("Order created: " + orderId);
        
        // Pubblicazione dell'evento
        eventBus.publish(new OrderCreatedEvent(orderId, customerName, amount));
    }
}