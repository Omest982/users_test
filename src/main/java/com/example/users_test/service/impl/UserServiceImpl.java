package com.example.users_test.service.impl;

import com.example.users_test.dto.RegisterDto;
import com.example.users_test.entity.Users;
import com.example.users_test.exception.AgeRestrictionException;
import com.example.users_test.mapper.UsersMapper;
import com.example.users_test.repository.UsersRepository;
import com.example.users_test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;
    @Value("registration.age")
    private final Integer allowedAge;
    @Override
    public UUID register(RegisterDto registerDto) {
        Users transientUser = usersMapper.registerDtoToUsers(registerDto);

        if (!validateUser(transientUser)){
            throw new AgeRestrictionException("Users is too young to register!");
        }

        return usersRepository.save(transientUser).getId();
    }

    private boolean validateUser(Users user){
        int userBirthYear = user.getBirthDate().getYear();
        int currentYear = LocalDate.now().getYear();

        return currentYear - userBirthYear >= allowedAge;
    }
}
