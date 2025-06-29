package com.example.desafio.service;


import com.example.desafio.dto.OrderRequest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private final OrderService orderService = new OrderService(Executors.newSingleThreadExecutor());

    @Test
    void testProcessOrderWithInvalidAmountThrowsException() {
        OrderRequest order = new OrderRequest();
        order.setOrderId("1");
        order.setCustomerId("c123");
        order.setOrderAmount(-50.0);
        order.setOrderItems(List.of("item1"));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            orderService.processOrder(order);
        });

        assertEquals("El monto debe ser mayor que 0", ex.getMessage());
    }

    @Test
    void testProcessedOrderGetsStoredInMap() throws InterruptedException {
        OrderRequest order = new OrderRequest();
        order.setOrderId("100");
        order.setCustomerId("clienteX");
        order.setOrderAmount(150.0);
        order.setOrderItems(List.of("pan", "queso"));

        orderService.processOrderAsync(order);

        Thread.sleep(600);

        assertTrue(orderService.getProcessedOrders().containsKey("100"));
    }

}