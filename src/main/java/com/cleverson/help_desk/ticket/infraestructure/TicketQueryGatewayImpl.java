package com.cleverson.help_desk.ticket.infraestructure;

import com.cleverson.help_desk.ticket.application.dto.GetTicketDetailsResponse;
import com.cleverson.help_desk.ticket.application.dto.TicketQueryGateway;
import com.cleverson.help_desk.ticket.application.dto.TicketSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

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

    @Override
    public Page<TicketSummaryResponse> findAllSummariesByTechnicianId(UUID technicianId, Pageable pageable) {
        return this.ticketJpaRepository.findAllSummariesByTechnicianId(technicianId, pageable);
    }

    @Override
    public Page<TicketSummaryResponse> findAllSummariesByCustomerId(UUID customerId, Pageable pageable) {
        return this.ticketJpaRepository.findAllSummariesByCustomerId(customerId, pageable);
    }

    @Override
    public Optional<GetTicketDetailsResponse> findDetailById(UUID id) {
        return this.ticketJpaRepository.findDetailById(id);
    }
}
