package com.spring_boot_jwt_long.repository;

import com.spring_boot_jwt_long.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Token findByToken(String token);
}
