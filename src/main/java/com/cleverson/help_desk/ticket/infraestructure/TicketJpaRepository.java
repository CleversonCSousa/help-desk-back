package com.cleverson.help_desk.ticket.infraestructure;

import com.cleverson.help_desk.ticket.application.dto.GetTicketDetailsResponse;
import com.cleverson.help_desk.ticket.application.dto.TicketSummaryResponse;
import com.cleverson.help_desk.ticket.domain.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface TicketJpaRepository extends JpaRepository<TicketEntity, UUID> {
    //This solves the N+1 problem. Since I set the fetch type to "lazy", performing a standard JOIN would mean new queries being executed when accessing the technician's, client's, or service's data.
    @Query("""
        SELECT new com.cleverson.help_desk.ticket.application.dto.TicketSummaryResponse(
            t.id, 
            t.code, 
            t.title, 
            s.title, 
            (t.basePrice + COALESCE(SUM(additional.price), 0)), 
            u.name, 
            tu.name, 
            t.status, 
            t.updatedAt
        ) 
        FROM TicketEntity t
            JOIN t.service s
            JOIN t.user u
            JOIN t.technician tech
            JOIN tech.user tu
            LEFT JOIN TicketAdditionalServiceEntity additional ON additional.ticket = t
        GROUP BY t.id, t.code, t.title, s.title, t.basePrice, u.name, tu.name, t.status, t.updatedAt
        ORDER BY t.updatedAt DESC
    """)
    Page<TicketSummaryResponse> findAllSummaries(Pageable pageable);

    @Query("""
        SELECT new com.cleverson.help_desk.ticket.application.dto.TicketSummaryResponse(
            t.id, 
            t.code, 
            t.title, 
            s.title, 
            (t.basePrice + COALESCE(SUM(additional.price), 0)), 
            u.name, 
            tu.name, 
            t.status, 
            t.updatedAt
        ) 
        FROM TicketEntity t
            JOIN t.service s
            JOIN t.user u
            JOIN t.technician tech
            JOIN tech.user tu
            LEFT JOIN TicketAdditionalServiceEntity additional ON additional.ticket = t
                WHERE tech.id = :technicianId AND (:status IS NULL OR t.status = :status)
        GROUP BY t.id, t.code, t.title, s.title, t.basePrice, u.name, tu.name, t.status, t.updatedAt
        ORDER BY t.updatedAt DESC
    """)
    Page<TicketSummaryResponse> findAllSummariesByTechnicianId(UUID technicianId, TicketStatus status, Pageable pageable);

    @Query(value = """
        SELECT new com.cleverson.help_desk.ticket.application.dto.TicketSummaryResponse(
            t.id, 
            t.code, 
            t.title, 
            s.title, 
            (t.basePrice + COALESCE(SUM(additional.price), 0)), 
            u.name, 
            tu.name, 
            t.status, 
            t.updatedAt
        ) 
        FROM TicketEntity t
            JOIN t.service s
            JOIN t.user u
            JOIN t.technician tech
            JOIN tech.user tu
            LEFT JOIN TicketAdditionalServiceEntity additional ON additional.ticket = t
                WHERE u.id = :customerId
        GROUP BY t.id, t.code, t.title, s.title, t.basePrice, u.name, tu.name, t.status, t.updatedAt
        ORDER BY t.updatedAt DESC
    """)
    Page<TicketSummaryResponse> findAllSummariesByCustomerId(UUID customerId, Pageable pageable);

    Optional<TicketEntity> findByTechnicianId(UUID id);

    @Query("""
        SELECT t FROM TicketEntity t
            JOIN FETCH t.service s
            JOIN FETCH t.user u
            JOIN FETCH t.technician tech
            JOIN FETCH tech.user tu
            LEFT JOIN FETCH t.additionalServices
        WHERE t.id = :id
    """)
    Optional<TicketEntity> findDetailById(UUID id);
}