package com.cleverson.help_desk.ticket.apresentation.update_status;

import com.cleverson.help_desk.ticket.application.useCases.UpdateTicketStatusUseCase;
import com.cleverson.help_desk.user.infrastructure.security.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("tickets")
public class UpdateTicketStatusController {
    private final UpdateTicketStatusUseCase updateTicketStatusUseCase;

    public UpdateTicketStatusController(UpdateTicketStatusUseCase updateTicketStatusUseCase) {
        this.updateTicketStatusUseCase = updateTicketStatusUseCase;
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable UUID id, @Valid @RequestBody UpdateTicketStatusRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        updateTicketStatusUseCase.execute(id, userDetails.getUser(), request.status());
        return ResponseEntity.noContent().build();
    }
}
