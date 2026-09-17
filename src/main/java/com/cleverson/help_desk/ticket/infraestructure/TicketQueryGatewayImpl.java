package com.cleverson.help_desk.ticket.infraestructure;

import com.cleverson.help_desk.ticket.application.dto.GetTicketDetailsResponse;
import com.cleverson.help_desk.ticket.application.dto.TicketQueryGateway;
import com.cleverson.help_desk.ticket.application.dto.TicketSummaryResponse;
import com.cleverson.help_desk.ticket.domain.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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
    public Page<TicketSummaryResponse> findAllSummariesByTechnicianId(UUID technicianId, TicketStatus status, Pageable pageable) {
        return this.ticketJpaRepository.findAllSummariesByTechnicianId(technicianId, status, pageable);
    }

    @Override
    public Page<TicketSummaryResponse> findAllSummariesByCustomerId(UUID customerId, Pageable pageable) {
        return this.ticketJpaRepository.findAllSummariesByCustomerId(customerId, pageable);
    }

    @Override
    public Optional<GetTicketDetailsResponse> findDetailById(UUID id) {
        return this.ticketJpaRepository.findDetailById(id)
                .map(t -> {
                    var additionals = t.getAdditionalServices().stream()
                            .map(add -> new GetTicketDetailsResponse.AdditionalServiceDTO(
                                    add.getId(),
                                    add.getDescription(),
                                    add.getPrice()
                            )).toList();

                    var additionalsTotal = additionals.stream()
                            .map(GetTicketDetailsResponse.AdditionalServiceDTO::price)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    var totalPrice = t.getBasePrice().add(additionalsTotal);

                    return new GetTicketDetailsResponse(
                            t.getId(),
                            t.getCode(),
                            t.getTitle(),
                            t.getDescription(),
                            t.getBasePrice(),
                            totalPrice,
                            t.getStatus(),
                            t.getCreatedAt(),
                            t.getUpdatedAt(),
                            new GetTicketDetailsResponse.CustomerDTO(t.getUser().getId(), t.getUser().getName(), t.getUser().getAvatarUrl()),
                            new GetTicketDetailsResponse.ServiceDTO(t.getService().getId(), t.getService().getTitle()),
                            new GetTicketDetailsResponse.TechnicianDTO(t.getTechnician().getId(), t.getTechnician().getUser().getName(), t.getTechnician().getUser().getEmail(), t.getTechnician().getUser().getAvatarUrl()),
                            additionals
                    );
                });
    }
}
