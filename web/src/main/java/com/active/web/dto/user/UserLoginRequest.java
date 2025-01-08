package com.active.web.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {
    @NotNull(message = "Email is required")
    @Email(message = "Not a valid email")
    private String email;
    @NotNull(message = "Password is required")
    private String password;
}
