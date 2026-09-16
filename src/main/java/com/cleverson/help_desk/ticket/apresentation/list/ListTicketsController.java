package com.cleverson.help_desk.ticket.apresentation.list;

import com.cleverson.help_desk.ticket.application.dto.TicketSummaryResponse;
import com.cleverson.help_desk.ticket.application.useCases.ListAllTicketsUseCase;
import com.cleverson.help_desk.ticket.application.useCases.ListCustomerTicketsUseCase;
import com.cleverson.help_desk.ticket.application.useCases.ListTechnicianTicketsUseCase;
import com.cleverson.help_desk.ticket.domain.TicketStatus;
import com.cleverson.help_desk.user.domain.User;
import com.cleverson.help_desk.user.domain.UserRole;
import com.cleverson.help_desk.user.infrastructure.security.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class ListTicketsController {
    private final ListAllTicketsUseCase listAllTicketsUseCase;
    private final ListCustomerTicketsUseCase listCustomerTicketsUseCase;
    private final ListTechnicianTicketsUseCase listTechnicianTicketsUseCase;


    public ListTicketsController(ListAllTicketsUseCase listAllTicketsUseCase, ListCustomerTicketsUseCase listCustomerTicketsUseCase, ListTechnicianTicketsUseCase listTechnicianTicketsUseCase) {
        this.listAllTicketsUseCase = listAllTicketsUseCase;
        this.listCustomerTicketsUseCase = listCustomerTicketsUseCase;
        this.listTechnicianTicketsUseCase = listTechnicianTicketsUseCase;
    }

    @GetMapping
    public ResponseEntity<Page<TicketSummaryResponse>> list(
            @PageableDefault(page = 0, size = 6) Pageable pageable,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) TicketStatus status,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if (size != null && size > 50) {
            pageable = PageRequest.of(pageable.getPageNumber(), 50, pageable.getSort());
        }
        User user = userDetails.getUser();

        if (user.role() == UserRole.ADMIN) {
            return ResponseEntity.ok(this.listAllTicketsUseCase.execute(pageable));
        }

        if (user.role() == UserRole.CUSTOMER) {
            return ResponseEntity.ok(this.listCustomerTicketsUseCase.execute(user.id(), pageable));
        }

        if (user.role() == UserRole.TECHNICIAN) {
            return ResponseEntity.ok(this.listTechnicianTicketsUseCase.execute(user.id(), status, pageable));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}