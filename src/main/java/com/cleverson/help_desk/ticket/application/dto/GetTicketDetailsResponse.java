package com.cleverson.help_desk.ticket.application.dto;

import com.cleverson.help_desk.ticket.domain.TicketStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record GetTicketDetailsResponse(
        UUID id,
        Integer code,
        String title,
        String description,
        BigDecimal basePrice,
        BigDecimal totalPrice,
        TicketStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        CustomerDTO customer,
        ServiceDTO service,
        TechnicianDTO technician,
        List<AdditionalServiceDTO> additionalServices
) {

    public GetTicketDetailsResponse(
            UUID id,
            Integer code,
            String title,
            String description,
            BigDecimal basePrice,
            BigDecimal totalPrice,
            TicketStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            UUID customerId,
            String customerName,
            String customerAvatarUrl,
            UUID serviceId,
            String serviceTitle,
            UUID technicianId,
            String technicianName,
            String technicianEmail,
            String technicianAvatarUrl
    ) {
        this(
                id, code, title, description, basePrice, totalPrice, status, createdAt, updatedAt,
                new CustomerDTO(customerId, customerName, customerAvatarUrl),
                new ServiceDTO(serviceId, serviceTitle),
                new TechnicianDTO(technicianId, technicianName, technicianEmail, technicianAvatarUrl),
                List.of()
        );
    }

    public record CustomerDTO(UUID id, String name, String avatarUrl) {}
    public record ServiceDTO(UUID id, String title) {}
    public record TechnicianDTO(UUID id, String name, String email, String avatarUrl) {}
    public record AdditionalServiceDTO(UUID id, String description, BigDecimal price) {}
}