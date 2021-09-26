package com.spring_boot_jwt_long.jwt;

import com.spring_boot_jwt_long.entity.Token;
import com.spring_boot_jwt_long.entity.User;
import com.spring_boot_jwt_long.service.TokenService;
import com.spring_boot_jwt_long.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class AuthTokenRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        UserDetailsImpl userDetails;
        Token token;
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Token")){
            String jwt = authorizationHeader.substring(6);
            String username = jwtUtils.getUsernameFormJwtToken(jwt);
            userDetails = userService.findByUsername(username);
            token = tokenService.findByToken(jwt);
            if (token.getTokenExpDate().after(new Date())){
                Set<GrantedAuthority> authorities = new HashSet<>();
                userDetails.getAuthorities().forEach(o -> authorities.add(new SimpleGrantedAuthority((String) o)));
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,authorities);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
