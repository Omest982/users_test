package com.example.users_test.mapper;

import com.example.users_test.dto.RegisterDto;
import com.example.users_test.dto.UserDto;
import com.example.users_test.dto.UserUpdateDto;
import com.example.users_test.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    UserDto toUserDto(Users user);

    @Mapping(target = "id", ignore = true)
    Users toUser(RegisterDto registerDto);

    @Mapping(target = "id", ignore = true)
    void updateUserFromDto(UserUpdateDto dto, @MappingTarget Users user);
}
