package com.cleverson.help_desk.ticket.infraestructure;

import com.cleverson.help_desk.service.infraestructure.ServiceJpaRepository;
import com.cleverson.help_desk.technician.infraestructure.TechnicianJpaRepository;
import com.cleverson.help_desk.ticket.domain.Ticket;
import com.cleverson.help_desk.ticket.domain.TicketRepository;
import com.cleverson.help_desk.user.infrastructure.UserJpaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class TicketRepositoryImpl implements TicketRepository {
    private final TicketJpaRepository ticketJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final ServiceJpaRepository serviceJpaRepository;
    private final TechnicianJpaRepository technicianJpaRepository;
    private final EntityManager entityManager;

    public TicketRepositoryImpl(
            TicketJpaRepository ticketJpaRepository,
            UserJpaRepository userJpaRepository,
            ServiceJpaRepository serviceJpaRepository,
            TechnicianJpaRepository technicianJpaRepository,
            EntityManager entityManager
    ) {
        this.ticketJpaRepository = ticketJpaRepository;
        this.userJpaRepository = userJpaRepository;
        this.serviceJpaRepository = serviceJpaRepository;
        this.technicianJpaRepository = technicianJpaRepository;
        this.entityManager = entityManager;
    }

    // This method assumes that the caller has already verified whether the records exist in the database.
    @Override
    public Ticket save(Ticket ticket) {
        var customer = this.userJpaRepository.getReferenceById(ticket.customerId());
        var service = this.serviceJpaRepository.getReferenceById(ticket.serviceId());
        var technician = this.technicianJpaRepository.getReferenceById(ticket.technicianId());

        var entity = new TicketEntity();
        // upsert strategy
        if(ticket.id() != null) {
            entity = this.ticketJpaRepository.findById(ticket.id()).orElse(new TicketEntity());
        }

        entity.setTitle(ticket.title());
        entity.setDescription(ticket.description());
        entity.setBasePrice(ticket.basePrice());
        entity.setStatus(ticket.status());
        entity.setUser(customer);
        entity.setService(service);
        entity.setTechnician(technician);

        var savedEntity = this.ticketJpaRepository.save(entity);
        return mapperToDomain(savedEntity);
    }

    @Override
    public Optional<Ticket> findById(UUID id) {
        return this.ticketJpaRepository.findById(id).map(this::mapperToDomain);
    }

    private Ticket mapperToDomain(TicketEntity entity) {
        return new Ticket(
                entity.getId(),
                entity.getCode(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getBasePrice(),
                entity.getStatus(),
                entity.getUser().getId(),
                entity.getService().getId(),
                entity.getTechnician().getId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
