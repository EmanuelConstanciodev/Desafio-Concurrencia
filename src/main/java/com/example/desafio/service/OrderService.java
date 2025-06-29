package com.example.desafio.service;

import com.example.desafio.dto.OrderRequest;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
public class OrderService {

    private final ExecutorService executor;

    private final Random random = new Random();

    @Getter
    private final Map<String, OrderRequest> processedOrders = new ConcurrentHashMap<>();

    public OrderService(ExecutorService orderExecutorService) {
        this.executor = orderExecutorService;
    }

    public void processOrderAsync(OrderRequest orderRequest) {
        CompletableFuture.runAsync(() -> processOrder(orderRequest), executor);
    }

    public void processOrder(OrderRequest orderRequest) {
        try {
            validateOrder(orderRequest);

            double total = calculateTotal(orderRequest);

            int delay = 100 + random.nextInt(401);
            Thread.sleep(delay);

            processedOrders.put(orderRequest.getOrderId(), orderRequest);

            System.out.println("Pedido " + orderRequest.getOrderId() +
                    " procesado en " + delay + " ms. Total: $" + total);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error procesando el pedido: " + e.getMessage());
        }
    }

    public void validateOrder(OrderRequest order) {
        if (order.getOrderId() == null || order.getCustomerId() == null) {
            throw new IllegalArgumentException("orderId y customerId no pueden ser nulos");
        }
        double amount = order.getOrderAmount();
        if (amount <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que 0");
        }

        if (order.getOrderItems() == null || order.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("Debe haber al menos un item");
        }
    }

    private double calculateTotal(OrderRequest order) {
        return order.getOrderAmount();
    }

}
