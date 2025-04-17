package com.example.eventdriven.service;

import com.example.eventdriven.core.EventBus;
import com.example.eventdriven.model.OrderCreatedEvent;

/**
 * Servizio che gestisce l'inventario in risposta agli eventi
 */
public class InventoryService {
    
    public InventoryService(EventBus eventBus) {
        eventBus.subscribe(OrderCreatedEvent.class, this::handleOrderCreatedEvent);
    }
    
    private void handleOrderCreatedEvent(OrderCreatedEvent event) {
        System.out.println("InventoryService: Updating inventory for order " + event.getOrderId());
        // Logica per aggiornare l'inventario...
    }
}