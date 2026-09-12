package com.cleverson.help_desk.ticket.application.dto;

import com.cleverson.help_desk.user.domain.UserRole;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public record ListTicketsInput(
        UUID userId,
        UserRole role,
        Pageable pageable
) {
}
