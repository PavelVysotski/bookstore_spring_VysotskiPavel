package com.belhard.service;

import com.belhard.service.dto.user.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    UserDto createUser(UserDto userDto);

    UserDto updateUserById(UserDto userDto);

    void deleteUserById(Long id);
}
