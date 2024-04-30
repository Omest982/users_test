package com.example.users_test.controller;

import com.example.users_test.dto.RegisterDto;
import com.example.users_test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;

    @PostMapping
    public UUID registerUser(@RequestBody RegisterDto registerDto){
        return  userService.register(registerDto);
    }
}
