package com.spring_boot_jwt_long.jwt.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private Long id;
    private String token, username, password;
    private List<String> roles;

    public JwtResponse(Long id, String token, String username, String password, List<String> roles) {
        this.id = id;
        this.token = token;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
