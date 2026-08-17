package com.cleverson.help_desk.user.presentation.auth;

import com.cleverson.help_desk.customer.presentation.register.UserResponseDTO;
import com.cleverson.help_desk.infrastructure.security.JWTService;
import com.cleverson.help_desk.user.application.dto.LoginUserInput;
import com.cleverson.help_desk.user.application.useCases.LoginUserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final LoginUserUseCase loginUserUseCase;
    private final JWTService jwtService;

    public AuthController(
            LoginUserUseCase loginUserUseCase,
            JWTService jwtService
    ) {
        this.loginUserUseCase = loginUserUseCase;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody @Valid LoginRequestDTO loginRequest
    ) {

        LoginUserInput input = new LoginUserInput(
                loginRequest.email(),
                loginRequest.password()
        );

        var user = loginUserUseCase.execute(input);

        var token = jwtService.generateToken(user);
        ResponseCookie cookie = ResponseCookie.from("auth-token", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(24 * 60 * 60)
                .sameSite("Lax")
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(new LoginResponseDTO(new UserResponseDTO(user.name(), user.email(), user.avatarUrl()), token));
    }
}