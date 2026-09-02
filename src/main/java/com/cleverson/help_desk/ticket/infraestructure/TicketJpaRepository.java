package com.cleverson.help_desk.ticket.infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketJpaRepository extends JpaRepository<TicketEntity, UUID> {
}