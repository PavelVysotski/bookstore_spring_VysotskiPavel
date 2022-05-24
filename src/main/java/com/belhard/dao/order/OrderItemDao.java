package com.belhard.dao.order;

import com.belhard.dao.entity.OrderItem;

import java.util.List;

public interface OrderItemDao {

    List<OrderItem> getAll();

    List<OrderItem> getByOrderId(Long id);

    OrderItem getOrderItemById(Long id);

    OrderItem createOrderItem(OrderItem newOrderItem);

    OrderItem updateOrderItem(OrderItem updateOrderItem);

    boolean deleteOrderItemById(Long id);
}
