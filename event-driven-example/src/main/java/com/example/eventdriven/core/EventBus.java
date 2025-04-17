package com.example.eventdriven.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;

/**
 * Event Bus - il cuore dell'architettura event-driven
 * Gestisce la pubblicazione e sottoscrizione degli eventi
 */
public class EventBus {
    private static final EventBus INSTANCE = new EventBus();
    private final Map<Class<? extends Event>, List<Consumer<? super Event>>> subscribers = new HashMap<>();
    private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
    
    private EventBus() {}
    
    public static EventBus getInstance() {
        return INSTANCE;
    }
    
    /**
     * Registra un subscriber per un tipo di evento
     * @param eventType Tipo di evento da sottoscrivere
     * @param eventConsumer Consumer che gestirà l'evento
     * @param <T> Tipo di evento
     */
    public <T extends Event> void subscribe(Class<T> eventType, Consumer<T> eventConsumer) {
        subscribers.computeIfAbsent(eventType, k -> new ArrayList<>())
                   .add((Consumer<? super Event>) eventConsumer);
    }
    
    /**
     * Pubblica un evento a tutti i subscriber interessati
     * @param event Evento da pubblicare
     * @param <T> Tipo di evento
     */
    public <T extends Event> void publish(T event) {
        CompletableFuture.runAsync(() -> {
            List<Consumer<? super Event>> eventSubscribers = subscribers.getOrDefault(event.getClass(), List.of());
            for (Consumer<? super Event> subscriber : eventSubscribers) {
                try {
                    subscriber.accept(event);
                } catch (Exception e) {
                    System.err.println("Error dispatching event to subscriber: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }, executor);
    }
    
    /**
     * Shutdown thread pool
     */
    public void shutdown() {
        executor.shutdown();
    }
}