package com.cleverson.help_desk.user.application.useCases;

import com.cleverson.help_desk.user.application.dto.UpdateUserPasswordInput;
import com.cleverson.help_desk.user.application.exceptions.InvalidCredentialsException;
import com.cleverson.help_desk.user.application.exceptions.UserNotFoundException;
import com.cleverson.help_desk.user.domain.User;
import com.cleverson.help_desk.user.domain.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateUserPasswordUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UpdateUserPasswordUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(UUID userId, UpdateUserPasswordInput input) {
        var existingUser = this.userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        boolean passwordsMatch = this.passwordEncoder.matches(input.currentPassword(), existingUser.password());

        if (!passwordsMatch) {
            throw new InvalidCredentialsException();
        }

        String hashNewPassword = this.passwordEncoder.encode(input.newPassword());

        User updatedUser = new User(
                existingUser.id(),
                existingUser.name(),
                existingUser.email(),
                hashNewPassword,
                existingUser.avatarUrl(),
                existingUser.role()
        );

        this.userRepository.save(updatedUser);
    }
}
