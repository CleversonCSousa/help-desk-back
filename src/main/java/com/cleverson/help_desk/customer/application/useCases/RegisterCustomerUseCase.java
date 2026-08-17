package com.cleverson.help_desk.customer.application.useCases;

import com.cleverson.help_desk.customer.application.dto.RegisterCustomerInput;
import com.cleverson.help_desk.user.application.exceptions.UserAlreadyExistsException;
import com.cleverson.help_desk.user.domain.User;
import com.cleverson.help_desk.user.domain.UserRepository;
import com.cleverson.help_desk.user.domain.UserRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterCustomerUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterCustomerUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User execute(RegisterCustomerInput input) {
        // Checking if a user with the same email exists, if so, a runtime exception (UserAlreadyExistsException) is thrown.
        if (this.userRepository.findByEmail(input.email()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        String encryptedPassword = passwordEncoder.encode(input.password());

        User user = new User(
                null,
                input.name(),
                input.email(),
                encryptedPassword,
                null,
                UserRole.CUSTOMER
        );

        return userRepository.save(user);
    }
}