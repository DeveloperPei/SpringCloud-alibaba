package com.example.usercenter.controller.user;

import com.example.usercenter.domain.entity.user.User;
import com.example.usercenter.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("{id}")
    public User findById (@PathVariable("id") Integer id){
       return   userService.findById(id);
    }
}
