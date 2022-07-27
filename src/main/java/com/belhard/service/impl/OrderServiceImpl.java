package com.belhard.service.impl;

import com.belhard.repository.entity.Order;
import com.belhard.repository.entity.OrderItem;
import com.belhard.repository.order.OrderItemRepository;
import com.belhard.repository.order.OrderRepository;
import com.belhard.service.BookService;
import com.belhard.service.OrderService;
import com.belhard.service.UserService;
import com.belhard.service.dto.order.OrderDto;
import com.belhard.service.dto.order.OrderDto.OrderStatusDto;
import com.belhard.service.dto.order.OrderItemDto;
import com.belhard.service.dto.user.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final BookService bookService;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAllOrders().stream()
                .map(this::mapOrderToDto)
                .toList();
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        Order order = orderOptional.orElseThrow(() -> new RuntimeException("No order with id = " + id));
        return mapOrderToDto(order);
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        BigDecimal totalCost = calculateOrderTotalCost(orderDto);
        orderDto.setTotalCost(totalCost);
        Order newOrder = orderRepository.save(mapOrderDtoToOrder(orderDto));
        orderDto.getOrderItems().forEach(itemDto -> orderItemRepository.save(mapItemDtoToItem(newOrder.getId(), itemDto)));
        return getOrderById(newOrder.getId());
    }

    @Override
    public OrderDto updateOrder(OrderDto orderDto) {
        BigDecimal totalCost = calculateOrderTotalCost(orderDto);
        orderDto.setTotalCost(totalCost);
        orderRepository.save(mapOrderDtoToOrder(orderDto));
        orderItemRepository.findOrderItemByOrderId(orderDto.getId()).forEach(item -> orderItemRepository.deleteById(item.getId()));
        orderDto.getOrderItems().forEach(itemDto -> orderItemRepository.save(mapItemDtoToItem(orderDto.getId(), itemDto)));
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
        List<OrderItem> orderItems = orderItemRepository.findOrderItemByOrderId(id);
        for (OrderItem item : orderItems) {
            orderItemRepository.deleteById(item.getId());
        }
        orderRepository.deleteById(id);
    }

    private OrderDto mapOrderToDto(Order order) {
        UserDto user = userService.getUserById(order.getUserId());
        List<OrderItem> orderItems = orderItemRepository.findOrderItemByOrderId(order.getId());
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
