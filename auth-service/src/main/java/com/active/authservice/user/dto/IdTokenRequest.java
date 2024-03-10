package com.active.authservice.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IdTokenRequest {
    @NotNull(message = "token is required")
    String idToken;
}
