package com.cleverson.help_desk.technician.application.useCases;

import com.cleverson.help_desk.technician.application.dto.TechnicianSummaryResponse;
import com.cleverson.help_desk.technician.domain.TechnicianRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListTechniciansUseCase {
    private final TechnicianRepository technicianRepository;

    public ListTechniciansUseCase(TechnicianRepository technicianRepository) {
        this.technicianRepository = technicianRepository;
    }

    public List<TechnicianSummaryResponse> execute() {
        var summaries = this.technicianRepository.findAllWithWorkingHours();

        return summaries.stream().map(s -> new TechnicianSummaryResponse(
                s.id(),
                s.name(),
                s.email(),
                s.avatarUrl(),
                s.workingHours()
        )).toList();
    }
}
