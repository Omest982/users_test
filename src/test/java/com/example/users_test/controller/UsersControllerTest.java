package com.example.users_test.controller;

import com.example.users_test.dto.RegisterDto;
import com.example.users_test.dto.UserDto;
import com.example.users_test.dto.UserUpdateDto;
import com.example.users_test.exception.AgeRestrictionException;
import com.example.users_test.exception.CustomExceptionHandler;
import com.example.users_test.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {UsersController.class, CustomExceptionHandler.class})
public class UsersControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsersService usersService;

    @Test
    public void registerUser_withValidData_returnsUserDtoAsJson() throws Exception {

        UUID userId = UUID.randomUUID();

        RegisterDto registerDto = RegisterDto.builder()
                .email("Test@test.com")
                .address("Test")
                .name("Test")
                .lastName("Test")
                .birthDate(LocalDate.of(2004, Month.DECEMBER, 10))
                .phoneNumber("9012")
                .build();

        UserDto userDto = UserDto.builder()
                .id(userId)
                .email("Test@test.com")
                .address("Test")
                .name("Test")
                .lastName("Test")
                .birthDate(LocalDate.of(2004, Month.DECEMBER, 10))
                .phoneNumber("9012")
                .build();

        given(usersService.register(registerDto)).willReturn(userDto);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(userDto.name())))
                .andExpect(jsonPath("$.id", is(userDto.id().toString())))
                .andExpect(jsonPath("$.birthDate", is("2004-12-10")));
    }

    @Test
    public void registerUser_whenDataInvalid_thenThrowsException() throws Exception {
        RegisterDto registerDto = RegisterDto.builder()
                .email("Test@test.com")
                .address("Test")
                .name("Test")
                .lastName("Test")
                .birthDate(LocalDate.of(2004, Month.DECEMBER, 10))
                .phoneNumber("9012")
                .build();

        String exceptionMsg = "Age is too low!";
        given(usersService.register(any())).willThrow(new AgeRestrictionException(exceptionMsg));

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> Assertions.assertInstanceOf(AgeRestrictionException.class, result.getResolvedException()))
                .andExpect(result -> Assertions.assertEquals(exceptionMsg, result.getResolvedException().getMessage()));
    }

    @Test
    public void updateUser_withValidIdAndData_returnsUpdatedUserDtoAsJson() throws Exception {

        UUID userId = UUID.randomUUID();

        UserUpdateDto updateDto = UserUpdateDto.builder()
                .email("Test@test.com")
                .address("Test")
                .name("Updated")
                .lastName("Test")
                .birthDate(LocalDate.of(2004, Month.DECEMBER, 10))
                .phoneNumber("9012")
                .build();

        UserDto userDto = UserDto.builder()
                .id(userId)
                .email("Test@test.com")
                .address("Test")
                .name("Updated")
                .lastName("Test")
                .birthDate(LocalDate.of(2004, Month.DECEMBER, 10))
                .phoneNumber("9012")
                .build();

        given(usersService.updateUser(userId, updateDto)).willReturn(userDto);

        mvc.perform(put("/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(userDto.name())))
                .andExpect(jsonPath("$.id", is(userDto.id().toString())))
                .andExpect(jsonPath("$.birthDate", is("2004-12-10")));
    }

}
