package com.example.asyncawait.util;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class ThreadUtilsTest {

    @Test
    void sleep_ShouldDelayExecution() {
        // Arrange
        long sleepMillis = 500;
        Instant start = Instant.now();

        // Act
        ThreadUtils.sleep(sleepMillis);
        Duration elapsed = Duration.between(start, Instant.now());

        // Assert
        // Potrebbe essere leggermente più lungo o più corto a causa della precisione del thread scheduler
        assertTrue(elapsed.toMillis() >= sleepMillis - 50, "Sleep deve durare almeno " + (sleepMillis - 50) + " ms");
    }

    @Test
    void sleep_WithZeroMillis_ShouldReturnQuickly() {
        // Arrange
        long sleepMillis = 0;
        Instant start = Instant.now();

        // Act
        ThreadUtils.sleep(sleepMillis);
        Duration elapsed = Duration.between(start, Instant.now());

        // Assert
        assertTrue(elapsed.toMillis() < 100, "Sleep con 0ms non dovrebbe durare più di 100ms");
    }

    @Test
    void sleep_WithInterruption_ShouldResetInterruptFlag() {
        // Arrange
        long sleepMillis = 1000;
        Thread currentThread = Thread.currentThread();
        
        // Act & Assert
        Thread interrupter = new Thread(() -> {
            try {
                Thread.sleep(100); // Attendi un po' prima di interrompere
                currentThread.interrupt();
            } catch (InterruptedException e) {
                fail("Thread di test interrotto inaspettatamente");
            }
        });
        
        interrupter.start();
        ThreadUtils.sleep(sleepMillis);
        
        // Verifica che il flag di interruzione sia stato reimpostato
        assertTrue(Thread.interrupted(), "Il flag di interruzione dovrebbe essere reimpostato");
    }

    @Test
    void sleepNegativeMillis_ShouldNotThrowException() {
        // Arrange & Act & Assert
        assertDoesNotThrow(() -> ThreadUtils.sleep(-1));
    }
}