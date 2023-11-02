package com.active.authservice.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserLoginRequest {
    private String email;
    private String password;
}
