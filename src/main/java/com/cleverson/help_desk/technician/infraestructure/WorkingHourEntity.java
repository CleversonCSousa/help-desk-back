package com.cleverson.help_desk.technician.infraestructure;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "working_hour")
@NoArgsConstructor
@AllArgsConstructor
public class WorkingHourEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "time_slot", nullable = false)
    private LocalTime timeSlot;

    // It also prevents it from pulling the user's data.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private TechnicianEntity technician;

}
