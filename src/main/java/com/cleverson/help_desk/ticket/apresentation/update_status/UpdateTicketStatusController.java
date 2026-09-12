package com.cleverson.help_desk.ticket.apresentation.update_status;

import com.cleverson.help_desk.ticket.application.dto.UpdateTicketStatusInput;
import com.cleverson.help_desk.ticket.application.useCases.UpdateTicketStatusUseCase;
import com.cleverson.help_desk.user.infrastructure.security.UserDetailsImpl;
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

    @PatchMapping("{ticketId}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable UUID ticketId, @RequestBody UpdateTicketStatusRequestDTO request, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        this.updateTicketStatusUseCase.execute(new UpdateTicketStatusInput(
                ticketId,
                userDetails.getUser().id(),
                request.status()
        ));

        return ResponseEntity.noContent().build();
    }
}
