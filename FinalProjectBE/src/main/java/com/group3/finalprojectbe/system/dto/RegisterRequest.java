package com.group3.finalprojectbe.system.dto;


import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class RegisterRequest {

    private String username;


    private String firstName;


    private String lastName;
    @NotNull(message = "Please enter a phone-number")
    @NotEmpty(message = "Please enter a phone-number")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Please enter a valid phone number")
    private String phoneNumber;
    @NotNull(message = "Please enter a password")
    @Size(min = 6, max=20,message = "Password should be more than 6 characters or numbers")

    private String password;
    @Email(message = "Please enter a correct email")
    private String email;
}
