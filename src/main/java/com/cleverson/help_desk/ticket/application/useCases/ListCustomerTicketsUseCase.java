package com.cleverson.help_desk.ticket.application.useCases;

import com.cleverson.help_desk.ticket.application.dto.TicketQueryGateway;
import com.cleverson.help_desk.ticket.application.dto.TicketSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ListCustomerTicketsUseCase {
    private final TicketQueryGateway ticketQueryGateway;

    public ListCustomerTicketsUseCase(TicketQueryGateway ticketQueryGateway) {
        this.ticketQueryGateway = ticketQueryGateway;
    }

    public Page<TicketSummaryResponse> execute(UUID customerId, Pageable pageable) {
        return this.ticketQueryGateway.findAllSummariesByCustomerId(customerId, pageable);
    }
}
