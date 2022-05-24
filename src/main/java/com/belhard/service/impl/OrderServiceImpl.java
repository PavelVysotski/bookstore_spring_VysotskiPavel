package com.belhard.service.impl;

import com.belhard.dao.entity.Order;
import com.belhard.dao.entity.OrderItem;
import com.belhard.dao.order.OrderDao;
import com.belhard.dao.order.OrderItemDao;
import com.belhard.service.BookService;
import com.belhard.service.OrderService;
import com.belhard.service.UserService;
import com.belhard.service.dto.order.OrderDto;
import com.belhard.service.dto.order.OrderDto.OrderStatusDto;
import com.belhard.service.dto.order.OrderItemDto;
import com.belhard.service.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;
    private final UserService userService;
    private final BookService bookService;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, OrderItemDao orderItemDao, UserService userService, BookService bookService) {
        this.orderDao = orderDao;
        this.orderItemDao = orderItemDao;
        this.userService = userService;
        this.bookService = bookService;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderDao.getAll().stream().map(this::mapOrderToDto).toList();
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Order order = orderDao.getOrderById(id);
        return mapOrderToDto(order);
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        BigDecimal totalCost = calculateOrderTotalCost(orderDto);
        orderDto.setTotalCost(totalCost);
        Order newOrder = orderDao.createOrder(mapOrderDtoToOrder(orderDto));
        orderDto.getOrderItems().forEach(itemDto -> orderItemDao.createOrderItem(mapItemDtoToItem(newOrder.getId(), itemDto)));
        return getOrderById(newOrder.getId());
    }

    @Override
    public OrderDto updateOrder(OrderDto orderDto) {
        BigDecimal totalCost = calculateOrderTotalCost(orderDto);
        orderDto.setTotalCost(totalCost);
        orderDao.updateOrder(mapOrderDtoToOrder(orderDto));
        orderItemDao.getByOrderId(orderDto.getId()).forEach(item -> orderItemDao.deleteOrderItemById(item.getId()));
        orderDto.getOrderItems().forEach(itemDto -> orderItemDao.createOrderItem(mapItemDtoToItem(orderDto.getId(), itemDto)));
        return getOrderById(orderDto.getId());
    }

    private BigDecimal calculateOrderTotalCost(OrderDto orderDto) {
        List<OrderItemDto> orderItemDtos = orderDto.getOrderItems();
        BigDecimal totalCost = BigDecimal.ZERO;
        for (OrderItemDto itemDto : orderItemDtos) {
            BigDecimal itemCost = itemDto.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            totalCost = totalCost.add(itemCost);
        }
        return totalCost;
    }

    @Override
    public void deleteOrderById(Long id) {
        List<OrderItem> orderItems = orderItemDao.getByOrderId(id);
        for (OrderItem item : orderItems) {
            orderItemDao.deleteOrderItemById(item.getId());
        }
        if (orderDao.deleteById(id)) {
            System.out.println("The order has been successfully removed.");
        } else {
            System.out.println("This order has been removed or does not exist in the list.");
        }
    }

    private OrderDto mapOrderToDto(Order order) {
        UserDto user = userService.getUserById(order.getUserId());
        List<OrderItem> orderItems = orderItemDao.getByOrderId(order.getId());
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUser(user);
        orderDto.setStatus(OrderStatusDto.valueOf(order.getStatus().toString()));
        orderDto.setTotalCost(order.getTotalCost());
        orderDto.setCreateTime(order.getCreateTime());
        orderDto.setUpdateTime(order.getUpdateTime());
        List<OrderItemDto> itemDtos = new ArrayList<>();
        for (OrderItem item : orderItems) {
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setId(item.getId());
            orderItemDto.setBook(bookService.getBookById(item.getBookId()));
            orderItemDto.setQuantity(item.getQuantity());
            orderItemDto.setPrice(item.getPrice());
            itemDtos.add(orderItemDto);
        }
        orderDto.setOrderItems(itemDtos);
        return orderDto;
    }

    private Order mapOrderDtoToOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setUserId(orderDto.getUser().getId());
        order.setStatus(Order.OrderStatus.valueOf(orderDto.getStatus().toString()));
        order.setTotalCost(orderDto.getTotalCost());
        order.setCreateTime(orderDto.getCreateTime());
        order.setUpdateTime(orderDto.getUpdateTime());
        return order;
    }

    private OrderItem mapItemDtoToItem(Long orderDtoId, OrderItemDto itemDto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderDtoId);
        orderItem.setBookId(itemDto.getBook().getId());
        orderItem.setQuantity(itemDto.getQuantity());
        orderItem.setPrice(itemDto.getPrice());
        return orderItem;
    }
}
