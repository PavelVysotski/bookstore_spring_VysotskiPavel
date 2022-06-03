package com.belhard.dao.order;

import com.belhard.dao.entity.Order;

import java.util.List;

public interface OrderDao {

    List<Order> getAll();

    Order getOrderById(Long id);

    Order createOrder(Order newOrder);

    Order updateOrder(Order updateOrder);

    boolean deleteById(Long id);
}
