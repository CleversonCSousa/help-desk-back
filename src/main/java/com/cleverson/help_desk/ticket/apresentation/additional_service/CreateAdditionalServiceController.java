package com.cleverson.help_desk.ticket.apresentation.additional_service;

import com.cleverson.help_desk.ticket.application.dto.CreateAdditionalServiceInput;
import com.cleverson.help_desk.ticket.application.dto.CreateAdditionalServiceResponse;
import com.cleverson.help_desk.ticket.application.useCases.CreateAdditionalServiceUseCase;
import com.cleverson.help_desk.user.infrastructure.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tickets")
public class CreateAdditionalServiceController {
    private final CreateAdditionalServiceUseCase createAdditionalServiceUseCase;

    public CreateAdditionalServiceController(CreateAdditionalServiceUseCase createAdditionalServiceUseCase) {
        this.createAdditionalServiceUseCase = createAdditionalServiceUseCase;
    }

    @PostMapping("/{ticketId}/additional-services")
    public ResponseEntity<CreateAdditionalServiceResponse> handle(
            @PathVariable UUID ticketId,
            @RequestBody CreateAdditionalServiceInput input,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) {
        UUID technicianId = userDetails.getUser().id();

        CreateAdditionalServiceResponse response = this.createAdditionalServiceUseCase.execute(
                ticketId,
                technicianId,
                input
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
