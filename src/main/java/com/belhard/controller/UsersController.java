package com.belhard.controller;

import com.belhard.service.UserService;
import com.belhard.service.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users-list";
    }

    @GetMapping("/byId/{id}")
    public String getUserById(Model model, @PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/addUser")
    public String addUserForm() {
        return "create-user";
    }

    @PostMapping("/createUser")
    public String addNewUser(@RequestParam Map<String, Object> params) {
        UserDto newUser = new UserDto();
        newUser.setName(String.valueOf(params.get("name")));
        newUser.setSecondName(String.valueOf(params.get("secondName")));
        newUser.setEmail(String.valueOf(params.get("email")));
        newUser.setPassword(String.valueOf(params.get("password")));
        newUser.setRole(UserDto.UserRoleDto.valueOf(String.valueOf(params.get("role")).toUpperCase(Locale.ROOT)));
        userService.createUser(newUser);
        return "redirect:/users";
    }

    @GetMapping("/updateUser/{id}")
    public String updateUserForm(Model model, @PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/updateUser")
    public String updateUser(@RequestParam Map<String, Object> params) {
        UserDto updateUser = new UserDto();
        updateUser.setId(Long.parseLong(String.valueOf(params.get("id"))));
        updateUser.setName(String.valueOf(params.get("name")));
        updateUser.setSecondName(String.valueOf(params.get("secondName")));
        updateUser.setEmail(String.valueOf(params.get("email")));
        updateUser.setPassword(String.valueOf(params.get("password")));
        updateUser.setRole(UserDto.UserRoleDto.valueOf(String.valueOf(params.get("role")).toUpperCase(Locale.ROOT)));
        userService.updateUser(updateUser);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }
}
