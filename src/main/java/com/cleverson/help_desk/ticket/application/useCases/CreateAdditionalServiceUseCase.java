package com.cleverson.help_desk.ticket.application.useCases;

import com.cleverson.help_desk.ticket.application.dto.CreateAdditionalServiceInput;
import com.cleverson.help_desk.ticket.application.dto.CreateAdditionalServiceResponse;
import com.cleverson.help_desk.ticket.application.exceptions.TicketNotFoundException;
import com.cleverson.help_desk.ticket.domain.Ticket;
import com.cleverson.help_desk.ticket.domain.TicketAdditionalService;
import com.cleverson.help_desk.ticket.domain.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateAdditionalServiceUseCase {
    private final TicketRepository ticketRepository;

    public CreateAdditionalServiceUseCase(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public CreateAdditionalServiceResponse execute(UUID ticketId, UUID userId, CreateAdditionalServiceInput input) {
        var ticket = this.ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException());

        var newAdditionalService = new TicketAdditionalService(
                null,
                ticketId,
                ticket.serviceId(),
                input.description(),
                input.price()
        );

        var updatedTicket = ticket.addAdditionalService(newAdditionalService);

        this.ticketRepository.save(updatedTicket);

        return new CreateAdditionalServiceResponse(
                newAdditionalService.id(),
                newAdditionalService.description(),
                newAdditionalService.price()
        );
    }
}
