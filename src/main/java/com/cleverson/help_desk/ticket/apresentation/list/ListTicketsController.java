package com.cleverson.help_desk.ticket.apresentation.list;

import com.cleverson.help_desk.ticket.application.dto.ListTicketsInput;
import com.cleverson.help_desk.ticket.application.dto.TicketSummaryResponse;
import com.cleverson.help_desk.ticket.application.useCases.ListTicketsUseCase;
import com.cleverson.help_desk.user.domain.User;
import com.cleverson.help_desk.user.infrastructure.security.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class ListTicketsController {
    private final ListTicketsUseCase listTicketsUseCase;

    public ListTicketsController(ListTicketsUseCase listTicketsUseCase) {
        this.listTicketsUseCase = listTicketsUseCase;
    }

    @GetMapping
    public ResponseEntity<Page<TicketSummaryResponse>> list(
            @PageableDefault(page = 0, size = 6) Pageable pageable,
            @RequestParam(required = false) Integer size,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if (size != null && size > 50) {
            pageable = PageRequest.of(pageable.getPageNumber(), 50, pageable.getSort());
        }
        User user = userDetails.getUser();
        return ResponseEntity.ok(this.listTicketsUseCase.execute(new ListTicketsInput(
                user.id(),
                user.role(),
                pageable
        )));
    }
}