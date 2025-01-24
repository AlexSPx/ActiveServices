package com.active.authservice.user;

import com.active.authservice.token.TokenPair;
import com.active.authservice.token.TokenService;
import com.active.authservice.user.exceptions.AuthenticationException;
import com.active.authservice.user.exceptions.EmailAlreadyInUseException;
import com.active.authservice.user.exceptions.EmailNotFoundException;
import com.active.authservice.user.exceptions.GoogleAlreadyConnected;
import com.active.authservice.user.exceptions.GoogleUserDoesNotExistException;
import com.active.authservice.user.exceptions.InvalidGoogleIdTokenException;
import com.active.authservice.user.exceptions.UserNotFoundException;
import com.active.authservice.user.exceptions.WrongPasswordException;
import com.active.models.User;
import com.active.repository.UserRepository;
import com.google.api.client.auth.openidconnect.IdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder encoder;
    private final GoogleIdTokenVerifier verifier;

    public UserMe getMe(@NonNull @NotBlank String token) {
        log.info("Getting user by token: {}", token);

        Optional<User> user = userRepository.findById(tokenService.validateToken(token));
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        log.info("User found: {}", user.get());

        return UserMe.builder()
               .id(user.get().getId())
               .gid(user.get().getGid())
               .email(user.get().getEmail())
               .username(user.get().getUsername())
               .firstname(user.get().getFirstname())
               .lastname(user.get().getLastname())
               .gid(user.get().getGid())
               .build();
    }

    public User getUserByid(@NonNull @NotBlank String uid) {
        log.info("Getting user by id: {}", uid);
        return userRepository.findById(uid).orElseThrow(() ->
                new UserNotFoundException(String.format("User %s not found", uid)));
    }

    public TokenPair createUser(@NotNull User userModel) throws EmailAlreadyInUseException {
        log.info("Creating user: {}", userModel);

        if (userRepository.existsByEmail(userModel.getEmail())) {
            log.debug("Email already in use: {}", userModel.getEmail());
            throw new EmailAlreadyInUseException();
        }

        String hashedPassword = encoder.encode(userModel.getPassword());
        userModel.setPassword(hashedPassword);

        User createdUser = userRepository.save(userModel);
        log.info("User created: {}", createdUser);

        return tokenService.generateTokenPair(createdUser.getId());
    }

    public TokenPair loginUser(String email, String password)
            throws EmailNotFoundException, WrongPasswordException {
        log.info("Logging in user with email: {}", email);

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            log.debug("Email not found: {}", email);
            throw new EmailNotFoundException();
        }

        if (!encoder.matches(password, user.get().getPassword())) {
            log.debug("Wrong password for email: {}", email);
            throw new WrongPasswordException();
        }

        log.info("User logged in: {}", user.get());
        return tokenService.generateTokenPair(user.get().getId());
    }

    public TokenPair createGoogleUser(String idTokenString)
            throws GeneralSecurityException, IOException, InvalidGoogleIdTokenException {
       if (idTokenString == null || idTokenString.isBlank()) {
           throw new IllegalArgumentException("idTokenString cannot be null or blank");
       }

        Payload payload = validateToken(idTokenString);

        User user = User.builder()
                .username((String) payload.get("name"))
                .firstname((String) payload.get("given_name"))
                .gid(payload.getSubject())
                .lastname((String) payload.get("family_name"))
                .email((String) payload.get("email"))
                .password(null)
                .build();

        User createdUser = userRepository.save(user);

        return tokenService.generateTokenPair(createdUser.getId());
    }

    public TokenPair loginGoogleUser(String idTokenString)
            throws GeneralSecurityException, IOException, GoogleUserDoesNotExistException {
        Payload payload = validateToken(idTokenString);

        Optional<User> user = userRepository.getByGid(payload.getSubject());

        if (user.isEmpty()) {
            throw new GoogleUserDoesNotExistException();
        }

        return tokenService.generateTokenPair(user.get().getId());
    }

    public void connectGoogleAccount(String token, String idTokenString)
            throws GeneralSecurityException, IOException {
        String uid = tokenService.validateToken(token);

        User user = userRepository.findById(uid)
                .orElseThrow(() -> new AuthenticationException("Account doesn't exist"));

        if(user.getGid() != null) {
            throw new GoogleAlreadyConnected("Your profile has already connected a google account");
        }

        String gid = validateToken(idTokenString).getSubject();

        userRepository.getByGid(gid)
                .ifPresent(s -> {
                    throw new GoogleAlreadyConnected("Profile with this google account already exists");
                });

        user.setGid(gid);
        userRepository.save(user);
    }

    private Payload validateToken(String idTokenString) throws GeneralSecurityException, IOException {
        if(idTokenString == null || idTokenString.isBlank()) {
            throw new IllegalArgumentException("idTokenString cannot be null or blank");
        }

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken == null) {
            throw new InvalidGoogleIdTokenException();
        }

        return idToken.getPayload();
    }
}
