package com.cleverson.help_desk.technician.presentation;

import com.cleverson.help_desk.technician.application.GetTechnicianProfileUseCase;
import com.cleverson.help_desk.technician.application.dto.TechnicianProfileResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/technicians")
public class GetTechnicianProfileController {
    private final GetTechnicianProfileUseCase getTechnicianProfileUseCase;

    public GetTechnicianProfileController(GetTechnicianProfileUseCase getTechnicianProfileUseCase) {
        this.getTechnicianProfileUseCase = getTechnicianProfileUseCase;
    }

    @GetMapping("/{id}")
    public TechnicianProfileResponse get(@PathVariable UUID id) {
        return this.getTechnicianProfileUseCase.execute(id);
    }
}
