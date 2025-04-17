package com.example.eventdriven.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.example.eventdriven.model.OrderCreatedEvent;

public class EventBusTest {

    @Test
    public void testEventPublication() throws InterruptedException {
        EventBus eventBus = EventBus.getInstance();
        
        AtomicBoolean eventReceived = new AtomicBoolean(false);
        CountDownLatch latch = new CountDownLatch(1);
        
        eventBus.subscribe(OrderCreatedEvent.class, event -> {
            eventReceived.set(true);
            latch.countDown();
        });
        
        eventBus.publish(new OrderCreatedEvent("test-id", "Test Customer", 100.0));
        
        // Wait for event to be processed
        assertTrue(latch.await(2, TimeUnit.SECONDS), "Event should be processed within timeout");
        assertTrue(eventReceived.get(), "Event should be received by subscriber");
    }
}