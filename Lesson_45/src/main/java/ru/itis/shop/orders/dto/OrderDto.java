package ru.itis.shop.orders.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.itis.shop.orders.entity.Order;

import java.util.List;

public class OrderDto {

    @Schema(description = "Идентификатор заказа", example = "1")
    private Long orderId;

    @Schema(description = "Идентификатор аккаунта", example = "1")
    private Long accountId;

    @Schema(description = "Дата заказа", example = "2022-01-01")
    private String date;

    public OrderDto() {
    }

    public OrderDto(Long orderId, Long accountId, String date) {
        this.orderId = orderId;
        this.accountId = accountId;
        this.date = date;
    }

    public static OrderDto from(Order order) {
        return new OrderDto(order.getId(), order.getOwner().getId(), order.getDate().toString());
    }

    public static List<OrderDto> from(List<Order> orders) {
        return orders.stream().map(OrderDto::from).toList();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
