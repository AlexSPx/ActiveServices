package com.active.authservice.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TokenPair {
    private String token;
    private String refresh;
}
