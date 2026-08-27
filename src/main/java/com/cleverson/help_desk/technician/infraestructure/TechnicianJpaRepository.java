package com.cleverson.help_desk.technician.infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TechnicianJpaRepository extends JpaRepository<TechnicianEntity, UUID> {

    @Query("""
    SELECT DISTINCT t 
    FROM TechnicianEntity t 
    JOIN FETCH t.user u 
    LEFT JOIN FETCH t.workingHours
""")
    List<TechnicianEntity> findAllTechniciansWithDetails();
}
