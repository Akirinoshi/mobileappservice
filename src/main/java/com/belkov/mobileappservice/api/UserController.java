package com.belkov.mobileappservice.api;

import com.belkov.mobileappservice.model.User;
import com.belkov.mobileappservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequestMapping("api/v1/user")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/register")
    public User registerUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PostMapping(path = "/login")
    public User loginUser(@RequestBody User user) {
        return userService.loginUser(user);
    }
}
