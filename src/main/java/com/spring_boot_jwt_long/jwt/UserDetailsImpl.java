package com.spring_boot_jwt_long.jwt;

import com.spring_boot_jwt_long.entity.User;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
public class UserDetailsImpl implements UserDetails {

    private Long userId;
    private String username;
    private String password;
    private Collection authorities;

    public static UserDetailsImpl buildUser(User user) {
        UserDetailsImpl userDetails = new UserDetailsImpl();

        userDetails.userId = user.getId();
        userDetails.username = user.getUsername();
        userDetails.password = user.getPassword();

        Set<String> authorities = new HashSet<>();
        if (user.getRoles() != null) {
            user.getRoles().forEach(role -> {
                authorities.add(role.getRoleKey());
                role.getPermissions().forEach(permission -> {
                    authorities.add(permission.getPermissionKey());
                });
            });
        }
        userDetails.authorities = authorities;

        return userDetails;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
