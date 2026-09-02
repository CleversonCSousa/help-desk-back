package com.cleverson.help_desk.ticket.infraestructure;

import com.cleverson.help_desk.service.infraestructure.ServiceEntity;
import com.cleverson.help_desk.technician.infraestructure.TechnicianEntity;
import com.cleverson.help_desk.ticket.domain.TicketStatus;
import com.cleverson.help_desk.user.infrastructure.UserEntity;
import jakarta.persistence.*;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tickets", indexes = {
        @Index(name = "idx_ticket_created_at", columnList = "created_at"),
        @Index(name = "idx_ticket_technician_status", columnList = "technician_id, status")
})
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // @Generated tells Hibernate that the database generates this value, so it must be fetched after an insert
    @Generated
    @Column(columnDefinition = "serial", insertable = false, updatable = false, unique = true)
    private Integer code;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "base_price", nullable = false)
    private BigDecimal basePrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status = TicketStatus.OPEN;

    // references

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEntity service;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technician_id", nullable = false)
    private TechnicianEntity technician;

    // audit

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
