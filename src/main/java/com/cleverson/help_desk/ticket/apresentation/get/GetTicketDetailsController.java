package com.cleverson.help_desk.ticket.apresentation.get;

import com.cleverson.help_desk.ticket.application.dto.GetTicketDetailsInput;
import com.cleverson.help_desk.ticket.application.dto.GetTicketDetailsResponse;
import com.cleverson.help_desk.ticket.application.useCases.GetTicketDetailsUseCase;
import com.cleverson.help_desk.user.infrastructure.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("tickets")
public class GetTicketDetailsController {

    private final GetTicketDetailsUseCase getTicketDetailsUseCase;

    public GetTicketDetailsController(GetTicketDetailsUseCase getTicketDetailsUseCase) {
        this.getTicketDetailsUseCase = getTicketDetailsUseCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetTicketDetailsResponse> getDetails(@PathVariable UUID id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userDetails.getUser();

        var input = new GetTicketDetailsInput(id, user.id(), user.role());

        var response = this.getTicketDetailsUseCase.execute(input);

        return ResponseEntity.ok(response);
    }
}
