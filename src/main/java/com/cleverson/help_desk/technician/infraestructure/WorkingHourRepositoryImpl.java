package com.cleverson.help_desk.technician.infraestructure;

import com.cleverson.help_desk.technician.domain.WorkingHour;
import com.cleverson.help_desk.technician.domain.WorkingHourRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class WorkingHourRepositoryImpl implements WorkingHourRepository {

    private final WorkingHourJpaRepository repository;

    public WorkingHourRepositoryImpl(WorkingHourJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<WorkingHour> findByTechnicianId(UUID technicianId) {
        return this.repository.findByTechnicianId(technicianId).stream()
                .map(entity -> new WorkingHour(
                        entity.getId(),
                        entity.getTimeSlot()
                )).toList();
    }


    @Override
    public List<WorkingHour> saveAll(UUID technicianId, List<WorkingHour> workingHours) {
        TechnicianEntity technician = new TechnicianEntity();
        technician.setId(technicianId);

        List<WorkingHourEntity> entities = workingHours.stream()
                .map(entity -> new WorkingHourEntity(
                        entity.id(),
                        entity.timeSlot(),
                        technician
                )).toList();

        List<WorkingHourEntity> savedEntities = repository.saveAll(entities);
        return savedEntities.stream()
                .map(entity -> new WorkingHour(
                        entity.getId(),
                        entity.getTimeSlot()
                )).toList();
    }

    @Override
    public void deleteByTechnicianId(UUID technicianId) {
        this.repository.deleteByTechnicianId(technicianId);
    }
}
