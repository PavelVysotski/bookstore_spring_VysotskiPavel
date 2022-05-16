package com.belhard.dao.user;

import com.belhard.dao.entity.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();

    User getUserById(Long id);

    User createUser(User newUser);

    User updateUser(User updateUser);

    boolean deleteUserById(Long id);
}
