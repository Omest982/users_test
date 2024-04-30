package com.example.users_test.controller;

import com.example.users_test.dto.RegisterDto;
import com.example.users_test.dto.SearchDto;
import com.example.users_test.dto.UserDto;
import com.example.users_test.dto.UserUpdateDto;
import com.example.users_test.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;

    @PostMapping
    public UserDto registerUser(@RequestBody @Valid RegisterDto registerDto){
        return userService.register(registerDto);
    }

    @PutMapping("/{id}")
    public UserDto updateUserById(@PathVariable UUID id,
                                @RequestBody @Valid UserUpdateDto userUpdateDto){
        return userService.updateUser(id, userUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable UUID id){
        userService.deleteUserById(id);
    }

    @PostMapping("/search")
    public List<UserDto> searchUsers (@RequestBody @Valid SearchDto searchDto){
        return userService.searchUsers(searchDto);
    }

}
