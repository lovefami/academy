package com.group3.finalprojectbe.system.mapper;

import com.group3.finalprojectbe.system.dto.UserDto;
import com.group3.finalprojectbe.system.entity.Role;
import com.group3.finalprojectbe.system.entity.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 */
@Component
public class UserMapper implements Function<User, UserDto> {
    @Override
    public UserDto apply(User user) {
        return new UserDto(user.getId(),user.getUsername(), user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getEmail(), user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
    }
}
