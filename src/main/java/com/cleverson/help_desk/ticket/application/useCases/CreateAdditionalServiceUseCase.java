package com.cleverson.help_desk.ticket.application.useCases;

import com.cleverson.help_desk.ticket.application.dto.CreateAdditionalServiceInput;
import com.cleverson.help_desk.ticket.application.dto.CreateAdditionalServiceResponse;
import com.cleverson.help_desk.ticket.application.exceptions.TicketNotFoundException;
import com.cleverson.help_desk.ticket.application.exceptions.UnauthorizedTicketAccessException;
import com.cleverson.help_desk.ticket.domain.Ticket;
import com.cleverson.help_desk.ticket.domain.TicketAdditionalService;
import com.cleverson.help_desk.ticket.domain.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CreateAdditionalServiceUseCase {
    private final TicketRepository ticketRepository;

    public CreateAdditionalServiceUseCase(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public CreateAdditionalServiceResponse execute(UUID ticketId, UUID userId, CreateAdditionalServiceInput input) {
        var ticket = this.ticketRepository.findById(ticketId)
                .orElseThrow(TicketNotFoundException::new);

        if (!ticket.technicianId().equals(userId)) {
            throw new UnauthorizedTicketAccessException();
        }

        // mapping existing service IDs before adding the new one
        var existingServiceIds = ticket.additionalServices().stream()
                .map(TicketAdditionalService::id)
                .collect(Collectors.toSet());

        var newAdditionalService = new TicketAdditionalService(
                null,
                ticketId,
                ticket.serviceId(),
                input.description(),
                input.price()
        );

        var updatedTicket = ticket.addAdditionalService(newAdditionalService);
        var savedTicket = this.ticketRepository.save(updatedTicket);

        // find the newly created service by checking for the new ID
        var createdService = savedTicket.additionalServices().stream()
                .filter(service -> !existingServiceIds.contains(service.id()))
                .findFirst()
                .orElseThrow();

        return new CreateAdditionalServiceResponse(
                createdService.id(),
                createdService.description(),
                createdService.price()
        );
    }
}
