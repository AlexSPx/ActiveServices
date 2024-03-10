package com.active.authservice.token.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    @NotNull(message = "Refresh token is required")
    private String refreshToken;
}
