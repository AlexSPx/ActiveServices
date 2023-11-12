package com.active.authservice.user;

import com.active.authservice.user.dto.IdTokenRequest;
import com.active.authservice.user.dto.TokenPairResponse;
import com.active.authservice.user.dto.UserLoginRequest;
import com.active.authservice.user.dto.UserMeResponse;
import com.active.authservice.user.dto.UserRegisterRequest;
import com.active.authservice.user.exceptions.EmailAlreadyInUseException;
import com.active.authservice.user.exceptions.EmailNotFoundException;
import com.active.authservice.user.exceptions.WrongPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/api/auth/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserMeResponse me(@RequestHeader("Authorization") String token) {
        return userService.getMe(token);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TokenPairResponse createUser(@RequestBody UserRegisterRequest userRegisterRequest)
            throws EmailAlreadyInUseException {
        return userService.createUser(userRegisterRequest);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenPairResponse loginUser(@RequestBody UserLoginRequest userLoginRequest)
            throws EmailNotFoundException, WrongPasswordException {
        return userService.loginUser(userLoginRequest);
    }

    @PostMapping("/google")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenPairResponse createGoogleUser(@RequestBody IdTokenRequest idTokenString)
            throws GeneralSecurityException, IOException {
        return userService.createGoogleUser(idTokenString.getIdToken());
    }

    @PostMapping("/google/login")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenPairResponse loginGoogleUser(@RequestBody IdTokenRequest idTokenString)
            throws GeneralSecurityException, IOException {
        return userService.createGoogleUser(idTokenString.getIdToken());
    }
}
