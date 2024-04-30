package com.example.users_test.service;

import com.example.users_test.dto.RegisterDto;

import java.util.UUID;

public interface UserService {
    UUID register(RegisterDto registerDto);
}
