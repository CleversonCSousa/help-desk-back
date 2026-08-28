package com.cleverson.help_desk.technician.presentation;

import com.cleverson.help_desk.technician.domain.WorkingHour;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record UpdateTechnicianRequestDTO(
        @NotNull(message = "Id is required")
        UUID id,

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        List<WorkingHour>workingHours
) {
}
