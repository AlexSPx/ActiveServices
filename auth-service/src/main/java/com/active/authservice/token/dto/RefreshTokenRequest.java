package com.active.authservice.token.dto;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}
