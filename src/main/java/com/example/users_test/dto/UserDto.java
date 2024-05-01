package com.example.users_test.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
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
