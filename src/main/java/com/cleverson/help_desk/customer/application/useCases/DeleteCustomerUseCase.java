package com.cleverson.help_desk.customer.application.useCases;

import com.cleverson.help_desk.user.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteCustomerUseCase {
    private final UserRepository userRepository;
    public DeleteCustomerUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UUID userId) {
        this.userRepository.deleteById(userId);
    }

}
