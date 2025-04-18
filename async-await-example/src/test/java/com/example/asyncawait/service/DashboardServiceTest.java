package com.example.asyncawait.service;

import com.example.asyncawait.model.UserDashboard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DashboardServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private OrderService orderService;

    private DashboardService dashboardService;

    @BeforeEach
    void setUp() {
        dashboardService = new DashboardService(userService, orderService);
    }

    @Test
    void loadUserDashboard_ShouldReturnCompleteDashboard() {
        // Arrange
        int userId = 123;
        String userData = "{\"id\":123, \"name\":\"TestUser\"}";
        List<String> orders = List.of("Order-123-1", "Order-123-2");
        
        when(userService.fetchUserData(anyInt()))
                .thenReturn(CompletableFuture.completedFuture(userData));
        
        when(orderService.fetchUserOrders(anyInt()))
                .thenReturn(CompletableFuture.completedFuture(orders));
        
        when(orderService.fetchOrderDetails(anyString()))
                .thenReturn(CompletableFuture.completedFuture("{\"id\":\"Order-123-1\", \"total\":99.90, \"date\":\"2025-04-15\"}"));

        // Act
        UserDashboard result = dashboardService.loadUserDashboard(userId).join();

        // Assert
        assertNotNull(result);
        assertEquals(userData, result.userData());
        assertEquals(orders, result.orders());
        assertEquals(2, result.orderDetails().size());
    }
}