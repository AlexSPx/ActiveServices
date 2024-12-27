package com.active.web.endpoints.auth;

import com.active.authservice.token.TokenPair;
import com.active.authservice.user.UserMe;
import com.active.authservice.user.UserModel;
import com.active.authservice.user.UserService;
import com.active.authservice.user.exceptions.EmailAlreadyInUseException;
import com.active.authservice.user.exceptions.EmailNotFoundException;
import com.active.authservice.user.exceptions.WrongPasswordException;
import com.active.web.dto.user.ConnectGoogleAccountRequest;
import com.active.web.dto.user.UserLoginRequest;
import com.active.web.dto.user.UserRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/api/auth/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserMe> me(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.getMe(token));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TokenPair> createUser(@RequestBody UserRegisterRequest userRegisterRequest)
            throws EmailAlreadyInUseException {

        UserModel user = UserModel.builder()
                .username(userRegisterRequest.getUsername())
                .firstname(userRegisterRequest.getFirstname())
                .gid(null)
                .lastname(userRegisterRequest.getLastname())
                .email(userRegisterRequest.getEmail())
                .password(userRegisterRequest.getPassword())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TokenPair> loginUser(@RequestBody UserLoginRequest userLoginRequest)
            throws EmailNotFoundException, WrongPasswordException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.loginUser(
                        userLoginRequest.getEmail(),
                        userLoginRequest.getPassword()
                ));
    }

    @PostMapping("/google")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TokenPair> createGoogleUser(@RequestBody String idTokenString)
            throws GeneralSecurityException, IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createGoogleUser(idTokenString));
    }

    @PostMapping("/google/login")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TokenPair> loginGoogleUser(@RequestBody String idTokenString)
            throws GeneralSecurityException, IOException {
        return ResponseEntity.ok(userService.loginGoogleUser(idTokenString));
    }

    @PostMapping("/google/connect")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> connectGoogleAccount(@RequestBody ConnectGoogleAccountRequest request)
            throws GeneralSecurityException, IOException {
        userService.connectGoogleAccount(request.getToken(), request.getGidToken());

        return ResponseEntity.ok("Google account connected");
    }
}