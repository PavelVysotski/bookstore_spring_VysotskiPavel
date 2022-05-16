package com.belhard.controller.command.impl;

import com.belhard.controller.command.Command;
import com.belhard.service.UserService;
import com.belhard.service.dto.user.UserDto;
import com.belhard.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class UserCommand implements Command {

    private final UserService USER_SERVICE = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.valueOf(req.getParameter("id"));
        UserDto user = USER_SERVICE.getUserById(id);
        if (user == null) {
            req.setAttribute("message", "No user with id = " + id);
            return "jsp/error.jsp";
        }
        req.setAttribute("user", user);
        return "jsp/user.jsp";
    }
}
