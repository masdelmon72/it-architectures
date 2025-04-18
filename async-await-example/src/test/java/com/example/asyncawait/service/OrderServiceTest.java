package com.example.asyncawait.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService();
    }

    @AfterEach
    void tearDown() {
        orderService.shutdown();
    }

    @Test
    void fetchUserOrders_ShouldReturnThreeOrders() throws ExecutionException, InterruptedException, TimeoutException {
        // Arrange
        int userId = 42;

        // Act
        List<String> orders = orderService.fetchUserOrders(userId).get(2, TimeUnit.SECONDS);

        // Assert
        assertNotNull(orders);
        assertEquals(3, orders.size());
        assertTrue(orders.stream().allMatch(order -> order.contains("Order-42-")));
    }

    @Test
    void fetchOrderDetails_ShouldReturnValidOrderDetails() throws ExecutionException, InterruptedException, TimeoutException {
        // Arrange
        String orderId = "Order-123-1";

        // Act
        String orderDetails = orderService.fetchOrderDetails(orderId).get(2, TimeUnit.SECONDS);

        // Assert
        assertNotNull(orderDetails);
        assertTrue(orderDetails.contains("\"id\":\"Order-123-1\""));
        assertTrue(orderDetails.contains("\"total\":99.90"));
        assertTrue(orderDetails.contains("\"date\":\"2025-04-15\""));
    }

    @Test
    void fetchOrderDetails_WithDifferentIds_ShouldReturnDifferentData() throws ExecutionException, InterruptedException, TimeoutException {
        // Arrange
        String orderId1 = "Order-100-1";
        String orderId2 = "Order-200-1";

        // Act
        String orderDetails1 = orderService.fetchOrderDetails(orderId1).get(2, TimeUnit.SECONDS);
        String orderDetails2 = orderService.fetchOrderDetails(orderId2).get(2, TimeUnit.SECONDS);

        // Assert
        assertNotNull(orderDetails1);
        assertNotNull(orderDetails2);
        assertNotEquals(orderDetails1, orderDetails2);
        assertTrue(orderDetails1.contains("\"id\":\"Order-100-1\""));
        assertTrue(orderDetails2.contains("\"id\":\"Order-200-1\""));
    }

    @Test
    void fetchUserOrders_DifferentUserIds_ShouldReturnDifferentOrderIds() throws ExecutionException, InterruptedException, TimeoutException {
        // Arrange
        int userId1 = 10;
        int userId2 = 20;

        // Act
        List<String> orders1 = orderService.fetchUserOrders(userId1).get(2, TimeUnit.SECONDS);
        List<String> orders2 = orderService.fetchUserOrders(userId2).get(2, TimeUnit.SECONDS);

        // Assert
        assertNotNull(orders1);
        assertNotNull(orders2);
        
        assertTrue(orders1.stream().allMatch(order -> order.contains("Order-10-")));
        assertTrue(orders2.stream().allMatch(order -> order.contains("Order-20-")));
        
        assertFalse(orders1.stream().anyMatch(order -> order.contains("Order-20-")));
        assertFalse(orders2.stream().anyMatch(order -> order.contains("Order-10-")));
    }
}