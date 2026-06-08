package ru.itis.shop.orders.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.shop.accounts.entity.Account;
import ru.itis.shop.accounts.service.AccountService;
import ru.itis.shop.orders.dto.NewOrderDto;
import ru.itis.shop.orders.dto.OrderDto;
import ru.itis.shop.orders.entity.Order;
import ru.itis.shop.orders.repository.OrderRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.itis.shop.orders.dto.OrderDto.from;

@Service
public class OrderService {

    private final AccountService accountService;
    private final OrderRepository orderRepository;

    public OrderService(AccountService accountService, OrderRepository orderRepository) {
        this.accountService = accountService;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderDto addOrder(NewOrderDto newOrder) {
        Account owner = accountService.getAccountEntity(newOrder.getAccountId());
        Order order = new Order(LocalDate.parse(newOrder.getDate()), owner);

        orderRepository.save(order);

        return from(order);
    }

    public List<OrderDto> getAccountOrders(Long accountId) {
        return from(orderRepository.findByOwner_Id(accountId));
    }
}
