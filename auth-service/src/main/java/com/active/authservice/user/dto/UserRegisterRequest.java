package com.active.authservice.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegisterRequest {
    @NotNull(message = "Username is required")
    @Min(value = 4, message = "Username must be at least 4 characters")
    @Max(value = 24, message = "Username must be no longer than 24 characters")
    private String username;
    @NotNull(message = "Email is required")
    @Email(message = "Not a valid email")
    private String email;
    @NotNull(message = "Firstname is required")
    @Max(value = 36, message = "Firstname must be no longer than 36 characters")
    private String firstname;
    @NotNull(message = "Lastname is required")
    @Max(value = 36, message = "Lastname must be no longer than 36 characters")
    private String lastname;
    @NotNull(message = "Password is required")
    private String password;
}
