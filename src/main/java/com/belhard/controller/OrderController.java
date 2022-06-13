package com.belhard.controller;

import com.belhard.service.BookService;
import com.belhard.service.OrderService;
import com.belhard.service.UserService;
import com.belhard.service.dto.book.BookDto;
import com.belhard.service.dto.order.OrderDto;
import com.belhard.service.dto.order.OrderItemDto;
import com.belhard.service.dto.user.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final BookService bookService;
    private final UserService userService;

    @GetMapping("/{id}")
    public ModelAndView getOrderById(Model model, @PathVariable Long id) {
        OrderDto order = orderService.getOrderById(id);
        UserDto user = order.getUser();
        List<OrderItemDto> orderItems = order.getOrderItems();
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("user", user);
        model.addAttribute("order", order);
        return new ModelAndView("order/order");
    }

    @GetMapping
    public ModelAndView getAllOrders(Model model) {
        List<OrderDto> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return new ModelAndView("order/orders-list");
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return new ModelAndView("redirect:/api/orders");
    }

    @GetMapping("/create")
    public ModelAndView addOrderForm(Model model) {
        List<BookDto> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return new ModelAndView("order/create-order");
    }

    @PostMapping("/createOrder")
    public ModelAndView addNewOrder(@RequestParam(name = "bookId") List<Long> allBookId,
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
        Long userId = users.get(randomIndex).getId();
        newOrder.setUser(userService.getUserById(userId));
        newOrder.setOrderItems(newItemsDto);
        newOrder.setStatus(OrderDto.OrderStatusDto.RESERVED);
        orderService.createOrder(newOrder);
        return new ModelAndView("redirect:/api/orders");
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateUserForm(Model model, @PathVariable Long id) {
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
        return new ModelAndView("order/update-order");
    }

    @PostMapping("/updateOrder")
    public ModelAndView updateUser(@RequestParam(name = "bookId") List<Long> allBookId,
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
        updateOrder.setUpdateTime(new Date());
        updateOrder.setStatus(orderService.getOrderById(orderId).getStatus());
        updateOrder.setOrderItems(newItemsDto);
        orderService.updateOrder(updateOrder);
        return new ModelAndView("redirect:/api/orders");
    }
}
