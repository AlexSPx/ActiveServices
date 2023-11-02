package com.active.authservice.token.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RefreshTokenModel {
    private String jti;
    private String sub;
    private long exp;
    private String iss;
}
