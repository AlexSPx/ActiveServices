package com.active.authservice.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegisterRequest {
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String password;
}
