# Event-Driven Application con Observability

Questo progetto dimostra un'applicazione event-driven Java con monitoraggio completo utilizzando Prometheus e Grafana.

## Architettura

L'applicazione utilizza un pattern event-driven dove:

1. `OrderService` pubblica eventi `OrderCreatedEvent` quando viene creato un nuovo ordine
2. `InventoryService` e `EmailNotificationService` si sottoscrivono a questi eventi
3. L'`EventBus` coordina la comunicazione asincrona tra servizi
4. `MetricsService` raccoglie e espone metriche per il monitoraggio

## Metriche Monitorate

- **Eventi pubblicati**: Numero totale e rate di eventi pubblicati per tipo
- **Eventi processati**: Numero totale e rate di eventi processati per tipo e subscriber
- **Tempo di elaborazione**: Quanto tempo impiega ogni handler a elaborare un evento
- **Metriche JVM**: Memoria heap, tempo GC, thread, utilizzo CPU

## Prerequisiti

- Java 11+
- Maven
- Docker e Docker Compose

## Configurazione e Avvio

### Opzione 1: Esecuzione manuale

1. Compilare il progetto:
   ```
   mvn clean package
   ```

2. Eseguire l'applicazione:
   ```
   java -jar target/event-driven-example-1.0-SNAPSHOT-jar-with-dependencies.jar
   ```

3. Le metriche saranno disponibili su `http://localhost:8080/metrics`

### Opzione 2: Docker Compose (raccomandato)

1. Rendere eseguibile lo script di setup:
   ```
   chmod +x setup.sh
   ```

2. Eseguire lo script di setup:
   ```
   ./setup.sh
   ```

3. I servizi saranno disponibili ai seguenti indirizzi:
   - **Applicazione**: http://localhost:8080
   - **Metriche Prometheus**: http://localhost:8080/metrics
   - **Prometheus UI**: http://localhost:9090
   - **Grafana**: http://localhost:3000 (username: admin, password: admin)
   - **Dashboard Grafana**: http://localhost:3000/d/event-driven-dashboard

## Struttura del Progetto

```
.
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── example/
│                   └── eventdriven/
│                       ├── core/
│                       │   ├── Event.java
│                       │   ├── EventBus.java
│                       │   └── IEventBus.java
│                       ├── model/
│                       │   └── OrderCreatedEvent.java
│                       ├── service/
│                       │   ├── EmailNotificationService.java
│                       │   ├── IEmailNotificationService.java
│                       │   ├── InventoryService.java
│                       │   ├── IInventoryService.java
│                       │   ├── IMetricsService.java
│                       │   ├── IOrderService.java
│                       │   ├── MetricsService.java
│                       │   └── OrderService.java
│                       └── EventDrivenApplication.java
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── prometheus.yml
├── grafana/
│   └── provisioning/
│       ├── datasources/
│       │   └── datasource.yml
│       └── dashboards/
│           ├── dashboard.yml
│           └── event-driven-dashboard.json
├── setup.sh
└── README.md
```

## Accesso alle dashboard

Una volta avviata l'applicazione con Docker Compose, puoi accedere alle dashboard:

### Prometheus

- URL: http://localhost:9090
- Prova alcune query:
  - `rate(events_published_total[1m])` - Rate di eventi pubblicati per minuto
  - `rate(events_processed_total[1m])` - Rate di eventi processati per minuto
  - `rate(event_processing_duration_seconds_sum[1m]) / rate(event_processing_duration_seconds_count[1m])` - Tempo medio di elaborazione

### Grafana

- URL: http://localhost:3000
- Login: admin / admin
- La dashboard "Event-Driven Application Dashboard" è preconfigurata e accessibile dalla home

## Monitoraggio principale disponibile

1. **Eventi pubblicati e processati**
   - Numero totale e rate per tipo di evento
   - Breakdown per subscriber

2. **Performance**
   - Tempo di elaborazione per subscriber
   - Latenza media e percentili

3. **Stato JVM**
   - Utilizzo memoria heap
   - Tempo GC
   - Thread
   - CPU

## Estensione del sistema di metriche

Per aggiungere nuove metriche:

1. Aggiungere nuovi contatori/gauge/istogrammi in `MetricsService.java`
2. Implementare metodi di registrazione nell'interfaccia `IMetricsService`
3. Aggiornare la dashboard Grafana per visualizzare le nuove metriche