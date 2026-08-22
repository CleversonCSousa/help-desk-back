package com.cleverson.help_desk.customer.presentation.delete;

import com.cleverson.help_desk.customer.application.useCases.DeleteCustomerUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class DeleteCustomerController {
    private final DeleteCustomerUseCase deleteCustomerUseCase;

    public DeleteCustomerController(DeleteCustomerUseCase deleteCustomerUseCase) {
        this.deleteCustomerUseCase = deleteCustomerUseCase;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable UUID id) {
        this.deleteCustomerUseCase.execute(id);
        return ResponseEntity.ok(Map.of("message", "Customer successfully deleted"));
    }

}
