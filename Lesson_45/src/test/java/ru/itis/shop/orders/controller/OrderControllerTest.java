package ru.itis.shop.orders.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.itis.shop.orders.dto.OrderDto;
import ru.itis.shop.orders.service.OrderService;

import java.time.format.DateTimeParseException;

import static org.hamcrest.Matchers.hasKey;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    OrderService orderService;

    @Nested
    @DisplayName("POST /api/v1/orders works ...")
    class AddOrder {

        @Test
        void add_order_returns_created_order_full_json() throws Exception {
            OrderDto order = new OrderDto(1L, 1L, "2022-01-01");

            when(orderService.addOrder(any())).thenReturn(order);

            mockMvc.perform(post("/api/v1/orders")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                      "accountId": 1,
                                      "date": "2022-01-01"
                                    }
                                    """))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.orderId").value(1))
                    .andExpect(jsonPath("$.accountId").value(1))
                    .andExpect(jsonPath("$.date").value("2022-01-01"));
        }

        @Test
        void add_order_returns_validation_errors_when_body_is_invalid() throws Exception {
            mockMvc.perform(post("/api/v1/orders")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                      "accountId": 0,
                                      "date": ""
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("Validation errors"))
                    .andExpect(jsonPath("$.errors", hasKey("accountId")))
                    .andExpect(jsonPath("$.errors", hasKey("date")))
                    .andExpect(jsonPath("$.timestamp").exists());
        }

        @Test
        void add_order_returns_bad_request_when_date_is_not_real() throws Exception {
            when(orderService.addOrder(any())).thenThrow(DateTimeParseException.class);

            mockMvc.perform(post("/api/v1/orders")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                      "accountId": 1,
                                      "date": "2022-13-01"
                                    }
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("Дата заказа должна быть существующей датой в формате yyyy-MM-dd"))
                    .andExpect(jsonPath("$.timestamp").exists());
        }
    }
}
