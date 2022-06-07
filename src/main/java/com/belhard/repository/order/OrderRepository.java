package com.belhard.repository.order;

import com.belhard.repository.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT id, user_id, total_cost, create_time, update_time, status FROM orders WHERE status != 'CANCELED' ORDER BY id ASC", nativeQuery = true)
    List<Order> findAllOrders();
}
