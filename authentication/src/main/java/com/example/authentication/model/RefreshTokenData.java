package com.example.authentication.model;

import java.time.Instant;

public class RefreshTokenData {
    private String emaiId;
    private Instant expiryTime;

    public RefreshTokenData(String emaiId, Instant expiryTime) {
        this.emaiId = emaiId;
        this.expiryTime = expiryTime;
    }

    public String getEmaiId() {
        return emaiId;
    }

    public Instant getExpiryTime() {
        return expiryTime;
    }
}
