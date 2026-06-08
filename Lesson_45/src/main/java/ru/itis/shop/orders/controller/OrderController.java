package ru.itis.shop.orders.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.itis.shop.orders.controller.api.OrderApi;
import ru.itis.shop.orders.dto.NewOrderDto;
import ru.itis.shop.orders.dto.OrderDto;
import ru.itis.shop.orders.service.OrderService;

@RestController
public class OrderController implements OrderApi {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public OrderDto addOrder(NewOrderDto newOrder) {
        return orderService.addOrder(newOrder);
    }
}
