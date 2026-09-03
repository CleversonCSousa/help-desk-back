package com.cleverson.help_desk.ticket.infraestructure;

import com.cleverson.help_desk.ticket.application.dto.TicketQueryGateway;
import com.cleverson.help_desk.ticket.application.dto.TicketSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketQueryGatewayImpl implements TicketQueryGateway {
    private final TicketJpaRepository ticketJpaRepository;

    public TicketQueryGatewayImpl(TicketJpaRepository ticketJpaRepository) {
        this.ticketJpaRepository = ticketJpaRepository;
    }

    @Override
    public Page<TicketSummaryResponse> findAllSummaries(Pageable pageable) {
        return this.ticketJpaRepository.findAllSummaries(pageable);
    }
}
