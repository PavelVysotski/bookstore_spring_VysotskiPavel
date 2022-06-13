package com.belhard.controller;

import com.belhard.service.UserService;
import com.belhard.service.dto.user.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UsersController {

    private final UserService userService;

    @GetMapping
    public ModelAndView getAllUsers(Model model) {
        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return new ModelAndView("user/users-list");
    }

    @GetMapping("/{id}")
    public ModelAndView getUserById(Model model, @PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        model.addAttribute("user", user);
        return new ModelAndView("user/user");
    }

    @GetMapping("/create")
    public ModelAndView addUserForm() {
        return new ModelAndView("user/create-user");
    }

    @PostMapping("/createUser")
    public ModelAndView addNewUser(Model model, @RequestParam Map<String, Object> params) {
        UserDto newUser = new UserDto();
        settingNewData(params, newUser);
        UserDto user = userService.createUser(newUser);
        model.addAttribute("user", user);
        return new ModelAndView("user/user");
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateUserForm(Model model, @PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        model.addAttribute("user", user);
        return new ModelAndView("user/update-user");
    }

    @PostMapping("/updateUser")
    public ModelAndView updateUser(@RequestParam Map<String, Object> params) {
        UserDto updateUser = new UserDto();
        updateUser.setId(Long.parseLong(String.valueOf(params.get("id"))));
        settingNewData(params, updateUser);
        userService.updateUser(updateUser);
        return new ModelAndView("redirect:/api/users");
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ModelAndView("redirect:/api/users");
    }

    private void settingNewData(Map<String, Object> params, UserDto newUser) {
        newUser.setName(String.valueOf(params.get("name")));
        newUser.setSecondName(String.valueOf(params.get("secondName")));
        newUser.setEmail(String.valueOf(params.get("email")));
        newUser.setPassword(String.valueOf(params.get("password")));
        newUser.setRole(UserDto.UserRoleDto.valueOf(String.valueOf(params.get("role")).toUpperCase(Locale.ROOT)));
    }
}
