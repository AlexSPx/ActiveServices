package com.active.authservice.user;

import com.active.authservice.token.service.TokenService;
import com.active.authservice.user.dto.TokenPairResponse;
import com.active.authservice.user.dto.UserLoginRequest;
import com.active.authservice.user.dto.UserRegisterRequest;
import com.active.authservice.user.exceptions.EmailAlreadyInUseException;
import com.active.authservice.user.exceptions.EmailNotFoundException;
import com.active.authservice.user.exceptions.WrongPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final TokenService tokenService;

    public TokenPairResponse createUser(UserRegisterRequest userRegisterRequest) throws EmailAlreadyInUseException {

        if(userRepository.existsByEmail(userRegisterRequest.getEmail())) {
            throw new EmailAlreadyInUseException();
        }

        String hashedPassword = encoder.encode(userRegisterRequest.getPassword());

        UserModel user = UserModel.builder()
                .username(userRegisterRequest.getUsername())
                .firstname(userRegisterRequest.getFirstname())
                .lastname(userRegisterRequest.getLastname())
                .email(userRegisterRequest.getEmail())
                .password(hashedPassword)
                .build();

        UserModel createdUser = userRepository.save(user);

        return TokenPairResponse.builder()
                .token(tokenService.generateJWT(createdUser.getId()))
                .refresh(tokenService.generateRefresh(createdUser.getId()))
                .build();
    }

    public TokenPairResponse loginUser(UserLoginRequest userLoginRequest) throws EmailNotFoundException, WrongPasswordException {

        Optional<UserModel> user = userRepository.findByEmail(userLoginRequest.getEmail());
        if(user.isEmpty()) {
            throw new EmailNotFoundException();
        }

        if(!encoder.matches(userLoginRequest.getPassword(), user.get().getPassword())){
            throw new WrongPasswordException();
        }

        return TokenPairResponse.builder()
                .token(tokenService.generateJWT(user.get().getId()))
                .refresh(tokenService.generateRefresh(user.get().getId()))
                .build();
    }
}
