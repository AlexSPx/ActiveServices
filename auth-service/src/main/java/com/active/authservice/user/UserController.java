package com.active.authservice.user;

import com.active.authservice.token.service.TokenService;
import com.active.authservice.user.dto.TokenPairResponse;
import com.active.authservice.user.dto.UserLoginRequest;
import com.active.authservice.user.dto.UserRegisterRequest;
import com.active.authservice.user.exceptions.EmailAlreadyInUseException;
import com.active.authservice.user.exceptions.EmailNotFoundException;
import com.active.authservice.user.exceptions.WrongPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TokenPairResponse createUser(@RequestBody UserRegisterRequest userRegisterRequest) throws EmailAlreadyInUseException {
        return userService.createUser(userRegisterRequest);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenPairResponse loginUser(@RequestBody UserLoginRequest userLoginRequest) throws EmailNotFoundException, WrongPasswordException {
        return userService.loginUser(userLoginRequest);
    }
}
