package com.active.authservice.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConnectGoogleAccountRequest {
    @NotNull(message = "Token is required")
    private String token;
    @NotNull(message = "google token is required")
    private String gidToken;
}
