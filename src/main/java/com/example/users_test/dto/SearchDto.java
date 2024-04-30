package com.example.users_test.dto;

import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record SearchDto(
        @Past
        LocalDate from,
        @Past
        LocalDate to
) {
}
