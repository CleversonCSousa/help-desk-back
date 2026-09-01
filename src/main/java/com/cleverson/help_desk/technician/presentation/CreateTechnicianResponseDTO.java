package com.cleverson.help_desk.technician.presentation;

import com.cleverson.help_desk.technician.domain.TechnicianSummary;

public record CreateTechnicianResponseDTO(
        String message,
        TechnicianSummary technician
) {
}
