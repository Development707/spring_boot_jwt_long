package com.spring_boot_jwt_long.service;

import com.spring_boot_jwt_long.entity.Token;
import org.springframework.stereotype.Service;

public interface TokenService {
    Token createToken(Token token);

    Token findByToken(String token);
}
