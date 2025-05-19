package com.example.authentication.service;

import com.example.authentication.dto.AuthResponse;
import com.example.authentication.dto.LoginRequest;
import com.example.authentication.model.RefreshTokenData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;

@Service
public class AuthService {
    private final long ACCESS_TOKEN_VALIDITY = 5 * 60 * 1000;//5 minutes
    private final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 1000;//7 days

    HashMap<String, RefreshTokenData> refreshTokenStore = new HashMap<>();
    @Autowired
    private JwtService jwtService;


    public AuthResponse login(LoginRequest request) {
        String accessToken = jwtService.generateToken(request.getEmailId(), ACCESS_TOKEN_VALIDITY);

        String refreshToken = UUID.randomUUID().toString();
        Instant expiry = Instant.now().plusMillis(REFRESH_TOKEN_VALIDITY);
        refreshTokenStore.put(refreshToken, new RefreshTokenData(request.getEmailId(), expiry));

        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse refreshAccessToken(String refreshToken) {
        RefreshTokenData data = refreshTokenStore.get(refreshToken);
        if (data == null) {
            throw new RuntimeException("Invalid refresh token");
        }

        if (data.getExpiryTime().isBefore(Instant.now())) {
            refreshTokenStore.remove(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }

        String newAccessToken = jwtService.generateToken(data.getEmaiId(), ACCESS_TOKEN_VALIDITY);
        return new AuthResponse(newAccessToken, refreshToken);
    }
}
