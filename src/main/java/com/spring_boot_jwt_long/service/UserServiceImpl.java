package com.spring_boot_jwt_long.service;

import com.spring_boot_jwt_long.authen.UserDetailsImpl;
import com.spring_boot_jwt_long.entity.User;
import com.spring_boot_jwt_long.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User createUser(User user) {
        return repository.save(user);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    @Transactional
    public UserDetailsImpl findByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.buildUser(user);
    }
}
