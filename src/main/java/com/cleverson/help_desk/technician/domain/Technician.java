package com.cleverson.help_desk.technician.domain;

import java.util.List;
import java.util.UUID;

public record Technician(
        UUID userId,
        List<WorkingHour> workingHours
) {
}
