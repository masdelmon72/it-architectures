package com.example.messagequeue.queue;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.messagequeue.model.Message;

class MessageQueueTest {
    
    private MessageQueue queue;
    
    @BeforeEach
    void setUp() {
        queue = new MessageQueue("test-queue", 5);
    }
    
    @Test
    void testQueueProperties() {
        assertEquals("test-queue", queue.getName());
        assertEquals(5, queue.getCapacity());
        assertEquals(0, queue.size());
    }
    
    @Test
    void testSendAndReceive() throws InterruptedException {
        Message sentMessage = Message.of("test", "payload");
        queue.send(sentMessage);
        
        assertEquals(1, queue.size());
        
        Message receivedMessage = queue.receive();
        assertEquals(sentMessage, receivedMessage);
        assertEquals(0, queue.size());
    }
    
    @Test
    void testSendWithTimeout() throws InterruptedException {
        Message message = Message.of("test", "payload");
        boolean result = queue.send(message, Duration.ofMillis(100));
        
        assertTrue(result);
        assertEquals(1, queue.size());
    }
    
    @Test
    void testReceiveWithTimeout() throws InterruptedException {
        Message sentMessage = Message.of("test", "payload");
        queue.send(sentMessage);
        
        Message receivedMessage = queue.receive(Duration.ofMillis(100));
        assertEquals(sentMessage, receivedMessage);
        
        // Coda vuota, dovrebbe restituire null dopo il timeout
        Message nullMessage = queue.receive(Duration.ofMillis(100));
        assertNull(nullMessage);
    }
}