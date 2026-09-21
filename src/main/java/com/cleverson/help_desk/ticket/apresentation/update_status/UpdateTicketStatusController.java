package com.cleverson.help_desk.ticket.apresentation.update_status;

<<<<<<< HEAD
import com.cleverson.help_desk.ticket.application.useCases.UpdateTicketStatusUseCase;
import com.cleverson.help_desk.user.infrastructure.security.UserDetailsImpl;
import jakarta.validation.Valid;
=======
import com.cleverson.help_desk.ticket.application.dto.UpdateTicketStatusInput;
import com.cleverson.help_desk.ticket.application.useCases.UpdateTicketStatusUseCase;
import com.cleverson.help_desk.user.infrastructure.security.UserDetailsImpl;
>>>>>>> 9a6fb06bebdd429cb5d3b2dcc9005bae1b6fc0ef
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

<<<<<<< HEAD
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable UUID id, @Valid @RequestBody UpdateTicketStatusRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        updateTicketStatusUseCase.execute(id, userDetails.getUser(), request.status());
=======
    @PatchMapping("{ticketId}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable UUID ticketId, @RequestBody UpdateTicketStatusRequestDTO request, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        this.updateTicketStatusUseCase.execute(new UpdateTicketStatusInput(
                ticketId,
                userDetails.getUser().id(),
                request.status()
        ));

>>>>>>> 9a6fb06bebdd429cb5d3b2dcc9005bae1b6fc0ef
        return ResponseEntity.noContent().build();
    }
}
