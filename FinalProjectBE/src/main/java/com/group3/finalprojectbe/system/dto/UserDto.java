package com.group3.finalprojectbe.system.dto;

import jakarta.validation.constraints.Email;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.group3.finalprojectbe.system.entity.User}
 */
public record UserDto(Long userId, String username, String firstName, String lastName, String phoneNumber,
                      @Email String email,
                      Set<String> roleName) implements Serializable {
}