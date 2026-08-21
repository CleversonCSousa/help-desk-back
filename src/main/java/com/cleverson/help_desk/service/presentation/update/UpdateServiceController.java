package com.cleverson.help_desk.service.presentation.update;

import com.cleverson.help_desk.service.application.dto.UpdateServiceInput;
import com.cleverson.help_desk.service.application.useCases.UpdateServiceUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/services")
public class UpdateServiceController {
    private final UpdateServiceUseCase updateServiceUseCase;

    public UpdateServiceController(UpdateServiceUseCase updateServiceUseCase) {
        this.updateServiceUseCase = updateServiceUseCase;
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateServiceResponseDTO> update(@PathVariable UUID id, @RequestBody UpdateServiceRequestDTO request) {
        UpdateServiceInput input = new UpdateServiceInput(
                id,
                request.title(),
                request.description(),
                request.price()
        );
        updateServiceUseCase.execute(input);
        return ResponseEntity.ok(new UpdateServiceResponseDTO("Service updated successfully"));
    }
}
