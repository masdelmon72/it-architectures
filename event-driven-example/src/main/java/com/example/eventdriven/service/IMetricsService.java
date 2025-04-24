package com.example.eventdriven.service;

/**
 * Interfaccia per il servizio di metriche
 */
public interface IMetricsService {
    
    /**
     * Registra un evento pubblicato
     * @param eventType Il tipo di evento pubblicato
     */
    void recordEventPublished(String eventType);
    
    /**
     * Registra un evento processato da un subscriber
     * @param eventType Il tipo di evento processato
     * @param subscriber L'identificatore del subscriber
     */
    void recordEventProcessed(String eventType, String subscriber);
    
    /**
     * Registra un nuovo subscriber per un tipo di evento
     * @param eventType Il tipo di evento a cui il subscriber si registra
     */
    void registerSubscriber(String eventType);
    
    /**
     * Rimuove un subscriber per un tipo di evento
     * @param eventType Il tipo di evento da cui il subscriber si cancella
     */
    void unregisterSubscriber(String eventType);
    
    /**
     * Misura il tempo di elaborazione di un evento
     * @param eventType Il tipo di evento elaborato
     * @param subscriber L'identificatore del subscriber
     * @return Un AutoCloseable per terminare la misurazione
     */
    AutoCloseable measureEventProcessingDuration(String eventType, String subscriber);
    
    /**
     * Chiude il servizio di metriche
     */
    void shutdown();
}