package com.belhard.controller.command.impl;

import com.belhard.controller.command.Command;
import com.belhard.service.UserService;
import com.belhard.service.dto.user.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsersCommand implements Command {

    private final UserService userService;

    @Autowired
    public UsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        List<UserDto> users = userService.getAllUsers();
        req.setAttribute("users", users);
        return "jsp/users.jsp";
    }
}
