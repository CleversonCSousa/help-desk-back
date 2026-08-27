package com.cleverson.help_desk.technician.infraestructure;

import com.cleverson.help_desk.technician.domain.TechnicianRepository;
import com.cleverson.help_desk.technician.domain.TechnicianSummary;
import com.cleverson.help_desk.technician.domain.WorkingHour;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}