package com.cleverson.help_desk.technician.application.dto;

import com.cleverson.help_desk.technician.domain.WorkingHour;

import java.util.List;
import java.util.UUID;

public record TechnicianProfileResponse(
        UUID id,
        String name,
        String email,
        String avatarUrl,
        List<WorkingHour> workingHours
) {
}
