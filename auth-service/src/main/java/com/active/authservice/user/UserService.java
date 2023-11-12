package com.active.authservice.user;

import com.active.authservice.token.TokenService;
import com.active.authservice.user.dto.TokenPairResponse;
import com.active.authservice.user.dto.UserLoginRequest;
import com.active.authservice.user.dto.UserMeResponse;
import com.active.authservice.user.dto.UserRegisterRequest;
import com.active.authservice.user.exceptions.EmailAlreadyInUseException;
import com.active.authservice.user.exceptions.EmailNotFoundException;
import com.active.authservice.user.exceptions.GoogleUserDoesNotExistException;
import com.active.authservice.user.exceptions.InvalidGoogleIdTokenException;
import com.active.authservice.user.exceptions.UserNotFoundException;
import com.active.authservice.user.exceptions.WrongPasswordException;
import com.google.api.client.auth.openidconnect.IdToken;
import com.google.api.client.auth.openidconnect.IdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final TokenService tokenService;

    private final BCryptPasswordEncoder encoder;
    private final GoogleIdTokenVerifier verifier;

    private Payload validateToken(String idTokenString) throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = verifier.verify(idTokenString);

        if (idToken == null) {
            throw new InvalidGoogleIdTokenException();
        }

        return idToken.getPayload();
    }

    public UserMeResponse getMe(String token) {
       Optional<UserModel> user = userRepository.findById(tokenService.validateToken(token));

       if (user.isEmpty()) {
           throw new UserNotFoundException();
       }

       return UserMeResponse.builder()
               .id(user.get().getId())
               .email(user.get().getEmail())
               .username(user.get().getUsername())
               .firstname(user.get().getFirstname())
               .lastname(user.get().getLastname())
               .gid(user.get().getGid())
               .build();
    }

    public TokenPairResponse createUser(UserRegisterRequest userRegisterRequest) throws EmailAlreadyInUseException {
        if (userRepository.existsByEmail(userRegisterRequest.getEmail())) {
            throw new EmailAlreadyInUseException();
        }

        String hashedPassword = encoder.encode(userRegisterRequest.getPassword());

        UserModel user = UserModel.builder()
                .username(userRegisterRequest.getUsername())
                .firstname(userRegisterRequest.getFirstname())
                .gid(null)
                .lastname(userRegisterRequest.getLastname())
                .email(userRegisterRequest.getEmail())
                .password(hashedPassword)
                .build();

        UserModel createdUser = userRepository.save(user);

        return tokenService.generateTokenPair(createdUser.getId());
    }

    public TokenPairResponse loginUser(UserLoginRequest userLoginRequest)
            throws EmailNotFoundException, WrongPasswordException {
        Optional<UserModel> user = userRepository.findByEmail(userLoginRequest.getEmail());
        if (user.isEmpty()) {
            throw new EmailNotFoundException();
        }

        if (!encoder.matches(userLoginRequest.getPassword(), user.get().getPassword())) {
            throw new WrongPasswordException();
        }

        return tokenService.generateTokenPair(user.get().getId());
    }

    public TokenPairResponse createGoogleUser(String idTokenString)
            throws GeneralSecurityException, IOException, InvalidGoogleIdTokenException {
        Payload payload = validateToken(idTokenString);

        UserModel user = UserModel.builder()
                .username((String) payload.get("username"))
                .firstname((String) payload.get("firstname"))
                .gid(payload.getSubject())
                .lastname((String) payload.get("lastname"))
                .email((String) payload.get("email"))
                .password(null)
                .build();

        UserModel createdUser = userRepository.save(user);

        return tokenService.generateTokenPair(createdUser.getId());
    }

    public TokenPairResponse loginGoogleUser(String idTokenString)
            throws GeneralSecurityException, IOException, GoogleUserDoesNotExistException {
        Payload payload = validateToken(idTokenString);

        Optional<UserModel> user = userRepository.getByGid(payload.getSubject());

        if (user.isEmpty()) {
            throw new GoogleUserDoesNotExistException();
        }

        return tokenService.generateTokenPair(user.get().getId());
    }
}
