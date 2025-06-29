package com.example.desafio.controller;

import com.example.desafio.dto.OrderRequest;
import com.example.desafio.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SuppressWarnings("unused")
public class ProcessOrderController {

    private final OrderService orderService;

    @Autowired
    public ProcessOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/processOrder")
    public ResponseEntity<String> processOrder(@RequestBody OrderRequest orderRequest) {
        orderService.validateOrder(orderRequest);
        orderService.processOrderAsync(orderRequest);
        return ResponseEntity.status(201).body("Pedido en procesamiento");
    }

}
