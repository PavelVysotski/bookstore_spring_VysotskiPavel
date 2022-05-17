package com.belhard.service.impl;

import com.belhard.dao.entity.User;
import com.belhard.dao.user.UserDao;
import com.belhard.service.UserService;
import com.belhard.service.dto.user.UserDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userDao.getAllUsers().stream()
                .map(this::userToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        return userToUserDto(userDao.getUserById(id));
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        return userToUserDto(userDao.createUser(userDtoToUser(userDto)));
    }

    @Override
    public UserDto updateUserById(UserDto userDto) {
        return userToUserDto(userDao.updateUser(userDtoToUser(userDto)));
    }

    @Override
    public void deleteUserById(Long id) {
        if (!userDao.deleteUserById(id)) {
            throw new RuntimeException("This user has been inactive or does not exist in the list.");
        }
    }

    private UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setSecondName(user.getSecondName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRole(UserDto.UserRoleDto.valueOf(user.getRole().toString()));
        return userDto;
    }

    private User userDtoToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setSecondName(userDto.getSecondName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(User.UserRole.valueOf(userDto.getRole().toString()));
        return user;
    }
}
