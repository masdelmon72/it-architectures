package com.example.eventdriven.service;

import com.example.eventdriven.core.IEventBus;
import com.example.eventdriven.model.OrderCreatedEvent;

/**
 * Implementazione del servizio di gestione inventario
 */
public class InventoryService implements IInventoryService {
    
    public InventoryService(IEventBus eventBus) {
        eventBus.subscribe(OrderCreatedEvent.class, this::handleOrderCreatedEvent);
    }
    
    private void handleOrderCreatedEvent(OrderCreatedEvent event) {
        updateInventory(event.getOrderId());
    }
    
    @Override
    public void updateInventory(String orderId) {
        System.out.println("InventoryService: Updating inventory for order " + orderId);
        // Logica per aggiornare l'inventario...
    }
}