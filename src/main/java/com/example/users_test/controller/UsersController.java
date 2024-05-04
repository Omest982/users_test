package com.example.users_test.controller;

import com.example.users_test.dto.RegisterDto;
import com.example.users_test.dto.UserDto;
import com.example.users_test.dto.UserUpdateDto;
import com.example.users_test.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;

    @PostMapping
    public UserDto registerUser(@RequestBody @Valid RegisterDto registerDto){
        return usersService.register(registerDto);
    }

    @PutMapping("/{id}")
    public UserDto updateUserById(@PathVariable UUID id,
                                @RequestBody @Valid UserUpdateDto userUpdateDto){
        return usersService.updateUser(id, userUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable UUID id){
        usersService.deleteUserById(id);
    }

    @GetMapping("/search")
    public List<UserDto> searchUsers (@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to){
        return usersService.searchUsers(from, to);
    }

}
