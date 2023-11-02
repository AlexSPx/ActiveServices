package com.active.authservice.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TokenPairResponse {
    private String token;
    private String refresh;
}
