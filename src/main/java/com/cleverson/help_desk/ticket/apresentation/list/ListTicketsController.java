package com.cleverson.help_desk.ticket.apresentation.list;

import com.cleverson.help_desk.ticket.application.dto.TicketSummaryResponse;
import com.cleverson.help_desk.ticket.application.useCases.ListTicketsUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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
            @RequestParam(required = false) Integer size
    ) {
        if (size != null && size > 50) {
            pageable = PageRequest.of(pageable.getPageNumber(), 50, pageable.getSort());
        }
        return ResponseEntity.ok(this.listTicketsUseCase.execute(pageable));
    }
}