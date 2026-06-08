package ru.itis.shop.orders.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class NewOrderDto {

    @Schema(description = "Идентификатор аккаунта", example = "1")
    @NotNull(message = "Идентификатор аккаунта обязателен")
    @Min(value = 1, message = "Идентификатор аккаунта должен быть положительным")
    private Long accountId;

    @Schema(description = "Дата заказа", example = "2022-01-01")
    @NotBlank(message = "Дата заказа обязательна")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Дата заказа должна быть в формате yyyy-MM-dd")
    private String date;

    public NewOrderDto() {
    }

    public NewOrderDto(Long accountId, String date) {
        this.accountId = accountId;
        this.date = date;
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
