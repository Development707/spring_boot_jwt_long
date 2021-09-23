package com.spring_boot_jwt_long.repository;

import com.spring_boot_jwt_long.entity.Role;
import com.spring_boot_jwt_long.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Token findByToken(String token);
}
