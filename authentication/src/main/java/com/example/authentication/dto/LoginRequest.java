package com.example.authentication.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LoginRequest {
    private String emailId;
    private String password;
}
