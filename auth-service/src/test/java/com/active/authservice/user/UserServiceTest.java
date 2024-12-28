package com.active.authservice.user;

import com.active.authservice.token.TokenPair;
import com.active.authservice.token.TokenService;
import com.active.authservice.user.exceptions.EmailAlreadyInUseException;
import com.active.authservice.user.exceptions.EmailNotFoundException;
import com.active.authservice.user.exceptions.WrongPasswordException;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UserService.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private BCryptPasswordEncoder encoder;

    @MockBean
    @SuppressWarnings("unused")
    private GoogleIdTokenVerifier verifier;

    private final UserService userService;

    @Test
    void testGetMe_ValidToken() {
        // Arrange
        String token = "validToken";
        String userId = "userId";
        UserModel userModel = UserModel.builder()
                .id(userId)
                .email("test@example.com")
                .username("testuser")
                .firstname("Test")
                .lastname("User")
                .gid("googleId")
                .build();

        when(tokenService.validateToken(token)).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userModel));

        // Act
        UserMe userMe = userService.getMe(token);

        // Assert
        assertNotNull(userMe);
        assertEquals(userModel.getId(), userMe.getId());
        assertEquals(userModel.getEmail(), userMe.getEmail());
    }

    @Test
    void testGetMe_InvalidToken() {
        // Arrange
        String token = "invalidToken";

        when(tokenService.validateToken(token)).thenThrow(new RuntimeException("Invalid token"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.getMe(token));
    }

    @Test
    void testCreateUser_EmailAlreadyExists() {
        // Arrange
        UserModel userModel = UserModel.builder()
                .email("existing@example.com")
                .password("password")
                .build();

        when(userRepository.existsByEmail(userModel.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(EmailAlreadyInUseException.class, () -> userService.createUser(userModel));
    }

    @Test
    void testCreateUser_Success() {
        // Arrange
        UserModel userModel = UserModel.builder()
                .email("new@example.com")
                .password("password")
                .build();

        String hashedPassword = "hashedPassword";
        when(userRepository.existsByEmail(userModel.getEmail())).thenReturn(false);
        when(encoder.encode(userModel.getPassword())).thenReturn(hashedPassword);
        when(userRepository.save(any(UserModel.class))).thenReturn(userModel);
        when(tokenService.generateTokenPair(any())).thenReturn(new TokenPair("accessToken", "refreshToken"));

        // Act
        TokenPair tokenPair = userService.createUser(userModel);

        // Assert
        assertNotNull(tokenPair);
        assertEquals("accessToken", tokenPair.getToken());
    }

    @Test
    void testLoginUser_InvalidEmail() {
        // Arrange
        String email = "nonexistent@example.com";
        String password = "password";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EmailNotFoundException.class, () -> userService.loginUser(email, password));
    }

    @Test
    void testLoginUser_WrongPassword() {
        // Arrange
        String email = "test@example.com";
        String password = "wrongPassword";
        UserModel userModel = UserModel.builder()
                .email(email)
                .password("hashedPassword")
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userModel));
        when(encoder.matches(password, userModel.getPassword())).thenReturn(false);

        // Act & Assert
        assertThrows(WrongPasswordException.class, () -> userService.loginUser(email, password));
    }

    @Test
    void testLoginUser_Success() {
        // Arrange
        String email = "test@example.com";
        String password = "correctPassword";
        UserModel userModel = UserModel.builder()
                .email(email)
                .password("hashedPassword")
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userModel));
        when(encoder.matches(password, userModel.getPassword())).thenReturn(true);
        when(tokenService.generateTokenPair(userModel.getId())).thenReturn(new TokenPair("accessToken", "refreshToken"));

        // Act
        TokenPair tokenPair = userService.loginUser(email, password);

        // Assert
        assertNotNull(tokenPair);
        assertEquals("accessToken", tokenPair.getToken());
    }
}
