package com.example.users_test.service;

import com.example.users_test.dto.RegisterDto;
import com.example.users_test.dto.UserDto;
import com.example.users_test.entity.Users;
import com.example.users_test.exception.SearchException;
import com.example.users_test.mapper.UsersMapper;
import com.example.users_test.repository.UsersRepository;
import com.example.users_test.service.impl.UsersServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {
    @Mock
    private UsersRepository usersRepository;

    @Mock
    private UsersMapper usersMapper;

    @InjectMocks
    private UsersServiceImpl usersService;

    @Test
    void registerUserWithFullInfo_Success(){
        // Given
        RegisterDto registerDto = RegisterDto.builder()
                .email("Test@test.com")
                .address("Test")
                .name("Test")
                .lastName("Test")
                .birthDate(LocalDate.of(2004, Month.DECEMBER, 10))
                .phoneNumber("9012")
                .build();

        Users user = Users.builder()
                .id(UUID.randomUUID())
                .email("Test@test.com")
                .address("Test")
                .name("Test")
                .lastName("Test")
                .birthDate(LocalDate.of(2004, Month.DECEMBER, 10))
                .phoneNumber("9012")
                .build();

        UserDto userDto = UserDto.builder()
                .id(UUID.randomUUID())
                .email("Test@test.com")
                .address("Test")
                .name("Test")
                .lastName("Test")
                .birthDate(LocalDate.of(2004, Month.DECEMBER, 10))
                .phoneNumber("9012")
                .build();

        given(usersMapper.toUser(registerDto)).willReturn(user);
        given(usersRepository.save(user)).willReturn(user);
        given(usersMapper.toUserDto(user)).willReturn(userDto);

        // When
        UserDto result = usersService.register(registerDto);

        // Then
        assertThat(result).isEqualTo(userDto);
        verify(usersRepository).save(user);
        verify(usersMapper).toUser(registerDto);
        verify(usersMapper).toUserDto(user);

    }

    @Test
    void searchUsers_Success() {
        // Given
        LocalDate from = LocalDate.of(2020, Month.DECEMBER, 1);
        LocalDate to = LocalDate.of(2020, Month.DECEMBER, 31);

        List<Users> foundUsers = Arrays.asList(
                new Users(UUID.randomUUID(), "test1@test.com", "Test", "User1", LocalDate.of(2020, Month.DECEMBER, 10), "Address1", "Phone1"),
                new Users(UUID.randomUUID(), "test2@test.com", "Test", "User2", LocalDate.of(2020, Month.DECEMBER, 15), "Address2", "Phone2")
        );
        List<UserDto> expectedDtos = foundUsers.stream()
                .map(user -> new UserDto(user.getId(), user.getEmail(), user.getName(), user.getLastName(), user.getBirthDate(), user.getAddress(), user.getPhoneNumber()))
                .collect(Collectors.toList());

        given(usersRepository.findByBirthDateRange(from, to)).willReturn(foundUsers);
        given(usersMapper.toUserDto(any(Users.class))).willAnswer(invocation -> {
            Users user = invocation.getArgument(0);
            return new UserDto(user.getId(), user.getEmail(), user.getName(), user.getLastName(), user.getBirthDate(), user.getAddress(), user.getPhoneNumber());
        });

        // When
        List<UserDto> result = usersService.searchUsers(from, to);

        // Then
        assertThat(result).hasSize(foundUsers.size()).usingRecursiveComparison().isEqualTo(expectedDtos);
        verify(usersRepository).findByBirthDateRange(from, to);
        verify(usersMapper, times(foundUsers.size())).toUserDto(any(Users.class));
    }

    @Test
    void searchUsers_InvalidDateRange_Exception() {
        // Given
        LocalDate from = LocalDate.of(2020, Month.DECEMBER, 31);
        LocalDate to = LocalDate.of(2020, Month.DECEMBER, 1);

        // When
        assertThrows(SearchException.class,
                () -> usersService.searchUsers(from, to),
                "Expected searchUsers to throw due to invalid date range");

        // Then
        verify(usersRepository, never()).findByBirthDateRange(any(LocalDate.class), any(LocalDate.class));
        verify(usersMapper, never()).toUserDto(any(Users.class));
    }

    @Test
    void searchUsers_DateRangeIsInFuture_Exception() {
        // Given
        LocalDate from = LocalDate.of(3020, Month.DECEMBER, 31);
        LocalDate to = LocalDate.of(3020, Month.DECEMBER, 1);

        // When
        assertThrows(SearchException.class,
                () -> usersService.searchUsers(from, to),
                "Expected searchUsers to throw due to invalid date range");

        // Then
        verify(usersRepository, never()).findByBirthDateRange(any(LocalDate.class), any(LocalDate.class));
        verify(usersMapper, never()).toUserDto(any(Users.class));
    }
}
