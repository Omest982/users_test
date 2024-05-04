package com.example.users_test.service;

import com.example.users_test.dto.RegisterDto;
import com.example.users_test.exception.AgeRestrictionException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceIntegrationTest {

    @Autowired
    private UsersService usersService;

    @Test
    void registerUserUnder18Years_Exception() {
        // Given
        RegisterDto registerDto = RegisterDto.builder()
                .email("Test@test.com")
                .address("Test")
                .name("Test")
                .lastName("Test")
                .birthDate(LocalDate.now())
                .phoneNumber("9012")
                .build();

        // When
        assertThrows(AgeRestrictionException.class,
                () -> usersService.register(registerDto),
                "Expected register to throw due to invalid age");
    }

}
