package com.example.eventdriven;

import com.example.eventdriven.core.IEventBus;
import com.example.eventdriven.core.EventBus;
import com.example.eventdriven.service.IEmailNotificationService;
import com.example.eventdriven.service.EmailNotificationService;
import com.example.eventdriven.service.IInventoryService;
import com.example.eventdriven.service.InventoryService;
import com.example.eventdriven.service.IOrderService;
import com.example.eventdriven.service.OrderService;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Applicazione principale che dimostra l'architettura event-driven
 * con supporto per il monitoraggio delle metriche
 */
public class EventDrivenApplication {
    
    public static void main(String[] args) {
        IEventBus eventBus = EventBus.getInstance();
        
        // Inizializzazione dei servizi
        IEmailNotificationService emailService = new EmailNotificationService(eventBus);
        IInventoryService inventoryService = new InventoryService(eventBus);
        IOrderService orderService = new OrderService(eventBus);
        
        System.out.println("Starting Event-Driven Application with Metrics...");
        System.out.println("Metrics available at: http://localhost:8080/metrics");
        
        // Eseguire un ciclo di creazione ordini per generare metriche
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        final Random random = new Random();
        
        // Array di nomi di clienti di esempio
        final String[] customers = {
                "Mario Rossi", "Giulia Bianchi", "Paolo Verdi", "Laura Neri",
                "Giuseppe Esposito", "Francesca Romano", "Alessandro Marino", "Sofia Greco"
        };
        
        // Creazione ordini casuali ogni 2 secondi
        scheduler.scheduleAtFixedRate(() -> {
            String customer = customers[random.nextInt(customers.length)];
            double amount = 50 + random.nextDouble() * 450; // Importo tra 50 e 500
            
            System.out.println("\nCreating order for: " + customer);
            orderService.createOrder(customer, amount);
            
        }, 0, 2, TimeUnit.SECONDS);
        
        // Eseguire l'applicazione per 60 secondi, poi terminare
        try {
            Thread.sleep(60_000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Shutting down Event-Driven Application...");
        scheduler.shutdown();
        eventBus.shutdown();
        
        System.out.println("Application terminated.");
    }
}