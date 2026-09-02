package com.cleverson.help_desk.technician.infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Query("""
    /* Selects the technician based on the lowest workload during the current time slot */
    SELECT t
        FROM TechnicianEntity t
        JOIN WorkingHourEntity wh ON wh.technician = t
        /* Picks up only active tickets regardless of creation hour to ensure real-world workload limits */
        LEFT JOIN TicketEntity tkActive ON tkActive.technician = t
             AND tkActive.status IN ('OPEN', 'IN_PROGRESS')
        /* Counts total load for today to distribute tickets fairly using index-friendly date ranges */
        LEFT JOIN TicketEntity tkAllDay ON tkAllDay.technician = t
             AND tkAllDay.createdAt >= :startOfDay 
             AND tkAllDay.createdAt < :endOfDay
        /* Filter only the technicians whose shift covers the current time */
        WHERE FUNCTION('date_part', 'hour', wh.timeSlot) = FUNCTION('date_part', 'hour', CAST(:currentTime AS time)) 
        GROUP BY t
        /* Prevents the selection of a technician who is already handling a service call */
        HAVING COUNT(tkActive) = 0
        ORDER BY COUNT(tkAllDay) ASC
""")
    List<TechnicianEntity> findAvailableTechnicianWithLeastLoad(
            @Param("currentTime") LocalTime currentTime,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);
}
