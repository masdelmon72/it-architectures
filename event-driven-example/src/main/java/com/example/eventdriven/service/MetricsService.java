package com.example.eventdriven.service;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.hotspot.DefaultExports;

import java.io.IOException;
import java.time.Duration;

/**
 * Servizio di metriche per monitorare l'applicazione event-driven
 */
public class MetricsService implements IMetricsService {
    // Counter per gli eventi pubblicati
    private final Counter eventsPublishedTotal = Counter.build()
            .name("events_published_total")
            .help("Numero totale di eventi pubblicati")
            .labelNames("event_type")
            .register();
    
    // Counter per gli eventi gestiti dai subscriber
    private final Counter eventsProcessedTotal = Counter.build()
            .name("events_processed_total")
            .help("Numero totale di eventi processati dai subscriber")
            .labelNames("event_type", "subscriber")
            .register();
    
    // Gauge per subscriber attivi
    private final Gauge activeSubscribersGauge = Gauge.build()
            .name("active_subscribers")
            .help("Numero di subscriber attualmente registrati")
            .labelNames("event_type")
            .register();
    
    // Histogram per i tempi di elaborazione degli eventi
    private final Histogram eventProcessingDuration = Histogram.build()
            .name("event_processing_duration_seconds")
            .help("Tempo impiegato per elaborare un evento")
            .labelNames("event_type", "subscriber")
            .buckets(0.001, 0.005, 0.01, 0.05, 0.1, 0.5, 1.0, 5.0) // buckets in secondi
            .register();
    
    private HTTPServer server;
    
    public MetricsService() {
        // Registra metriche JVM predefinite (memoria, CPU, thread, GC)
        DefaultExports.initialize();
        
        try {
            // Avvia il server HTTP su porta 8080 per esporre le metriche a Prometheus
            server = new HTTPServer(8080);
            System.out.println("Metrics server started on port 8080");
        } catch (IOException e) {
            System.err.println("Failed to start metrics server: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public void recordEventPublished(String eventType) {
        eventsPublishedTotal.labels(eventType).inc();
    }
    
    @Override
    public void recordEventProcessed(String eventType, String subscriber) {
        eventsProcessedTotal.labels(eventType, subscriber).inc();
    }
    
    @Override
    public void registerSubscriber(String eventType) {
        activeSubscribersGauge.labels(eventType).inc();
    }
    
    @Override
    public void unregisterSubscriber(String eventType) {
        activeSubscribersGauge.labels(eventType).dec();
    }
    
    @Override
    public AutoCloseable measureEventProcessingDuration(String eventType, String subscriber) {
        Histogram.Timer timer = eventProcessingDuration.labels(eventType, subscriber).startTimer();
        return timer;
    }
    
    @Override
    public void shutdown() {
        if (server != null) {
            server.stop();
        }
    }
}