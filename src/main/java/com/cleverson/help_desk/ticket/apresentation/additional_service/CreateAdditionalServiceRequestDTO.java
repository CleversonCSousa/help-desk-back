package com.cleverson.help_desk.ticket.apresentation.additional_service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateAdditionalServiceRequestDTO(
        @NotBlank(message = "Description is required")
        String description,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than zero")
        BigDecimal price
) {
}
