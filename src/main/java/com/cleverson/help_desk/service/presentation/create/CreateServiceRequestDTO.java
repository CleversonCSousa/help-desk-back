package com.cleverson.help_desk.service.presentation.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateServiceRequestDTO(

        @NotBlank(message = "Title is mandatory")
        String title,

        String description,

        @NotNull(message = "Price is mandatory")
        @Positive(message = "Price must be greater than zero")
        BigDecimal price
) {
}
