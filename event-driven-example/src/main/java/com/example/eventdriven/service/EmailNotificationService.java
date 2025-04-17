package com.example.eventdriven.service;

import com.example.eventdriven.core.EventBus;
import com.example.eventdriven.model.OrderCreatedEvent;

/**
 * Servizio che invia notifiche email in risposta agli eventi
 */
public class EmailNotificationService {
    
    public EmailNotificationService(EventBus eventBus) {
        eventBus.subscribe(OrderCreatedEvent.class, this::handleOrderCreatedEvent);
    }
    
    private void handleOrderCreatedEvent(OrderCreatedEvent event) {
        System.out.println("EmailService: Sending order confirmation email to " + 
                          event.getCustomerName() + " for order " + event.getOrderId());
        // Logica per inviare una email...
    }
}