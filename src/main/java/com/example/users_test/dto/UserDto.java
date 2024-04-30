package com.example.users_test.dto;

import java.time.LocalDate;
import java.util.UUID;

public record UserDto(
        UUID id,
        String email,
        String name,
        String lastName,
        LocalDate birthDate,
        String address,
        String phoneNumber
) {
}
