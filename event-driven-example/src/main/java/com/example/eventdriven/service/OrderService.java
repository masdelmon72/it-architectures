package com.example.eventdriven.service;

import java.util.UUID;

import com.example.eventdriven.core.IEventBus;
import com.example.eventdriven.model.OrderCreatedEvent;

/**
 * Implementazione del servizio di gestione ordini
 */
public class OrderService implements IOrderService {
    private final IEventBus eventBus;
    
    public OrderService(IEventBus eventBus) {
        this.eventBus = eventBus;
    }
    
    @Override
    public String createOrder(String customerName, double amount) {
        // Logica per creare l'ordine nel database...
        String orderId = UUID.randomUUID().toString();
        System.out.println("Order created: " + orderId);
        
        // Pubblicazione dell'evento
        eventBus.publish(new OrderCreatedEvent(orderId, customerName, amount));
        
        return orderId;
    }
}