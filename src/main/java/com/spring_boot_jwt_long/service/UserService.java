package com.spring_boot_jwt_long.service;

import com.spring_boot_jwt_long.jwt.UserDetailsImpl;
import com.spring_boot_jwt_long.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    User createUser(User user);

    Boolean existsByUsername(String username);

    UserDetailsImpl findByUsername(String username) throws UsernameNotFoundException;
}
