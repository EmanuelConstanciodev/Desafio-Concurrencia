package com.example.desafio.controller;


import com.example.desafio.dto.OrderRequest;
import com.example.desafio.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(ProcessOrderController.class)
class ProcessOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPostOrder() throws Exception {
        OrderRequest order = OrderRequest.builder()
                .orderId("123")
                .customerId("client1")
                .orderAmount(200.0)
                .orderItems(List.of("bread", "milk"))
                .build();

        mockMvc.perform(post("/processOrder")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated());
    }
}
