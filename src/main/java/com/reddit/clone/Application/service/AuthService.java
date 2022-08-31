package com.reddit.clone.Application.service;

import com.reddit.clone.Application.config.AppConfig;
import com.reddit.clone.Application.dto.AuthenticationResponse;
import com.reddit.clone.Application.dto.LoginRequest;
import com.reddit.clone.Application.dto.RefreshTokenRequest;
import com.reddit.clone.Application.dto.RegisterRequest;
import com.reddit.clone.Application.exception.SpringRedditException;
import com.reddit.clone.Application.model.NotificationEmail;
import com.reddit.clone.Application.model.User;
import com.reddit.clone.Application.model.VerificationToken;
import com.reddit.clone.Application.repository.UserRepo;
import com.reddit.clone.Application.repository.VerificationTokenRepo;
import com.reddit.clone.Application.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final VerificationTokenRepo verificationTokenRepo;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final AppConfig appConfig;

    @Transactional
    public void signUp(RegisterRequest registerRequest) throws SpringRedditException {
        User user = new User();
        user.setUserName(registerRequest.getUserName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        userRepo.save(user);
        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please Activate your account", user.getEmail(),
                "Thank you for signing up with Reddit Clone by SHOBIT GUPTA , Please click the link below to" +
                        "activate your account : " + appConfig.getUrl()+"/api/auth/accountVerification/" + token));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepo.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) throws SpringRedditException {

        Optional<VerificationToken> verificationToken = verificationTokenRepo.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringRedditException("Invalid token"));
        findUserAndEnable(verificationToken.get());
    }

    private void findUserAndEnable(VerificationToken verificationToken) throws SpringRedditException {
        String userName = verificationToken.getUser().getUserName();
        User user = userRepo.findByUserName(userName).orElseThrow(() ->
                new SpringRedditException("User with the given name doesnot exist"));
        user.setEnabled(true);
        userRepo.save(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) throws SpringRedditException {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(token, refreshTokenService.generateRefreshToken().getToken(),
                Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()), loginRequest.getUserName());
    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userRepo.findByUserName(username).get();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws SpringRedditException {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUserName());
        return new AuthenticationResponse(token, refreshTokenRequest.getRefreshToken(),
                Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()), refreshTokenRequest.getUserName());
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}