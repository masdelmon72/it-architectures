package com.example.eventdriven.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import com.example.eventdriven.core.EventBus;
import com.example.eventdriven.model.OrderCreatedEvent;

public class OrderServiceTest {

    private EventBus eventBus;
    private OrderService orderService;

    @BeforeEach
    public void setup() {
        eventBus = EventBus.getInstance();
        orderService = new OrderService(eventBus);
    }

    @Test
    public void testCreateOrder() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<OrderCreatedEvent> receivedEvent = new AtomicReference<>();
        
        eventBus.subscribe(OrderCreatedEvent.class, event -> {
            receivedEvent.set(event);
            latch.countDown();
        });
        
        // Create an order
        orderService.createOrder("Test Customer", 99.99);
        
        // Wait for event to be processed
        assertTrue(latch.await(2, TimeUnit.SECONDS), "Event should be processed within timeout");
        
        // Verify event details
        OrderCreatedEvent event = receivedEvent.get();
        assertNotNull(event, "Event should not be null");
        assertEquals("Test Customer", event.getCustomerName(), "Customer name should match");
        assertEquals(99.99, event.getAmount(), "Order amount should match");
        assertNotNull(event.getOrderId(), "Order ID should not be null");
    }
}