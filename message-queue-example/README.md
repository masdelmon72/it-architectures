# Message Queue System

Un sistema di code di messaggi in memoria con producer, consumer e processamento asincrono implementato in Java 17.

## Struttura del Progetto

Il progetto è organizzato nei seguenti package:

- `com.example.messagequeue.model`: Contiene la classe `Message` che rappresenta un messaggio nel sistema
- `com.example.messagequeue.queue`: Contiene la classe `MessageQueue` che implementa una coda di messaggi
- `com.example.messagequeue.service.producer`: Contiene la classe `MessageProducer` per inviare messaggi
- `com.example.messagequeue.service.consumer`: Contiene la classe `MessageConsumer` per ricevere ed elaborare messaggi
- `com.example.messagequeue.broker`: Contiene la classe `MessageBroker` che gestisce code, producer e consumer

## Requisiti

- Java Development Kit (JDK) 17 o superiore
- Apache Maven 3.6.0 o superiore

## Guida rapida

### Compilazione

```bash
mvn clean compile
```

### Esecuzione dei test

```bash
mvn test
```

### Creazione del JAR eseguibile

```bash
mvn clean package
```

### Esecuzione dell'applicazione

```bash
java -jar target/message-queue-1.0-SNAPSHOT.jar
```

Oppure:

```bash
mvn exec:java -Dexec.mainClass="com.example.messagequeue.MessageQueueApplication"
```

## Caratteristiche

- Code di messaggi in memoria
- Producer di messaggi sincorni e asincroni
- Consumer di messaggi con elaborazione asincrona
- Timeout configurabili per le operazioni di invio e ricezione
- Broker per gestire più code e componenti

## Estensione del progetto

Il progetto può essere esteso in vari modi:

- Aggiungere persistenza dei messaggi su disco
- Implementare un meccanismo di retry per i messaggi falliti
- Aggiungere supporto per messaggi prioritari
- Implementare routing basato sul tipo di messaggio
- Aggiungere funzionalità di monitoraggio e statistiche