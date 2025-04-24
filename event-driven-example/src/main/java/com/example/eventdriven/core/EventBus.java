package com.example.eventdriven.core;

import com.example.eventdriven.service.IMetricsService;
import com.example.eventdriven.service.MetricsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;

/**
 * Implementazione dell'Event Bus con supporto per le metriche
 */
public class EventBus implements IEventBus {
    private static final EventBus INSTANCE = new EventBus();
    private final Map<Class<? extends Event>, List<Consumer<? super Event>>> subscribers = new HashMap<>();
    private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
    private final IMetricsService metricsService;
    
    private EventBus() {
        this.metricsService = new MetricsService();
    }
    
    public static EventBus getInstance() {
        return INSTANCE;
    }
    
    @Override
    public <T extends Event> void subscribe(Class<T> eventType, Consumer<T> eventConsumer) {
        subscribers.computeIfAbsent(eventType, k -> new ArrayList<>())
                   .add((Consumer<? super Event>) eventConsumer);
        
        // Registrare il subscriber nelle metriche
        metricsService.registerSubscriber(eventType.getSimpleName());
    }
    
    @Override
    public <T extends Event> void publish(T event) {
        String eventTypeName = event.getClass().getSimpleName();
        
        // Registrare l'evento pubblicato
        metricsService.recordEventPublished(eventTypeName);
        
        CompletableFuture.runAsync(() -> {
            List<Consumer<? super Event>> eventSubscribers = subscribers.getOrDefault(event.getClass(), List.of());
            
            for (Consumer<? super Event> subscriber : eventSubscribers) {
                String subscriberName = subscriber.getClass().getEnclosingMethod() != null ?
                        subscriber.getClass().getEnclosingMethod().getName() :
                        subscriber.getClass().getSimpleName();
                
                try (AutoCloseable timer = metricsService.measureEventProcessingDuration(eventTypeName, subscriberName)) {
                    subscriber.accept(event);
                    // Registrare l'evento processato con successo
                    metricsService.recordEventProcessed(eventTypeName, subscriberName);
                } catch (Exception e) {
                    System.err.println("Error dispatching event to subscriber: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }, executor);
    }
    
    @Override
    public void shutdown() {
        metricsService.shutdown();
        executor.shutdown();
    }
    
    // Getter per scopi di test
    public IMetricsService getMetricsService() {
        return metricsService;
    }
}