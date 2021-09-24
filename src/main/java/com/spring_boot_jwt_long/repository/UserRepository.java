package com.spring_boot_jwt_long.repository;

import com.spring_boot_jwt_long.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

    Boolean existsByUsername(String username);
}
