#FROM maven:3.8-openjdk-17 AS builder
#WORKDIR /app
#COPY pom.xml /app/
# Scarico dipendenze separatamente per ottimizzare la cache
#RUN mvn dependency:go-offline

# Copia la struttura del progetto mantenendo l'organizzazione delle directory
#COPY src /app/src/
# Compila il progetto
#RUN mvn clean package

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copia il JAR compilato dall'immagine builder
#COPY --from=builder /app/target/event-driven-example-1.0-SNAPSHOT-jar-with-dependencies.jar /app/app.jar
#COPY target/event-driven-example-1.0-SNAPSHOT.jar /app/app.jar
COPY target/event-driven-example-1.0-SNAPSHOT-jar-with-dependencies.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
