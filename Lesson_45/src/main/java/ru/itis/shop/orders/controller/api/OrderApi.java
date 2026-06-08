package ru.itis.shop.orders.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.itis.shop.orders.dto.NewOrderDto;
import ru.itis.shop.orders.dto.OrderDto;

@RequestMapping("/api/v1/orders")
public interface OrderApi {

    @Operation(
            summary = "Добавить заказ аккаунту",
            description = "Проверяет, что аккаунт существует, создает заказ и привязывает его к аккаунту."
    )
    @ApiResponse(
            description = "Заказ добавлен",
            responseCode = "201",
            content = @Content(schema = @Schema(implementation = OrderDto.class))
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    OrderDto addOrder(@RequestBody @Valid NewOrderDto newOrder);
}
