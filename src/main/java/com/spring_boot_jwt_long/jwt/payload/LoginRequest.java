package com.spring_boot_jwt_long.jwt.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String username, password;
}
