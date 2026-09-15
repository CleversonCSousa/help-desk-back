package com.cleverson.help_desk.ticket.application.dto;

import com.cleverson.help_desk.user.domain.UserRole;

import java.util.UUID;

public record GetTicketDetailsInput(
        UUID ticketId,
        UUID userId,
        UserRole role
) {
}