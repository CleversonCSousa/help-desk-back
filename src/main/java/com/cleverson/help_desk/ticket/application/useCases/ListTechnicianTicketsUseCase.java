package com.cleverson.help_desk.ticket.application.useCases;

import com.cleverson.help_desk.ticket.application.dto.TicketQueryGateway;
import com.cleverson.help_desk.ticket.application.dto.TicketSummaryResponse;
import com.cleverson.help_desk.ticket.domain.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ListTechnicianTicketsUseCase {
    private final TicketQueryGateway ticketQueryGateway;

    public ListTechnicianTicketsUseCase(TicketQueryGateway ticketQueryGateway) {
        this.ticketQueryGateway = ticketQueryGateway;
    }

    public Page<TicketSummaryResponse> execute(UUID technicianId, TicketStatus status, Pageable pageable) {
        return this.ticketQueryGateway.findAllSummariesByTechnicianId(technicianId, status, pageable);
    }
}