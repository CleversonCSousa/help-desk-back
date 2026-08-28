package com.cleverson.help_desk.technician.application.dto;

import com.cleverson.help_desk.technician.domain.WorkingHour;

import java.util.List;
import java.util.UUID;

public record UpdateTechnicianInput(
        UUID id,
        String name,
        String email,
        List<WorkingHour> workingHours
) {
}
