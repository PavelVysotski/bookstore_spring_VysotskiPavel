package com.belhard.service;

import com.belhard.service.dto.order.OrderDto;

import java.util.List;

public interface OrderService {

    List<OrderDto> getAllOrders();

    OrderDto getOrderById(Long id);

    OrderDto createOrder(OrderDto OrderDto);

    OrderDto updateOrder(OrderDto OrderDto);

    void deleteOrderById(Long id);
}
