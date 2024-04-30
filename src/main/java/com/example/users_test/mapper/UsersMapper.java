package com.example.users_test.mapper;

import com.example.users_test.dto.RegisterDto;
import com.example.users_test.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    @Mapping(target = "id", ignore = true)
    Users registerDtoToUsers(RegisterDto registerDto);
}
