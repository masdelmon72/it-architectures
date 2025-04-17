package com.example.eventdriven.service;

/**
 * Interfaccia per il servizio di gestione inventario
 */
public interface IInventoryService {
    /**
     * Aggiorna l'inventario in base a un ordine
     * @param orderId ID dell'ordine
     */
    void updateInventory(String orderId);
}