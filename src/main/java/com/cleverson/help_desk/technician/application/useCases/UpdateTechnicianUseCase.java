package com.cleverson.help_desk.technician.application.useCases;

import com.cleverson.help_desk.technician.application.dto.UpdateTechnicianInput;
import com.cleverson.help_desk.technician.application.exceptions.TechnicianNotFound;
import com.cleverson.help_desk.technician.domain.Technician;
import com.cleverson.help_desk.technician.domain.TechnicianRepository;
import com.cleverson.help_desk.user.application.exceptions.UserAlreadyExistsException;
import com.cleverson.help_desk.user.domain.User;
import com.cleverson.help_desk.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UpdateTechnicianUseCase {
    private final TechnicianRepository technicianRepository;
    private final UserRepository userRepository;

    public UpdateTechnicianUseCase(TechnicianRepository technicianRepository, UserRepository userRepository) {
        this.technicianRepository = technicianRepository;
        this.userRepository = userRepository;
    }

    /*
    * This annotation is essential because we update both tables (user and technician), or neither of them, to avoid inconsistencies.
    * */
    @Transactional
    public void execute(UpdateTechnicianInput input) {
        User user = this.userRepository.findById(input.id())
                .orElseThrow(() -> new TechnicianNotFound());

        // checks if the email already exists
        this.userRepository.findByEmail(input.email()).ifPresent(existingUser -> {
            if (!existingUser.id().equals(input.id())) {
                throw new UserAlreadyExistsException();
            }
        });

        // update main table
        User updatedUser = new User(
                user.id(),
                input.name(),
                input.email(),
                user.password(),
                user.avatarUrl(),
                user.role()
        );

        this.userRepository.save(updatedUser);

        // update specific table
        Technician technician = this.technicianRepository.findById(input.id())
                .orElseThrow(() -> new TechnicianNotFound());

        Technician updatedTechnician = new Technician(
                technician.id(),
                input.workingHours()
        );
        this.technicianRepository.save(updatedTechnician);
    }
}
