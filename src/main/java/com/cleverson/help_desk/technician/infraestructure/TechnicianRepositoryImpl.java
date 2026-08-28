package com.cleverson.help_desk.technician.infraestructure;

import com.cleverson.help_desk.technician.domain.Technician;
import com.cleverson.help_desk.technician.domain.TechnicianRepository;
import com.cleverson.help_desk.technician.domain.TechnicianSummary;
import com.cleverson.help_desk.technician.domain.WorkingHour;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TechnicianRepositoryImpl implements TechnicianRepository {

    private final TechnicianJpaRepository technicianJpaRepository;

    public TechnicianRepositoryImpl(TechnicianJpaRepository technicianJpaRepository) {
        this.technicianJpaRepository = technicianJpaRepository;
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
     * Brief summary: Before actually saving the technician and their working hours, this method needs to update
     * the collection to avoid inserting duplicate schedules or keeping removed ones.
     *
     * - The "getReferenceById" method creates a proxy object that holds the technician's ID without requiring a
     *   database lookup (the check for the technician's existence is already handled within the Use Case).
     * - When we call "getWorkingHours()", the database executes a "SELECT" statement to retrieve the tracked collection.
     * - When we invoke "clear()", Hibernate prepares a "DELETE" operation behind the scenes for the old schedules
     *   due to "orphanRemoval = true".
     * - When we invoke "addAll()", Hibernate prepares the "INSERT" statements for the new schedules.
     * - All these queued operations (DELETEs and INSERTs) are finally flushed/executed in the database at the end of the transaction.
     */
    @Override
    public void save(Technician technician) {
        var entity = this.technicianJpaRepository.getReferenceById(technician.id());
        entity.getWorkingHours().clear();

        var updatedWorkingHours = technician.workingHours().stream()
                .map(wh -> new WorkingHourEntity(
                        wh.id(),
                        wh.timeSlot(),
                        entity
                ))
                .toList();
        entity.getWorkingHours().addAll(updatedWorkingHours);

        this.technicianJpaRepository.save(entity);
    }

}