package com.cleverson.help_desk.technician.presentation;

import com.cleverson.help_desk.technician.application.dto.CreateTechnicianInput;
import com.cleverson.help_desk.technician.application.useCases.CreateTechnicianUseCase;
import com.cleverson.help_desk.technician.domain.TechnicianSummary;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/technicians")
public class CreateTechnicianController {
    private final CreateTechnicianUseCase createTechnicianUseCase;

    public CreateTechnicianController(CreateTechnicianUseCase createTechnicianUseCase) {
        this.createTechnicianUseCase = createTechnicianUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateTechnicianResponseDTO> create(@Valid @RequestBody  CreateTechnicianRequestDTO request) {
        TechnicianSummary technician = this.createTechnicianUseCase.execute(new CreateTechnicianInput(
                request.name(),
                request.email(),
                request.password(),
                request.workingHours()
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateTechnicianResponseDTO(
                "Technician successfully created",
                technician
        ));
    }
}
