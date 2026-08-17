package com.cleverson.help_desk.infrastructure.security;

import com.cleverson.help_desk.user.domain.User;
import com.cleverson.help_desk.user.domain.UserRepository;
import com.cleverson.help_desk.user.infrastructure.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserRepository userRepository;

    public SecurityFilter(JWTService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.recoverToken(request);
        if(token != null) {
            String id = jwtService.validateToken(token);
            if(id != null) {
                Optional<User> user = userRepository.findById(UUID.fromString(id));
                if(user.isPresent()) {
                    UserDetails userDetails = new UserDetailsImpl(user.get());

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("auth-token")) {
                    return cookie.getValue();
                }
            }
        }

        return null;

    }

}
