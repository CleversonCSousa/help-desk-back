package com.cleverson.help_desk.customer.application.useCases;

import com.cleverson.help_desk.customer.application.dto.UpdateCustomerInput;
import com.cleverson.help_desk.customer.application.exceptions.CustomerAlreadyExistsException;
import com.cleverson.help_desk.customer.application.exceptions.CustomerNotFoundException;
import com.cleverson.help_desk.user.domain.User;
import com.cleverson.help_desk.user.domain.UserRepository;
import com.cleverson.help_desk.user.domain.UserRole;
import org.springframework.stereotype.Service;

@Service
public class UpdateCustomerUseCase {
    private final UserRepository userRepository;
    public UpdateCustomerUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UpdateCustomerInput input) {
        User customer = this.userRepository.findById(input.id())
                .orElseThrow(() -> new CustomerNotFoundException());

        this.userRepository.findByEmail(input.email()).ifPresent(existingCustomer -> {
            if (!existingCustomer.id().equals(input.id())) {
                throw new CustomerAlreadyExistsException();
            }
        });

        User updatedCustomer = new User(
                customer.id(),
                input.name(),
                input.email(),
                customer.password(),
                customer.avatarUrl(),
                UserRole.CUSTOMER
        );

        this.userRepository.save(updatedCustomer);
    }
}
