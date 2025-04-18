package com.example.asyncawait.model;

import java.util.List;

/**
 * Record che rappresenta i dati aggregati del dashboard di un utente.
 */
public record UserDashboard(String userData, List<String> orders, List<String> orderDetails) {
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UserDashboard {\n");
        sb.append("  userData: ").append(userData).append("\n");
        sb.append("  orders: ").append(orders).append("\n");
        sb.append("  orderDetails: [\n");
        orderDetails.forEach(detail -> sb.append("    ").append(detail).append("\n"));
        sb.append("  ]\n");
        sb.append("}");
        return sb.toString();
    }
}