package com.cleverson.help_desk.technician.infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkingHourJpaRepository extends JpaRepository<WorkingHourEntity, UUID> {
    List<WorkingHourEntity> findByUserId(UUID userId);
    void deleteByUserId(UUID userId);
}
