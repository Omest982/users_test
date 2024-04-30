package com.example.users_test.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record UserUpdateDto(
        @NotBlank
        @Email(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
        String email,
        @NotBlank
        String name,
        @NotBlank
        String lastName,
        @NotNull
        @Past
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate birthDate,
        String address,
        String phoneNumber
) {
}
