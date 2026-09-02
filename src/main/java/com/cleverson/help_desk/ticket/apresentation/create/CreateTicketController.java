package com.cleverson.help_desk.ticket.apresentation.create;

import com.cleverson.help_desk.ticket.application.dto.CreateTicketInput;
import com.cleverson.help_desk.ticket.application.useCases.CreateTicketUseCase;
import com.cleverson.help_desk.user.infrastructure.UserEntity;
import com.cleverson.help_desk.user.infrastructure.security.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class CreateTicketController {
    private final CreateTicketUseCase createTicketUseCase;

    public CreateTicketController(CreateTicketUseCase createTicketUseCase) {
        this.createTicketUseCase = createTicketUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateTicketResponseDTO> create(@Valid @RequestBody CreateTicketRequestDTO request) {

        var userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var customerId = userDetails.getUser().id();

        var ticket = this.createTicketUseCase.execute(new CreateTicketInput(
                customerId,
                request.title(),
                request.description(),
                request.serviceId()
        ));

        var response = new CreateTicketResponseDTO(
                "Ticket created successfully",
                ticket
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
