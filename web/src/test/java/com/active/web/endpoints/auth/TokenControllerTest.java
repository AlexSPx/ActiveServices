package com.active.web.endpoints.auth;

import com.active.authservice.token.TokenPair;
import com.active.authservice.token.TokenService;
import com.active.authservice.user.UserService;
import com.active.models.User;
import com.active.web.dto.auth.RefreshToken;
import com.active.web.endpoints.TestConfigurations;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {TokenController.class, TestConfigurations.class})
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TokenControllerTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    private final UserService userService;
    private final TokenService tokenService;

    private TokenPair userTokenPair;

    @BeforeEach
    void setup() {
        userTokenPair = userService.createUser(User.builder()
                .email("user@mail.com")
                .username("user")
                .firstname("User")
                .lastname("User")
                .password("password")
                .build()
        );
    }

    @Test
    void refreshTokenSuccessTest() throws Exception {
        RefreshToken refreshToken = new RefreshToken(userTokenPair.getRefresh());

        String responseString = mockMvc.perform(post("/api/auth/token/refresh")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(refreshToken)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TokenPair tokenPair = objectMapper.readValue(responseString, TokenPair.class);
        assertThat(tokenPair.getToken()).isNotNull();
        assertThat(tokenPair.getRefresh()).isNotNull();

        assertThat(tokenService.validateToken(tokenPair.getToken())).isNotNull();
    }
}
