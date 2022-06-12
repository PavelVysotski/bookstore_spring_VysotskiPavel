package com.belhard.service.impl;

import com.belhard.repository.entity.Order;
import com.belhard.repository.entity.User;
import com.belhard.repository.entity.User.UserRole;
import com.belhard.repository.order.OrderRepository;
import com.belhard.repository.user.UserRepository;
import com.belhard.service.OrderService;
import com.belhard.service.UserService;
import com.belhard.service.dto.user.UserDto;
import com.belhard.service.dto.user.UserDto.UserRoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, @Lazy OrderService orderService, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAllUsers().stream()
                .map(this::userToUserDto)
                .toList();
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.orElseThrow(() -> new RuntimeException("No user with id = " + id));
        return userToUserDto(user);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        return userToUserDto(userRepository.save(userDtoToUser(userDto)));
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        return userToUserDto(userRepository.save(userDtoToUser(userDto)));
    }

    @Override
    public void deleteUserById(Long id) {
        List<Order> orders = orderRepository.findAllOrders();
        orders.forEach(order -> {
            if (order.getUserId().equals(id)) {
                orderService.deleteOrderById(order.getId());
            }
        });
        userRepository.deleteById(id);
    }

    private UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setSecondName(user.getSecondName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRole(UserRoleDto.valueOf(user.getRole().toString()));
        return userDto;
    }

    private User userDtoToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setSecondName(userDto.getSecondName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(UserRole.valueOf(userDto.getRole().toString()));
        return user;
    }
}
