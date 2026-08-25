package com.cleverson.help_desk.technician.infraestructure;

import com.cleverson.help_desk.technician.domain.WorkingHour;
import com.cleverson.help_desk.technician.domain.WorkingHourRepository;
import com.cleverson.help_desk.user.infrastructure.UserEntity;
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
    public List<WorkingHour> findByUserId(UUID userId) {
        return this.repository.findByUserId(userId).stream()
                .map(entity -> new WorkingHour(
                        entity.getId(),
                        entity.getTimeSlot()
                )).toList();
    }

    @Override
    public List<WorkingHour> saveAll(UUID userId, List<WorkingHour> workingHours) {
        UserEntity user = new UserEntity();
        user.setId(userId);

        List<WorkingHourEntity> entities = workingHours.stream()
                .map(entity -> new WorkingHourEntity(
                        entity.id(),
                        entity.timeSlot(),
                        user
                )).toList();

        List<WorkingHourEntity> savedEntities = repository.saveAll(entities);
        return savedEntities.stream()
                .map(entity -> new WorkingHour(
                        entity.getId(),
                        entity.getTimeSlot()
                )).toList();
    }

    @Override
    public void deleteByUserId(UUID userId) {
        this.repository.deleteByUserId(userId);
    }
}
