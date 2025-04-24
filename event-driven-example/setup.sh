#!/bin/bash

# Creare le directory necessarie
mkdir -p grafana/provisioning/datasources
mkdir -p grafana/provisioning/dashboards

# Copiare i file di configurazione
cp prometheus.yml ./
cp grafana/provisioning/datasources/datasource.yml ./grafana/provisioning/datasources/
cp grafana/provisioning/dashboards/dashboard.yml ./grafana/provisioning/dashboards/
cp grafana/provisioning/dashboards/event-driven-dashboard.json ./grafana/provisioning/dashboards/

# Avviare i container
docker-compose up -d

echo "Setup completato!"
echo "Applicazione accessibile su: http://localhost:8080"
echo "Prometheus accessibile su: http://localhost:9090"
echo "Grafana accessibile su: http://localhost:3000 (username: admin, password: admin)"
echo "Grafana dashboard: http://localhost:3000/d/event-driven-dashboard"