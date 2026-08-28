package com.cleverson.help_desk.technician.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TechnicianRepository {
    List<TechnicianSummary> findAllWithWorkingHours();
    Optional<Technician> findById(UUID id);
    void save(Technician technician);
}