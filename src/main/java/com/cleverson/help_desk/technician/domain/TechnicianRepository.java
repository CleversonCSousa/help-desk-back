package com.cleverson.help_desk.technician.domain;

import com.cleverson.help_desk.technician.infraestructure.TechnicianEntity;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TechnicianRepository {
    List<TechnicianSummary> findAllWithWorkingHours();
    Optional<Technician> findById(UUID id);
    void save(Technician technician);
    List<TechnicianEntity> findAvailableTechnicianWithLeastLoad(LocalTime currentTime);
}