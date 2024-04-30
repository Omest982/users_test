package com.example.users_test.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterDto {
    private String email;
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;
}
