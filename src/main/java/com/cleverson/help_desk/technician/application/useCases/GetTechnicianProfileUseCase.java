package com.cleverson.help_desk.technician.application.useCases;

import com.cleverson.help_desk.technician.application.dto.TechnicianProfileResponse;
import com.cleverson.help_desk.technician.application.exceptions.TechnicianNotFound;
import com.cleverson.help_desk.technician.domain.WorkingHour;
import com.cleverson.help_desk.technician.domain.WorkingHourRepository;
import com.cleverson.help_desk.user.domain.User;
import com.cleverson.help_desk.user.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetTechnicianProfileUseCase {
    private final UserRepository userRepository;
    private final WorkingHourRepository workingHourRepository;

    public GetTechnicianProfileUseCase(
            UserRepository userRepository,
            WorkingHourRepository workingHourRepository
    ) {
        this.userRepository = userRepository;
        this.workingHourRepository = workingHourRepository;
    }

    public TechnicianProfileResponse execute(UUID technicianId) {
        User user = this.userRepository.findById(technicianId)
                .orElseThrow(() -> new TechnicianNotFound());

        List<WorkingHour> workingHours = this.workingHourRepository.findByTechnicianId(technicianId);
        return new TechnicianProfileResponse(
                user.id(),
                user.name(),
                user.email(),
                user.avatarUrl(),
                workingHours
        );
    }
}
