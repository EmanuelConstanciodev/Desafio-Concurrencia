package com.example.desafio.controller;



import com.example.desafio.dto.OrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProcessOrderIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos a JSON

    @Test
    void testProcessOrderEndpoint_returnsCreated() throws Exception {
        OrderRequest order = new OrderRequest();
        order.setOrderId("999");
        order.setCustomerId("clienteTest");
        order.setOrderAmount(199.99);
        order.setOrderItems(List.of("item1", "item2"));

        mockMvc.perform(post("/processOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated());
    }

    @Test
    void testProcessOrderEndpoint_invalidAmount_returnsBadRequest() throws Exception {
        OrderRequest order = new OrderRequest();
        order.setOrderId("1000");
        order.setCustomerId("clienteBad");
        order.setOrderAmount(-10.0);
        order.setOrderItems(List.of("itemX"));

        mockMvc.perform(post("/processOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isBadRequest());
    }
}

