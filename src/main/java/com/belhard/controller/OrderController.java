package com.belhard.controller;

import com.belhard.service.BookService;
import com.belhard.service.OrderService;
import com.belhard.service.UserService;
import com.belhard.service.dto.book.BookDto;
import com.belhard.service.dto.order.OrderDto;
import com.belhard.service.dto.order.OrderItemDto;
import com.belhard.service.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final BookService bookService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, BookService bookService, UserService userService) {
        this.orderService = orderService;
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping("/byId/{id}")
    public String getOrderById(Model model, @PathVariable Long id) {
        OrderDto order = orderService.getOrderById(id);
        UserDto user = order.getUser();
        List<OrderItemDto> orderItems = order.getOrderItems();
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("user", user);
        model.addAttribute("order", order);
        return "order/order";
    }

    @GetMapping
    public String getAllOrders(Model model) {
        List<OrderDto> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "order/orders-list";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return "redirect:/orders";
    }

    @GetMapping("/addOrder")
    public String addOrderForm(Model model) {
        List<BookDto> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "order/create-order";
    }

    @PostMapping("/createOrder")
    public String addNewOrder(@RequestParam(name = "bookId") List<Long> allBookId,
                              @RequestParam(name = "quantity") List<Integer> allBookQuantity) {
        Map<Long, Integer> params = new HashMap<>();
        for (int i = 0; i < allBookId.size(); i++) {
            if (allBookQuantity.get(i) == 0) {
                continue;
            } else {
                params.put(allBookId.get(i), allBookQuantity.get(i));
            }
        }
        OrderDto newOrder = new OrderDto();
        List<OrderItemDto> newItemsDto = new ArrayList<>();
        for (Map.Entry paramItem : params.entrySet()) {
            OrderItemDto item = new OrderItemDto();
            item.setBook(bookService.getBookById((Long) paramItem.getKey()));
            item.setQuantity((Integer) paramItem.getValue());
            item.setPrice(bookService.getBookById((Long) paramItem.getKey()).getPrice());
            newItemsDto.add(item);
        }
        List<UserDto> users = userService.getAllUsers();
        int randomIndex = (int) (Math.random() * users.size());
        newOrder.setUser(users.get(randomIndex));
        newOrder.setOrderItems(newItemsDto);
        newOrder.setStatus(OrderDto.OrderStatusDto.RESERVED);
        orderService.createOrder(newOrder);
        return "redirect:/orders";
    }

    @GetMapping("/updateOrder/{id}")
    public String updateUserForm(Model model, @PathVariable Long id) {
        OrderDto order = orderService.getOrderById(id);
        List<BookDto> books = bookService.getAllBooks();
        List<OrderItemDto> orderItems = order.getOrderItems();
        List<OrderItemDto> itemsForUpdate = new ArrayList<>();
        for (BookDto book : books) {
            OrderItemDto itemTemp = new OrderItemDto();
            for (OrderItemDto item : orderItems) {
                itemTemp.setBook(book);
                itemTemp.setPrice(book.getPrice());
                if (book.getIsbn().equals(item.getBook().getIsbn())) {
                    itemTemp.setQuantity(item.getQuantity());
                    break;
                } else {
                    itemTemp.setQuantity(0);
                }
            }
            itemsForUpdate.add(itemTemp);
        }
        model.addAttribute("books", books);
        model.addAttribute("itemsForUpdate", itemsForUpdate);
        model.addAttribute("order", order);
        return "order/update-order";
    }

    @PostMapping("/updateOrder")
    public String updateUser(@RequestParam(name = "bookId") List<Long> allBookId,
                             @RequestParam(name = "quantity") List<Integer> allBookQuantity,
                             @RequestParam(name = "id") Long orderId) {
        Map<Long, Integer> params = new HashMap<>();
        for (int i = 0; i < allBookId.size(); i++) {
            if (allBookQuantity.get(i) == 0) {
                continue;
            } else {
                params.put(allBookId.get(i), allBookQuantity.get(i));
            }
        }
        OrderDto updateOrder = new OrderDto();
        List<OrderItemDto> newItemsDto = new ArrayList<>();
        for (Map.Entry paramItem : params.entrySet()) {
            OrderItemDto item = new OrderItemDto();
            item.setBook(bookService.getBookById((Long) paramItem.getKey()));
            item.setQuantity((Integer) paramItem.getValue());
            item.setPrice(bookService.getBookById((Long) paramItem.getKey()).getPrice());
            newItemsDto.add(item);
        }
        updateOrder.setId(orderId);
        updateOrder.setUser(orderService.getOrderById(orderId).getUser());
        updateOrder.setCreateTime(orderService.getOrderById(orderId).getCreateTime());
        updateOrder.setUpdateTime(LocalDateTime.now());
        updateOrder.setStatus(orderService.getOrderById(orderId).getStatus());
        updateOrder.setOrderItems(newItemsDto);
        orderService.updateOrder(updateOrder);
        return "redirect:/orders";
    }
}
