package ru.itis.shop.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.shop.orders.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByOwner_Id(Long ownerId);
}
