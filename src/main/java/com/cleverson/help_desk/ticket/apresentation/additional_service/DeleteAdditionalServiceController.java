package com.cleverson.help_desk.ticket.apresentation.additional_service;

import com.cleverson.help_desk.ticket.application.useCases.DeleteAdditionalServiceUseCase;
import com.cleverson.help_desk.user.infrastructure.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("tickets")
public class DeleteAdditionalServiceController {

    private final DeleteAdditionalServiceUseCase deleteAdditionalServiceUseCase;

    public DeleteAdditionalServiceController(DeleteAdditionalServiceUseCase deleteAdditionalServiceUseCase) {
        this.deleteAdditionalServiceUseCase = deleteAdditionalServiceUseCase;
    }

    @DeleteMapping("/{ticketId}/additional-services/{additionalServiceId}")
    public ResponseEntity<Void> deleteAdditionalService(
            @PathVariable UUID ticketId,
            @PathVariable UUID additionalServiceId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        deleteAdditionalServiceUseCase.execute(ticketId, additionalServiceId, userDetails.getUser().id());

        return ResponseEntity.noContent().build();
    }
}