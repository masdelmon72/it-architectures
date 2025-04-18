package com.example.asyncawait;

import com.example.asyncawait.service.DashboardService;
import com.example.asyncawait.service.OrderService;
import com.example.asyncawait.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe principale dell'applicazione che dimostra
 * l'utilizzo del pattern async/await in Java.
 */
public class AsyncAwaitApplication {
    private static final Logger logger = LoggerFactory.getLogger(AsyncAwaitApplication.class);

    public static void main(String[] args) {
        logger.info("Avvio dell'applicazione async/await demo");
        
        // Creazione e inizializzazione dei servizi
        UserService userService = new UserService();
        OrderService orderService = new OrderService();
        DashboardService dashboardService = new DashboardService(userService, orderService);
        
        // Esegui l'elaborazione del dashboard per un utente
        int userId = 123;
        logger.info("Caricamento del dashboard per l'utente con ID: {}", userId);
        
        dashboardService.processUserDashboard(userId);
        
        logger.info("Elaborazione completata, applicazione terminata");
    }
}