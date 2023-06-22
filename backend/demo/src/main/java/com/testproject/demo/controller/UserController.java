package com.testproject.demo.controller;

import com.testproject.demo.model.User;
import com.testproject.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/register")
    public int register(@RequestBody User user){
        userService.save(user.getLogin(), user.getPasswordSha());
        return 0;
    }

    @PostMapping("/login")
    public User login(@RequestBody User user){
        return userService.login(user.getLogin(), user.getPasswordSha());
    }
}
