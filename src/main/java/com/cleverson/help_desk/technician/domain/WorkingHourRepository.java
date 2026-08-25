package com.cleverson.help_desk.technician.domain;

import java.util.List;
import java.util.UUID;

public interface WorkingHourRepository {
    List<WorkingHour> findByUserId(UUID userId);
    List<WorkingHour> saveAll(UUID userId, List<WorkingHour> workingHours);
    void deleteByUserId(UUID userId);
}
