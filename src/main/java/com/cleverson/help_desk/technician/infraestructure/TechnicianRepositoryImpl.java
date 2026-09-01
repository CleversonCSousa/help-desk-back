package com.cleverson.help_desk.technician.infraestructure;

import com.cleverson.help_desk.technician.application.exceptions.TechnicianNotFound;
import com.cleverson.help_desk.technician.domain.Technician;
import com.cleverson.help_desk.technician.domain.TechnicianRepository;
import com.cleverson.help_desk.technician.domain.TechnicianSummary;
import com.cleverson.help_desk.technician.domain.WorkingHour;
import com.cleverson.help_desk.user.infrastructure.UserJpaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TechnicianRepositoryImpl implements TechnicianRepository {

    private final TechnicianJpaRepository technicianJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final EntityManager entityManager;

    public TechnicianRepositoryImpl(
            TechnicianJpaRepository technicianJpaRepository,
            UserJpaRepository userJpaRepository,
            EntityManager entityManager
    ) {
        this.technicianJpaRepository = technicianJpaRepository;
        this.userJpaRepository = userJpaRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<TechnicianSummary> findAllWithWorkingHours() {
        var technicians = this.technicianJpaRepository.findAllTechniciansWithDetails();

        return technicians.stream().map(tech -> {
            var workingHours = tech.getWorkingHours().stream()
                    .map(wh -> new WorkingHour(wh.getId(), wh.getTimeSlot()))
                    .toList();

            var user = tech.getUser();

            return new TechnicianSummary(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getAvatarUrl(),
                    workingHours
            );
        }).toList();
    }

    @Override
    public Optional<Technician> findById(UUID id) {
        return this.technicianJpaRepository.findById(id).map(entity -> {
            var workingHours = entity.getWorkingHours().stream()
                    .map(wh -> new WorkingHour(wh.getId(), wh.getTimeSlot()))
                    .toList();

            return new Technician(entity.getId(), workingHours);
        });
    }

    /*
     * Brief summary: Implements an "Upsert" strategy (Update or Insert) for technicians and their working hours.
     *
     * - Fetches the managed User entity to safely satisfy the @MapsId relationship.
     * - If the technician exists, retrieves the managed entity and clears old working hours for orphan removal.
     * - If the technician is new, instantiates it and uses "entityManager.persist()" to satisfy @MapsId identifier rules.
     * - Maps and adds the updated working hours, relying on Hibernate's session flush to synchronize changes.
     */
    @Override
    public void save(Technician technician) {
        var managedUser = this.userJpaRepository.findById(technician.id())
                .orElseThrow(() -> new TechnicianNotFound());

        var existingEntity = this.technicianJpaRepository.findById(technician.id());

        TechnicianEntity entity;

        if (existingEntity.isPresent()) {
            // update flow: retrieve the managed entity directly from the database
            entity = existingEntity.get();
            entity.getWorkingHours().clear();
        } else {
            // insert flow: create a new entity and use persist() to satisfy @MapsId identifier rules
            entity = new TechnicianEntity();
            entity.setId(technician.id());
            entity.setUser(managedUser);
            this.entityManager.persist(entity);
        }

        // map and add the new working hours linked to the entity
        var updatedWorkingHours = technician.workingHours().stream()
                .map(wh -> new WorkingHourEntity(
                        wh.id(),
                        wh.timeSlot(),
                        entity
                ))
                .toList();

        entity.getWorkingHours().addAll(updatedWorkingHours);
    }

}