package com.cleverson.help_desk.user.application.useCases;

import com.cleverson.help_desk.user.application.dto.LoginUserInput;
import com.cleverson.help_desk.user.application.exceptions.InvalidCredentialsException;
import com.cleverson.help_desk.user.domain.User;
import com.cleverson.help_desk.user.infrastructure.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class LoginUserUseCase {

    private final AuthenticationManager authenticationManager;

    public LoginUserUseCase(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public User execute(LoginUserInput input) {

        try {
            var authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            input.email(),
                            input.password()
                    );

            var authentication =
                    authenticationManager.authenticate(authenticationToken);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return userDetails.getUser();
        } catch (AuthenticationException exception) {
            throw new InvalidCredentialsException();
        }
    }
}