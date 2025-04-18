package com.example.asyncawait.service;

import com.example.asyncawait.util.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Servizio che fornisce operazioni asincrone per la gestione degli utenti.
 */
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final ExecutorService executor = Executors.newCachedThreadPool();
    //private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    /**
     * Recupera in modo asincrono i dati di un utente.
     *
     * @param userId ID dell'utente
     * @return CompletableFuture con i dati dell'utente in formato JSON
     */
    public CompletableFuture<String> fetchUserData(int userId) {
        logger.info("Recupero dati utente con ID: {}", userId);
        return CompletableFuture.supplyAsync(() -> {
            // Simulazione di una richiesta di rete
            ThreadUtils.sleep(500);
            logger.debug("Dati utente recuperati per ID: {}", userId);
            return "{\"id\":" + userId + ", \"name\":\"User" + userId + "\"}";
        }, executor);
    }

    /**
     * Chiude l'executor service quando non è più necessario.
     */
    public void shutdown() {
        logger.info("Chiusura dell'executor service di UserService");
        executor.shutdown();
    }
}
