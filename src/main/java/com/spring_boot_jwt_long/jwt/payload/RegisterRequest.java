package com.spring_boot_jwt_long.jwt.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RegisterRequest {
    private String username, password;
    private Set<String> roles;
}
