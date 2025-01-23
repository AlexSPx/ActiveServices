package com.active.web.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    @NotBlank(message = "Refresh token cannot be blank")
    private String refreshToken;
}
