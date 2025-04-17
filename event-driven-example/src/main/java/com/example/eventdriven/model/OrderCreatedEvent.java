package com.example.eventdriven.model;

import com.example.eventdriven.core.Event;

/**
 * Esempio di evento concreto per la creazione di un ordine
 */
public class OrderCreatedEvent extends Event {
    private final String orderId;
    private final String customerName;
    private final double amount;
    
    public OrderCreatedEvent(String orderId, String customerName, double amount) {
        super();
        this.orderId = orderId;
        this.customerName = customerName;
        this.amount = amount;
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public double getAmount() {
        return amount;
    }
    
    @Override
    public String toString() {
        return "OrderCreatedEvent{orderId='" + orderId + "', customerName='" + 
               customerName + "', amount=" + amount + '}';
    }
}