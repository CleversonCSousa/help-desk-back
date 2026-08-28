package com.cleverson.help_desk.technician.presentation;

import com.cleverson.help_desk.technician.application.dto.TechnicianSummaryResponse;
import com.cleverson.help_desk.technician.application.dto.UpdateTechnicianInput;
import com.cleverson.help_desk.technician.application.useCases.ListTechniciansUseCase;
import com.cleverson.help_desk.technician.application.useCases.UpdateTechnicianUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/technicians")
public class UpdateTechnicianController {
    private final UpdateTechnicianUseCase updateTechnicianUseCase;

    public UpdateTechnicianController(UpdateTechnicianUseCase updateTechnicianUseCase) {
        this.updateTechnicianUseCase = updateTechnicianUseCase;
    }

    @PutMapping()
    public ResponseEntity<Map<String, String>> update(@Valid @RequestBody UpdateTechnicianRequestDTO request) {
        this.updateTechnicianUseCase.execute(new UpdateTechnicianInput(
                request.id(),
                request.name(),
                request.email(),
                request.workingHours()
        ));
        return ResponseEntity.ok(Map.of("message", "Technician successfully updated."));
    }
}
