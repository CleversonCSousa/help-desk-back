package com.cleverson.help_desk.user.application.useCases;

import com.cleverson.help_desk.user.application.dto.UpdateUserProfileInput;
import com.cleverson.help_desk.user.application.exceptions.UserAlreadyExistsException;
import com.cleverson.help_desk.user.application.exceptions.UserNotFoundException;
import com.cleverson.help_desk.user.domain.User;
import com.cleverson.help_desk.user.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateUserProfileUseCase {
    private final UserRepository userRepository;

    public UpdateUserProfileUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UUID userId, UpdateUserProfileInput input) {
        var existingUser = this.userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        // verify if the email from the input is different from the user's current email and if email is different, we check whether a user with the input email already exists.
        if (!existingUser.email().equalsIgnoreCase(input.email()) && this.userRepository.findByEmail(input.email()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        User updatedUser = new User(
                existingUser.id(),
                input.name(),
                input.email(),
                existingUser.password(),
                existingUser.avatarUrl(),
                existingUser.role()
        );

        this.userRepository.save(updatedUser);

    }
}
