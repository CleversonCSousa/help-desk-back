package com.cleverson.help_desk.technician.domain;

import java.time.LocalTime;
import java.util.UUID;

public record WorkingHour(
        UUID id,
        LocalTime timeSlot
) {
}
