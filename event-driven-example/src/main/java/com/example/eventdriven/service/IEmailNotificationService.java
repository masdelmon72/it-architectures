package com.example.eventdriven.service;

/**
 * Interfaccia per il servizio di notifica email
 */
public interface IEmailNotificationService {
    /**
     * Invia una email di conferma ordine
     * @param orderId ID dell'ordine
     * @param customerName Nome del cliente
     * @param amount Importo dell'ordine
     */
    void sendOrderConfirmation(String orderId, String customerName, double amount);
}