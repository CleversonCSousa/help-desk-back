package com.cleverson.help_desk.technician.application.dto;

import com.cleverson.help_desk.technician.domain.WorkingHour;

import java.util.List;

public record CreateTechnicianInput(
        String name,
        String email,
        String password,
        List<WorkingHour> workingHours
) {
}
