package com.example.eventdriven.service;

/**
 * Interfaccia per il servizio di gestione ordini
 */
public interface IOrderService {
    /**
     * Crea un nuovo ordine
     * @param customerName Nome del cliente
     * @param amount Importo dell'ordine
     * @return ID dell'ordine creato
     */
    String createOrder(String customerName, double amount);
}