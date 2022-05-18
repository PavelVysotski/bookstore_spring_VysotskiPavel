package com.belhard.controller.command.impl;

import com.belhard.controller.command.Command;
import com.belhard.service.UserService;
import com.belhard.service.dto.user.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCommand implements Command {

    private final UserService userService;

    @Autowired
    public UserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.valueOf(req.getParameter("id"));
        UserDto user = userService.getUserById(id);
        if (user == null) {
            req.setAttribute("message", "No user with id = " + id);
            return "jsp/error.jsp";
        }
        req.setAttribute("user", user);
        return "jsp/user.jsp";
    }
}
