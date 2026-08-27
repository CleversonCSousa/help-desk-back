package com.cleverson.help_desk.technician.infraestructure;

import com.cleverson.help_desk.user.infrastructure.UserEntity;
import jakarta.persistence.*;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "technicians")
public class TechnicianEntity {
    @Id
    @Setter
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "technician", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkingHourEntity> workingHours;

    public UUID getId() { return id; }
    public UserEntity getUser() { return user; }
    public List<WorkingHourEntity> getWorkingHours() { return workingHours; }
}