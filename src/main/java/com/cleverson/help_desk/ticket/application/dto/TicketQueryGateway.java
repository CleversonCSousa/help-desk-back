package com.cleverson.help_desk.ticket.application.dto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


// Created so the Domain layer (TicketRepository) does not need to depend on Application layer DTOs (TicketSummaryResponse)
public interface TicketQueryGateway {
    Page<TicketSummaryResponse> findAllSummaries(Pageable pageable);
}