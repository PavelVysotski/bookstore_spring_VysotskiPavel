package com.belhard.controller.command.impl;

import com.belhard.controller.command.Command;
import com.belhard.service.UserService;
import com.belhard.service.dto.user.UserDto;
import com.belhard.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class UsersCommand implements Command {

    private final UserService USER_SERVICE = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest req) {
        List<UserDto> users = USER_SERVICE.getAllUsers();
        req.setAttribute("users", users);
        return "jsp/users.jsp";
    }
}
