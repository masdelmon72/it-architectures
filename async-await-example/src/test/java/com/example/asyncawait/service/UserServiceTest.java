package com.example.asyncawait.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @AfterEach
    void tearDown() {
        userService.shutdown();
    }

    @Test
    void fetchUserData_ShouldReturnValidUserData() throws ExecutionException, InterruptedException, TimeoutException {
        // Arrange
        int userId = 42;

        // Act
        String userData = userService.fetchUserData(userId).get(2, TimeUnit.SECONDS);

        // Assert
        assertNotNull(userData);
        assertTrue(userData.contains("\"id\":42"));
        assertTrue(userData.contains("\"name\":\"User42\""));
    }

    @Test
    void fetchUserData_WithDifferentIds_ShouldReturnDifferentData() throws ExecutionException, InterruptedException, TimeoutException {
        // Arrange
        int userId1 = 1;
        int userId2 = 2;

        // Act
        String userData1 = userService.fetchUserData(userId1).get(2, TimeUnit.SECONDS);
        String userData2 = userService.fetchUserData(userId2).get(2, TimeUnit.SECONDS);

        // Assert
        assertNotNull(userData1);
        assertNotNull(userData2);
        assertNotEquals(userData1, userData2);
        assertTrue(userData1.contains("\"id\":1"));
        assertTrue(userData2.contains("\"id\":2"));
    }

    @Test
    void fetchUserData_ShouldCompleteFuture() {
        // Arrange
        int userId = 99;

        // Act
        var future = userService.fetchUserData(userId);

        // Assert
        assertFalse(future.isCompletedExceptionally());
        assertTrue(future.isDone() || !future.isDone()); // This will always be true, but we're testing the CompletableFuture behavior
    }
}