package ru.itis.shop.orders.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itis.shop.accounts.entity.Account;
import ru.itis.shop.accounts.service.AccountService;
import ru.itis.shop.orders.dto.NewOrderDto;
import ru.itis.shop.orders.dto.OrderDto;
import ru.itis.shop.orders.entity.Order;
import ru.itis.shop.orders.repository.OrderRepository;

import java.time.format.DateTimeParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class OrderServiceTest {

    @Mock
    AccountService accountService;

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderService orderService;

    @Nested
    @DisplayName("addOrder() works ...")
    class AddOrder {

        @Test
        void add_order_returns_created_order_with_id() {
            Account account = new Account(1L, "Andrew", "Ganiyev", "andrew@gmail.com");
            NewOrderDto newOrder = new NewOrderDto(1L, "2022-01-01");

            when(accountService.getAccountEntity(1L)).thenReturn(account);
            when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
                Order order = invocation.getArgument(0);
                order.setId(1L);
                return order;
            });

            OrderDto actualOrder = orderService.addOrder(newOrder);

            ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
            verify(orderRepository).save(orderCaptor.capture());

            assertAll(
                    () -> assertEquals(1L, actualOrder.getOrderId()),
                    () -> assertEquals(1L, actualOrder.getAccountId()),
                    () -> assertEquals("2022-01-01", actualOrder.getDate()),
                    () -> assertEquals(account, orderCaptor.getValue().getOwner())
            );
        }

        @Test
        void add_order_throws_exception_when_date_is_not_real() {
            Account account = new Account(1L, "Andrew", "Ganiyev", "andrew@gmail.com");
            NewOrderDto newOrder = new NewOrderDto(1L, "2022-13-01");

            when(accountService.getAccountEntity(1L)).thenReturn(account);

            assertThrows(DateTimeParseException.class, () -> orderService.addOrder(newOrder));
        }
    }

    @Nested
    @DisplayName("getAccountOrders() works ...")
    class GetAccountOrders {

        @Test
        void get_account_orders_returns_order_list() {
            Account account = new Account(1L, "Andrew", "Ganiyev", "andrew@gmail.com");
            Order order = new Order(1L, java.time.LocalDate.parse("2022-01-01"), account);

            when(orderRepository.findByOwner_Id(1L)).thenReturn(List.of(order));

            List<OrderDto> actualOrders = orderService.getAccountOrders(1L);

            assertAll(
                    () -> assertEquals(1, actualOrders.size()),
                    () -> assertEquals(1L, actualOrders.get(0).getOrderId()),
                    () -> assertEquals(1L, actualOrders.get(0).getAccountId()),
                    () -> assertEquals("2022-01-01", actualOrders.get(0).getDate())
            );
        }
    }
}
