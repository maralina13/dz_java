package ru.itis.shop.usecase.account;

import org.springframework.stereotype.Service;
import ru.itis.shop.accounts.service.AccountService;
import ru.itis.shop.orders.dto.OrderDto;
import ru.itis.shop.orders.service.OrderService;

import java.util.List;

@Service
public class AccountOrderService {

    private final AccountService accountService;
    private final OrderService orderService;

    public AccountOrderService(AccountService accountService, OrderService orderService) {
        this.accountService = accountService;
        this.orderService = orderService;
    }

    public List<OrderDto> getAccountOrders(Long accountId) {
        accountService.checkAccountExists(accountId);
        return orderService.getAccountOrders(accountId);
    }
}
