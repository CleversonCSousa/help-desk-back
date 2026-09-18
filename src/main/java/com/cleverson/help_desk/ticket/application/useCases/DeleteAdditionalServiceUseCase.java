package com.cleverson.help_desk.ticket.application.useCases;

import com.cleverson.help_desk.ticket.application.exceptions.AdditionalServiceNotFoundException;
import com.cleverson.help_desk.ticket.application.exceptions.TicketNotFoundException;
import com.cleverson.help_desk.ticket.application.exceptions.UnauthorizedTicketAccessException;
import com.cleverson.help_desk.ticket.domain.Ticket;
import com.cleverson.help_desk.ticket.domain.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteAdditionalServiceUseCase {
    private final TicketRepository ticketRepository;

    public DeleteAdditionalServiceUseCase(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public void execute(UUID ticketId, UUID ticketAdditionalServiceId, UUID userId) {
        var ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException());

        if (!ticket.technicianId().equals(userId)) {
            throw new UnauthorizedTicketAccessException();
        }

        boolean serviceExists = ticket.additionalServices().stream()
                .anyMatch(service -> service.id().equals(ticketAdditionalServiceId));

        if (!serviceExists) {
            throw new AdditionalServiceNotFoundException();
        }
        var updatedAdditionals = ticket.additionalServices().stream()
                .filter(service -> !service.id().equals(ticketAdditionalServiceId))
                .toList();

        var updatedTicket = new Ticket(
                ticket.id(), ticket.code(), ticket.title(), ticket.description(),
                ticket.basePrice(), ticket.status(), ticket.customerId(),
                ticket.serviceId(), ticket.technicianId(), updatedAdditionals,
                ticket.createdAt(), ticket.updatedAt()
        );

        this.ticketRepository.save(updatedTicket);
    }
}
