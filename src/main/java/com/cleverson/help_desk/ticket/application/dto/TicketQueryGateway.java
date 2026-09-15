package com.cleverson.help_desk.ticket.application.dto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;


// Created so the Domain layer (TicketRepository) does not need to depend on Application layer DTOs (TicketSummaryResponse)
public interface TicketQueryGateway {
    Page<TicketSummaryResponse> findAllSummaries(Pageable pageable);
    Page<TicketSummaryResponse> findAllSummariesByTechnicianId(UUID technicianId, Pageable pageable);
    Page<TicketSummaryResponse> findAllSummariesByCustomerId(UUID customerId, Pageable pageable);
    Optional<GetTicketDetailsResponse> findDetailById(UUID id);
}