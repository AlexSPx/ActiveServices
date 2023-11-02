package com.active.authservice.token.model;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenModel {
    private String sub;
    private LocalDateTime creaatedOn;
    private String iss;
    private LocalDateTime exp;
}
