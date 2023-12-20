package com.ecommerce.order.repository;

import com.ecommerce.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {

    @Query(value = "select * from orders where user_id = :user_id", nativeQuery = true)
    List<Order> getAllOrdersByUserId(long user_id);
}
