package com.cleverson.help_desk.ticket.application.useCases;

import com.cleverson.help_desk.ticket.application.exceptions.TicketNotFoundException;
import com.cleverson.help_desk.ticket.application.exceptions.UnauthorizedTicketAccessException;
import com.cleverson.help_desk.ticket.domain.Ticket;
import com.cleverson.help_desk.ticket.domain.TicketRepository;
import com.cleverson.help_desk.ticket.domain.TicketStatus;
import com.cleverson.help_desk.user.domain.User;
import com.cleverson.help_desk.user.domain.UserRole;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateTicketStatusUseCase {
    private final TicketRepository ticketRepository;

    public UpdateTicketStatusUseCase(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public void execute(UUID ticketId, User user, TicketStatus status) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(TicketNotFoundException::new);
        if (user.role() == UserRole.TECHNICIAN) {
            this.checkIfTechnicianIsAssignedToTicket(ticket, user);
        } else if (user.role() != UserRole.ADMIN) {
            throw new UnauthorizedTicketAccessException();
        }

        ticket.changeStatus(status);

        ticketRepository.save(ticket);
    }

    private void checkIfTechnicianIsAssignedToTicket(Ticket ticket, User user) {
        if (!ticket.technicianId().equals(user.id())) {
            throw new UnauthorizedTicketAccessException();
        }
    }

}
