package com.active.web.endpoints.auth;

import com.active.authservice.token.TokenPair;
import com.active.authservice.token.TokenService;
import com.active.models.User;
import com.active.repository.UserRepository;
import com.active.web.dto.user.UserLoginRequest;
import com.active.web.dto.user.UserRegisterRequest;
import com.active.web.endpoints.TestConfigurations;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {UserController.class, TestConfigurations.class})
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void userRegisterSuccessTest() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest(
                "userA", "usera@mail.com", "user", "A", "password");

        String responseString = mockMvc.perform(post("/api/auth/user")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userRegisterRequest)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        TokenPair tokenPair = objectMapper.readValue(responseString, TokenPair.class);
        assertNotNull(tokenPair.getToken());
        assertNotNull(tokenPair.getRefresh());

        String uid = tokenService.validateToken(tokenPair.getToken());
        Optional<User> user = userRepository.findById(uid);

        assertThat(user).isPresent();
        assertEquals(user.get().getUsername(), userRegisterRequest.getUsername());
        assertEquals(user.get().getEmail(), userRegisterRequest.getEmail());
        assertEquals(user.get().getFirstname(), userRegisterRequest.getFirstname());
        assertEquals(user.get().getLastname(), userRegisterRequest.getLastname());
        assertThat(user.get().getGid()).isNull();
        assertThat(user.get().getPassword()).isNotNull();
        assertThat(user.get().getPassword()).isNotEqualTo(userRegisterRequest.getPassword());
        assertThat(user.get().getCreatedAt()).isNotNull();
    }

    @Test
    void userRegisterEmailAlreadyInUseTest() throws Exception {
        User user = User.builder()
                .username("userA")
                .email("usera@mail.com")
                .firstname("user")
                .lastname("A")
                .password("password")
                .build();

        userRepository.save(user);

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest(
                "userB", "usera@mail.com", "user", "B", "password");

        mockMvc.perform(post("/api/auth/user")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userRegisterRequest)))
                        .andExpect(status().isConflict())
                        .andExpect(jsonPath("$.message", containsString("User with that email already exists")));

        assertThat(userRepository.count()).isEqualTo(1);
    }

    @Test
    void userLoginSuccessTest() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest(
                "userA", "usera@mail.com", "user", "A", "password");

        mockMvc.perform(post("/api/auth/user")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userRegisterRequest)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        UserLoginRequest userLoginRequest = new UserLoginRequest("usera@mail.com", "password");

        String responseString = mockMvc.perform(post("/api/auth/user/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userLoginRequest)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        TokenPair tokenPair = objectMapper.readValue(responseString, TokenPair.class);

        assertThat(tokenPair.getToken()).isNotNull();
        assertThat(tokenPair.getRefresh()).isNotNull();
    }

    @Test
    void userLoginEmailNotFoundTest() throws Exception {
        UserLoginRequest userRegisterRequest = new UserLoginRequest("user@mail.com", "123456");

        mockMvc.perform(post("/api/auth/user/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userRegisterRequest)))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.message", containsString("User with that email was not found")));
    }

    @Test
    void userLoginWrongPasswordTest() throws Exception {
        UserLoginRequest userRegisterRequest = new UserLoginRequest("user@mail.com", "123456");

        userRepository.save(User.builder()
                .username("user")
                .email(userRegisterRequest.getEmail())
                .firstname("user")
                .lastname("A")
                .password(userRegisterRequest.getPassword())
                .build());

        mockMvc.perform(post("/api/auth/user/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userRegisterRequest)))
                        .andExpect(status().isUnauthorized())
                        .andExpect(jsonPath("$.message", containsString("Wrong password")));
    }
}