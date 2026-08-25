package com.cleverson.help_desk.customer.presentation.update;

import com.cleverson.help_desk.customer.application.dto.UpdateCustomerInput;
import com.cleverson.help_desk.customer.application.useCases.UpdateCustomerUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class UpdateController {
    private final UpdateCustomerUseCase updateCustomerUseCase;

    public UpdateController(UpdateCustomerUseCase updateCustomerUseCase) {
        this.updateCustomerUseCase = updateCustomerUseCase;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> update(@RequestBody @Valid UpdateRequestDTO request, @PathVariable UUID id) {
        UpdateCustomerInput input = new UpdateCustomerInput(
                id,
                request.name(),
                request.email()
        );
        this.updateCustomerUseCase.execute(input);
        return ResponseEntity.ok(Map.of("message", "Customer updated successfully"));    }

}
