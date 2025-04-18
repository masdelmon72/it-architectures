package com.example.asyncawait.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class per le operazioni relative ai thread.
 */
public class ThreadUtils {
    private static final Logger logger = LoggerFactory.getLogger(ThreadUtils.class);
    
    private ThreadUtils() {
        // Utility class, no instantiation
    }
    
    /**
     * Mette in pausa il thread corrente per il numero specificato di millisecondi.
     *
     * @param millis durata della pausa in millisecondi
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            logger.warn("Thread interrotto durante il sleep", e);
            Thread.currentThread().interrupt();
        }
    }
}