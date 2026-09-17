package com.cleverson.help_desk.ticket.infraestructure;

import com.cleverson.help_desk.service.infraestructure.ServiceJpaRepository;
import com.cleverson.help_desk.technician.infraestructure.TechnicianJpaRepository;
import com.cleverson.help_desk.ticket.domain.Ticket;
import com.cleverson.help_desk.ticket.domain.TicketAdditionalService;
import com.cleverson.help_desk.ticket.domain.TicketRepository;
import com.cleverson.help_desk.user.infrastructure.UserJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
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
    // Ensures that all operations performed by Hibernate are handled within a transaction.
    @Transactional
    public Ticket save(Ticket ticket) {
        var customer = this.userJpaRepository.getReferenceById(ticket.customerId());
        var service = this.serviceJpaRepository.getReferenceById(ticket.serviceId());
        var technician = this.technicianJpaRepository.getReferenceById(ticket.technicianId());
        final TicketEntity entity;
        // upsert strategy
        if(ticket.id() != null) {
            entity = this.ticketJpaRepository.findById(ticket.id()).orElse(new TicketEntity());
        } else {
            entity = new TicketEntity();
        }

        entity.setTitle(ticket.title());
        entity.setDescription(ticket.description());
        entity.setBasePrice(ticket.basePrice());
        entity.setStatus(ticket.status());
        entity.setUser(customer);
        entity.setService(service);
        entity.setTechnician(technician);

        if (ticket.additionalServices() != null) {
            // if there are no additional services list initialized yet, create a new ArrayList, otherwise prepare to clear stale elements
            if (entity.getAdditionalServices() == null) {
                entity.setAdditionalServices(new ArrayList<>());
            } else {
                // this is essential because, since orphanRemoval is set to true, clearing the collection lets Hibernate detect removed items and delete them from the database
                entity.getAdditionalServices().clear();
            }

            // convert domain layer to infra layer
            var additionalEntities = ticket.additionalServices().stream().map(add -> {
                var additionalEntity = new TicketAdditionalServiceEntity();
                additionalEntity.setId(add.id());
                additionalEntity.setDescription(add.description());
                additionalEntity.setPrice(add.price());

                additionalEntity.setTicket(entity);
                additionalEntity.setService(this.serviceJpaRepository.getReferenceById(add.serviceId()));

                return additionalEntity;
            }).toList();

            entity.getAdditionalServices().addAll(additionalEntities);
        }

        var savedEntity = this.ticketJpaRepository.save(entity);
        return mapperToDomain(savedEntity);
    }

    @Override
    public Optional<Ticket> findById(UUID id) {
        return this.ticketJpaRepository.findById(id).map(this::mapperToDomain);
    }

    private Ticket mapperToDomain(TicketEntity entity) {
        List<TicketAdditionalService> additionals = entity.getAdditionalServices() != null
                ? entity.getAdditionalServices().stream()
                .map(add -> new TicketAdditionalService(
                        add.getId(),
                        entity.getId(),
                        add.getService().getId(),
                        add.getDescription(),
                        add.getPrice()
                )).toList()
                : List.of();

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
                additionals,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
