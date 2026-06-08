package ru.itis.shop.usecase.account.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.shop.orders.dto.OrderDto;

import java.util.List;

@RequestMapping("/api/v1")
public interface AccountOrdersApi {

    @Operation(
            summary = "Получить заказы аккаунта",
            description = "Возвращает список всех заказов указанного аккаунта."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Список заказов аккаунта",
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderDto.class)))
            ),
            @ApiResponse(description = "Аккаунт не найден", responseCode = "404", content = @Content)
    })
    @GetMapping("/accounts/{account-id}/orders")
    List<OrderDto> getAccountOrders(
            @Parameter(description = "Идентификатор аккаунта") @PathVariable("account-id") Long accountId);
}
