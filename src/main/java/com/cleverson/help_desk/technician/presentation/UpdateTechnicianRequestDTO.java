package com.cleverson.help_desk.technician.presentation;

import com.cleverson.help_desk.technician.domain.WorkingHour;

import java.util.List;
import java.util.UUID;

public record UpdateTechnicianRequestDTO(
        UUID id,
        String name,
        String email,
        List<WorkingHour>workingHours
) {
}
