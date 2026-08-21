package com.cleverson.help_desk.customer.application.useCases;

import com.cleverson.help_desk.user.domain.User;
import com.cleverson.help_desk.user.domain.UserRepository;
import com.cleverson.help_desk.user.domain.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListCustomersUseCase {
    private final UserRepository userRepository;
    public ListCustomersUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> execute() {
        return this.userRepository.findByRole(UserRole.CUSTOMER);
    }
}
