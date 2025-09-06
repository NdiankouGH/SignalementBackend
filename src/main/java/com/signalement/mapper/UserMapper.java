package com.signalement.mapper;

import com.signalement.dto.UserDto;
import com.signalement.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User fromUserDto(UserDto userDto);

    UserDto toUserDto(User user);
}
