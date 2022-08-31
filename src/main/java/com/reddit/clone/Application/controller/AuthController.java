package com.reddit.clone.Application.controller;

import com.reddit.clone.Application.dto.AuthenticationResponse;
import com.reddit.clone.Application.dto.LoginRequest;
import com.reddit.clone.Application.dto.RefreshTokenRequest;
import com.reddit.clone.Application.dto.RegisterRequest;
import com.reddit.clone.Application.exception.SpringRedditException;
import com.reddit.clone.Application.service.AuthService;
import com.reddit.clone.Application.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest){
        try {
            authService.signUp(registerRequest);
        } catch (SpringRedditException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return new ResponseEntity<>("User Registered Successful", HttpStatus.OK);
    }
    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
        try {
            authService.verifyAccount(token);
        } catch (SpringRedditException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return new ResponseEntity<>("Account Activated successfully",HttpStatus.OK);
    }
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        try {
            return authService.login(loginRequest);
        } catch (SpringRedditException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/refresh-token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            return authService.refreshToken(refreshTokenRequest);
        } catch (SpringRedditException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully!!");
    }
}