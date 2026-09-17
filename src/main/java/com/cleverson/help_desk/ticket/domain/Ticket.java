package com.cleverson.help_desk.ticket.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record Ticket(
        UUID id,
        Integer code,
        String title,
        String description,
        BigDecimal basePrice,
        TicketStatus status,

        UUID customerId,
        UUID serviceId,
        UUID technicianId,

        List<TicketAdditionalService> additionalServices,

        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    // secondary constructor to avoid breaking old declarations
    public Ticket(UUID id, Integer code, String title, String description, BigDecimal basePrice,
                  TicketStatus status, UUID customerId, UUID serviceId, UUID technicianId,
                  LocalDateTime createdAt, LocalDateTime updatedAt) {

        this(id, code, title, description, basePrice, status, customerId, serviceId, technicianId,
                List.of(), createdAt, updatedAt);
    }

    public Ticket changeStatus(TicketStatus newStatus) {
        return new Ticket(
                id,
                code,
                title,
                description,
                basePrice,
                newStatus,
                customerId,
                serviceId,
                technicianId,
                additionalServices,
                createdAt,
                updatedAt
        );
    }

    public Ticket addAdditionalService(TicketAdditionalService newService) {
        List<TicketAdditionalService> updatedServices = new ArrayList<>(
                this.additionalServices != null ? this.additionalServices : List.of()
        );

        updatedServices.add(newService);

        return new Ticket(
                id,
                code,
                title,
                description,
                basePrice,
                status,
                customerId,
                serviceId,
                technicianId,
                updatedServices,
                createdAt,
                updatedAt
        );
    }
}
