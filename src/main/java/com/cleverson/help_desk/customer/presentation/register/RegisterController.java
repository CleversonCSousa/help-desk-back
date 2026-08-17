package com.cleverson.help_desk.customer.presentation.register;

import com.cleverson.help_desk.infrastructure.security.JWTService;
import com.cleverson.help_desk.customer.application.dto.RegisterCustomerInput;
import com.cleverson.help_desk.customer.application.useCases.RegisterCustomerUseCase;
import com.cleverson.help_desk.user.infrastructure.security.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer/register")
public class RegisterController {

    private final RegisterCustomerUseCase registerCustomerUseCase;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public RegisterController(
            RegisterCustomerUseCase registerCustomerUseCase,
            AuthenticationManager authenticationManager,
            JWTService jwtService
    ) {
        this.registerCustomerUseCase = registerCustomerUseCase;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping()
    public ResponseEntity<RegisterResponseDTO> register(
            @RequestBody @Valid RegisterRequestDTO registerCustomerRequest
    ) {

        RegisterCustomerInput input =
                new RegisterCustomerInput(
                        registerCustomerRequest.name(),
                        registerCustomerRequest.email(),
                        registerCustomerRequest.password()
                );

        registerCustomerUseCase.execute(input);

        var usernamePassword = new UsernamePasswordAuthenticationToken(
                registerCustomerRequest.email(),
                registerCustomerRequest.password()
        );

        var auth = authenticationManager.authenticate(usernamePassword);
        var userDetails = (UserDetailsImpl) auth.getPrincipal();
        var token = jwtService.generateToken(userDetails.getUser());
        var user = new UserResponseDTO(registerCustomerRequest.name(), registerCustomerRequest.email(), null);
        RegisterResponseDTO registerResponseDTO =
                new RegisterResponseDTO(user, token);
        ResponseCookie cookie = ResponseCookie.from("auth-token", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(24 * 60 * 60)
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(registerResponseDTO);
    }

}