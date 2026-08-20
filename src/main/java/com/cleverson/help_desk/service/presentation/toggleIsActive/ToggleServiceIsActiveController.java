package com.cleverson.help_desk.service.presentation.toggleIsActive;

import com.cleverson.help_desk.service.application.dto.ToggleServiceIsActiveInput;
import com.cleverson.help_desk.service.application.useCases.ToggleServiceIsActiveUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/services")
public class ToggleServiceIsActiveController {
    private final ToggleServiceIsActiveUseCase toggleServiceIsActiveUseCase;

    public ToggleServiceIsActiveController(ToggleServiceIsActiveUseCase toggleServiceIsActiveUseCase) {
        this.toggleServiceIsActiveUseCase = toggleServiceIsActiveUseCase;
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<ToggleServiceIsActiveResponseDTO> toggleIsActive(@PathVariable UUID id) {
        ToggleServiceIsActiveInput input = new ToggleServiceIsActiveInput(
                id
        );

        toggleServiceIsActiveUseCase.execute(input);

        return ResponseEntity.ok(new ToggleServiceIsActiveResponseDTO("Status updated"));
    }
}
