package com.cleverson.help_desk.technician.application.useCases;

import com.cleverson.help_desk.technician.application.dto.CreateTechnicianInput;
import com.cleverson.help_desk.technician.domain.Technician;
import com.cleverson.help_desk.technician.domain.TechnicianRepository;
import com.cleverson.help_desk.technician.domain.TechnicianSummary;
import com.cleverson.help_desk.user.application.exceptions.UserAlreadyExistsException;
import com.cleverson.help_desk.user.domain.User;
import com.cleverson.help_desk.user.domain.UserRepository;
import com.cleverson.help_desk.user.domain.UserRole;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateTechnicianUseCase {
    private final TechnicianRepository technicianRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateTechnicianUseCase(TechnicianRepository technicianRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.technicianRepository = technicianRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public TechnicianSummary execute(CreateTechnicianInput input) {
        // checks if the email already exists
        this.userRepository.findByEmail(input.email()).ifPresent(existingUser -> {
            throw new UserAlreadyExistsException();
        });

        String encryptedPassword = this.passwordEncoder.encode(input.password());

        User user = this.userRepository.save(new User(
                null,
                input.name(),
                input.email(),
                encryptedPassword,
                null,
                UserRole.TECHNICIAN
        ));

        Technician technician = new Technician(
                user.id(),
                input.workingHours()
        );

        this.technicianRepository.save(technician);

        return new TechnicianSummary(
                technician.id(),
                user.name(),
                user.email(),
                user.avatarUrl(),
                technician.workingHours()
        );
    }
}
