package com.cleverson.help_desk.technician.domain;

import java.util.List;
import java.util.UUID;

public interface WorkingHourRepository {
    List<WorkingHour> findByTechnicianId(UUID technicianId);
    List<WorkingHour> saveAll(UUID technicianId, List<WorkingHour> workingHours);
    void deleteByTechnicianId(UUID technicianId);
}
