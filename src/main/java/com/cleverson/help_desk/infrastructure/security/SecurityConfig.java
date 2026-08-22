package com.cleverson.help_desk.infrastructure.security;

import com.cleverson.help_desk.user.domain.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable()).cors(cors -> {})
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // allows requests to the endpoint /customer/register (POST method) without authentication
                        .requestMatchers(HttpMethod.POST, "/customer/register").permitAll()
                        // allows requests to the endpoint /auth/login (POST method) without authentication
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()

                        // protects the child routes of /technician (POST method)
                        .requestMatchers(HttpMethod.POST, "/technician/**").hasRole(UserRole.TECHNICIAN.name())

                        // protects the /services route and child routes (POST method)
                        .requestMatchers(HttpMethod.POST, "/services", "/services/**").hasRole(UserRole.ADMIN.name())

                        // protects the /services route and child routes (GET method)
                        .requestMatchers(HttpMethod.GET, "/services", "/services/**").hasRole(UserRole.ADMIN.name())

                        // protects the child routes of /services (PATCH method)
                        .requestMatchers(HttpMethod.PATCH, "/services/**").hasRole(UserRole.ADMIN.name())

                        // protects the child routes of /services (PUT method)
                        .requestMatchers(HttpMethod.PUT, "/services/**").hasRole(UserRole.ADMIN.name())

                        // protects the /customers route and child routes (GET method)
                        .requestMatchers(HttpMethod.GET, "/customers", "/customers/**").hasRole(UserRole.ADMIN.name())

                        // protects the child routes of /customers (DELETE method)
                        .requestMatchers(HttpMethod.DELETE, "/customers/**").hasRole(UserRole.ADMIN.name())
                        .anyRequest().authenticated()
                )

                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
