package com.spring_boot_jwt_long.controller;

import com.spring_boot_jwt_long.entity.Token;
import com.spring_boot_jwt_long.entity.User;
import com.spring_boot_jwt_long.jwt.JwtUtils;
import com.spring_boot_jwt_long.jwt.UserDetailsImpl;
import com.spring_boot_jwt_long.jwt.payload.JwtResponse;
import com.spring_boot_jwt_long.jwt.payload.LoginRequest;
import com.spring_boot_jwt_long.jwt.payload.RegisterRequest;
import com.spring_boot_jwt_long.service.TokenService;
import com.spring_boot_jwt_long.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest registerRequest) {
        if (userService.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new String("Error: Username(" + registerRequest.getUsername() + ") is already taken!"));
        }
        registerRequest.setPassword(encoder.encode(registerRequest.getPassword()));
        return ResponseEntity.ok(userService.createUser(new User(registerRequest.getUsername(),registerRequest.getPassword())));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
//        CACH 1:
        UserDetailsImpl userDetails = userService.findByUsername(loginRequest.getUsername());
        if (userDetails == null){
            return ResponseEntity.badRequest().body("Account is not valid");
        }
        if (!encoder.matches(loginRequest.getPassword(),userDetails.getPassword())){
            return ResponseEntity.badRequest().body("Password is not valid");
        }
        Token token = new Token();
        token.setToken(jwtUtils.generateJwtToken(userDetails));
        token.setTokenExpDate(jwtUtils.generateExpirationDate());
        token.setCreatedBy(userDetails.getUserId()+"");
        tokenService.createToken(token);
        return ResponseEntity.ok(token);

////        CACH 2: LOI PHAI THEO CAU TRUC SPRING SECURITY (Collection<? extends GrantedAuthority> authorities)
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
//        Authentication authentication = authenticationManager.authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = jwtUtils.generateJwtToken(authentication);
//        UserDetailsImpl userDetails = UserDetailsImpl.buildUser((User) authentication.getPrincipal());
//        List<String> roles = (List<String>) userDetails.getAuthorities();
//        return ResponseEntity.ok(new JwtResponse(userDetails.getUserId(),
//                jwt,userDetails.getUsername(),userDetails.getPassword(),roles));
    }
}
