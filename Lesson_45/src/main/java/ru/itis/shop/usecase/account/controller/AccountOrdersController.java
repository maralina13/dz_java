package ru.itis.shop.usecase.account.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.itis.shop.orders.dto.OrderDto;
import ru.itis.shop.usecase.account.AccountOrderService;
import ru.itis.shop.usecase.account.controller.api.AccountOrdersApi;

import java.util.List;

@RestController
public class AccountOrdersController implements AccountOrdersApi {

    private final AccountOrderService accountOrderService;

    public AccountOrdersController(AccountOrderService accountOrderService) {
        this.accountOrderService = accountOrderService;
    }

    @Override
    public List<OrderDto> getAccountOrders(Long accountId) {
        return accountOrderService.getAccountOrders(accountId);
    }
}
