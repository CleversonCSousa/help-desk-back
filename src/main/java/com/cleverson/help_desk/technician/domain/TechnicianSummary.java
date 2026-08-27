package com.cleverson.help_desk.technician.domain;

import java.util.List;
import java.util.UUID;

public record TechnicianSummary(
        UUID id,
        String name,
        String email,
        String avatarUrl,
        List<WorkingHour> workingHours
) {}