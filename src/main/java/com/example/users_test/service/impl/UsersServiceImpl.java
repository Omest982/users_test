package com.example.users_test.service.impl;

import com.example.users_test.dto.RegisterDto;
import com.example.users_test.dto.SearchDto;
import com.example.users_test.dto.UserDto;
import com.example.users_test.dto.UserUpdateDto;
import com.example.users_test.entity.Users;
import com.example.users_test.exception.AgeRestrictionException;
import com.example.users_test.exception.SearchException;
import com.example.users_test.mapper.UsersMapper;
import com.example.users_test.repository.UsersRepository;
import com.example.users_test.service.UsersService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;
    @Value("${registration.age}")
    private int allowedAge;

    @Override
    @Transactional
    public UserDto register(RegisterDto registerDto) {
        Users transientUser = usersMapper.toUser(registerDto);

        if (!validateUser(transientUser)){
            throw new AgeRestrictionException("User is too young to register!");
        }

        Users persistentUser = usersRepository.save(transientUser);

        return usersMapper.toUserDto(persistentUser);
    }

    @Override
    public void deleteUserById(UUID id) {
        usersRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserDto updateUser(UUID userId, UserUpdateDto userUpdateDto) {
        Users user = usersRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        "User with id %s not found!", userId
                ))
        );

        usersMapper.updateUserFromDto(userUpdateDto, user);

        return usersMapper.toUserDto(usersRepository.save(user));
    }

    @Override
    public List<UserDto> searchUsers(SearchDto searchDto) {
        if (!searchDto.from().isBefore(searchDto.to())){
            throw new SearchException("Invalid date!");
        }

        List<Users> users = usersRepository.findByBirthDateRange(searchDto.from(), searchDto.to());

        return users.stream()
                .map(usersMapper::toUserDto)
                .collect(Collectors.toList());
    }

    private boolean validateUser(Users user){
        int userBirthYear = user.getBirthDate().getYear();
        int currentYear = LocalDate.now().getYear();

        return currentYear - userBirthYear >= allowedAge;
    }
}
