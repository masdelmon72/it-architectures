package com.example.asyncawait.service;

import com.example.asyncawait.model.UserDashboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Servizio che compone i dati dell'utente e degli ordini per creare un dashboard.
 */
public class DashboardService {
    private static final Logger logger = LoggerFactory.getLogger(DashboardService.class);
    
    private final UserService userService;
    private final OrderService orderService;

    /**
     * Costruttore che inietta le dipendenze necessarie.
     *
     * @param userService servizio per la gestione degli utenti
     * @param orderService servizio per la gestione degli ordini
     */
    public DashboardService(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    /**
     * Carica in modo asincrono i dati del dashboard di un utente.
     *
     * @param userId ID dell'utente
     * @return CompletableFuture con i dati aggregati del dashboard
     */
    public CompletableFuture<UserDashboard> loadUserDashboard(int userId) {
        logger.info("Inizio caricamento dashboard per utente: {}", userId);
        
        CompletableFuture<String> userDataFuture = userService.fetchUserData(userId);
        CompletableFuture<List<String>> ordersFuture = orderService.fetchUserOrders(userId);
        
        // Combina i risultati delle prime due operazioni asincrone
        return userDataFuture.thenCombine(ordersFuture, (userData, orders) -> {
            logger.debug("Dati utente e ordini recuperati, ora recupero i dettagli degli ordini");
            
            // Una volta ottenuti utente e ordini, recupera i dettagli degli ordini
            List<CompletableFuture<String>> orderDetailsFutures = 
                orders.stream()
                      .map(orderService::fetchOrderDetails)
                      .collect(Collectors.toList());
            
            // Attendi il completamento di tutti i dettagli degli ordini
            CompletableFuture<Void> allOrderDetails = 
                CompletableFuture.allOf(
                    orderDetailsFutures.toArray(new CompletableFuture[0])
                );
            
            // Trasforma il risultato in un UserDashboard
            return allOrderDetails.thenApply(v -> {
                List<String> orderDetails = 
                    orderDetailsFutures.stream()
                                      .map(CompletableFuture::join)
                                      .collect(Collectors.toList());
                logger.debug("Tutti i dettagli degli ordini recuperati, creazione dashboard");
                return new UserDashboard(userData, orders, orderDetails);
            }).join(); // Qui usiamo join() perché siamo già in un contesto asincrono
        });
    }

    /**
     * Processa il dashboard di un utente, gestendo anche le eccezioni.
     *
     * @param userId ID dell'utente
     */
    public void processUserDashboard(int userId) {
        try {
            loadUserDashboard(userId)
                .thenAccept(dashboard -> {
                    logger.info("Dashboard utente caricato con successo:");
                    System.out.println(dashboard);
                })
                .exceptionally(ex -> {
                    logger.error("Errore durante il caricamento del dashboard: {}", ex.getMessage());
                    return null;
                })
                .join(); // Blocca fino al completamento per dimostrare il risultato
        } finally {
            // Chiudi gli executor services
            userService.shutdown();
            orderService.shutdown();
        }
    }
}