package com.example.asyncawait.service;

import com.example.asyncawait.util.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Servizio che fornisce operazioni asincrone per la gestione degli ordini.
 */
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final ExecutorService executor = Executors.newCachedThreadPool();
//    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    /**
     * Recupera in modo asincrono gli ordini di un utente.
     *
     * @param userId ID dell'utente
     * @return CompletableFuture con la lista degli ID degli ordini
     */
    public CompletableFuture<List<String>> fetchUserOrders(int userId) {
        logger.info("Recupero ordini per l'utente con ID: {}", userId);
        return CompletableFuture.supplyAsync(() -> {
            // Simulazione di una query al database
            ThreadUtils.sleep(700);
            List<String> orders = List.of(
                "Order-" + userId + "-1",
                "Order-" + userId + "-2",
                "Order-" + userId + "-3"
            );
            logger.debug("Recuperati {} ordini per l'utente {}", orders.size(), userId);
            return orders;
        }, executor);
    }

    /**
     * Recupera in modo asincrono i dettagli di un ordine.
     *
     * @param orderId ID dell'ordine
     * @return CompletableFuture con i dettagli dell'ordine in formato JSON
     */
    public CompletableFuture<String> fetchOrderDetails(String orderId) {
        logger.info("Recupero dettagli per l'ordine: {}", orderId);
        return CompletableFuture.supplyAsync(() -> {
            // Simulazione di una query al database
            ThreadUtils.sleep(300);
            logger.debug("Dettagli recuperati per l'ordine: {}", orderId);
            return "{\"id\":\"" + orderId + "\", \"total\":99.90, \"date\":\"2025-04-15\"}";
        }, executor);
    }

    /**
     * Chiude l'executor service quando non è più necessario.
     */
    public void shutdown() {
        logger.info("Chiusura dell'executor service di OrderService");
        executor.shutdown();
    }
}
