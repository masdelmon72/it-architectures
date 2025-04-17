package com.example.eventdriven.service;

import com.example.eventdriven.core.IEventBus;
import com.example.eventdriven.model.OrderCreatedEvent;

/**
 * Implementazione del servizio di notifica email
 */
public class EmailNotificationService implements IEmailNotificationService {
    
    public EmailNotificationService(IEventBus eventBus) {
        eventBus.subscribe(OrderCreatedEvent.class, this::handleOrderCreatedEvent);
    }
    
    private void handleOrderCreatedEvent(OrderCreatedEvent event) {
        sendOrderConfirmation(event.getOrderId(), event.getCustomerName(), event.getAmount());
    }
    
    @Override
    public void sendOrderConfirmation(String orderId, String customerName, double amount) {
        System.out.println("EmailService: Sending order confirmation email to " + 
                          customerName + " for order " + orderId);
        // Logica per inviare una email...
    }
}