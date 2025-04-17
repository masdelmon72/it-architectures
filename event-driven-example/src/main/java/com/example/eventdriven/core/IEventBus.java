package com.example.eventdriven.core;

import java.util.function.Consumer;

/**
 * Interfaccia per l'Event Bus
 */
public interface IEventBus {
    /**
     * Registra un subscriber per un tipo di evento
     * @param eventType Tipo di evento da sottoscrivere
     * @param eventConsumer Consumer che gestirà l'evento
     * @param <T> Tipo di evento
     */
    <T extends Event> void subscribe(Class<T> eventType, Consumer<T> eventConsumer);
    
    /**
     * Pubblica un evento a tutti i subscriber interessati
     * @param event Evento da pubblicare
     * @param <T> Tipo di evento
     */
    <T extends Event> void publish(T event);
    
    /**
     * Shutdown dell'event bus
     */
    void shutdown();
}