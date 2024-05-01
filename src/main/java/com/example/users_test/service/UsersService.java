package com.example.users_test.service;

import com.example.users_test.dto.RegisterDto;
import com.example.users_test.dto.SearchDto;
import com.example.users_test.dto.UserDto;
import com.example.users_test.dto.UserUpdateDto;

import java.util.List;
import java.util.UUID;

public interface UsersService {
    UserDto register(RegisterDto registerDto);

    void deleteUserById(UUID id);

    UserDto updateUser(UUID userId, UserUpdateDto userUpdateDto);

    List<UserDto> searchUsers(SearchDto searchDto);
}
