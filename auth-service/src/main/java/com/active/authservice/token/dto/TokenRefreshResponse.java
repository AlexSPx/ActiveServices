package com.active.authservice.token.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TokenRefreshResponse {
    @NotNull(message = "Token token is required")
    private String token;
}
