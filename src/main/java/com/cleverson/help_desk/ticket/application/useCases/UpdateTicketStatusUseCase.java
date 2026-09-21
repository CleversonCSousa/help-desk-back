package com.cleverson.help_desk.ticket.application.useCases;

import com.cleverson.help_desk.ticket.application.dto.UpdateTicketStatusInput;
import com.cleverson.help_desk.ticket.application.exceptions.TicketNotFoundException;
import com.cleverson.help_desk.ticket.application.exceptions.UnauthorizedTicketAccessException;
import com.cleverson.help_desk.ticket.domain.Ticket;
import com.cleverson.help_desk.ticket.domain.TicketRepository;
import com.cleverson.help_desk.user.application.exceptions.UserNotFoundException;
import com.cleverson.help_desk.user.domain.User;
import com.cleverson.help_desk.user.domain.UserRepository;
import com.cleverson.help_desk.user.domain.UserRole;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateTicketStatusUseCase {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public UpdateTicketStatusUseCase(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public void execute(UpdateTicketStatusInput input) {
        User user = this.userRepository.findById(input.userId())
                .orElseThrow(UserNotFoundException::new);

        Ticket ticket = this.ticketRepository.findById(input.ticketId())
                .orElseThrow(TicketNotFoundException::new);

        if (user.role() == UserRole.TECHNICIAN) {
            this.checkIfTechnicianIsAssignedToTicket(ticket, user.id());
        } else if (user.role() != UserRole.ADMIN) {
            throw new UnauthorizedTicketAccessException();
        }

        Ticket updatedTicket = ticket.changeStatus(input.status());

        this.ticketRepository.save(updatedTicket);
    }

    private void checkIfTechnicianIsAssignedToTicket(Ticket ticket, UUID technicianId) {
        if (!ticket.technicianId().equals(technicianId)) {
            throw new UnauthorizedTicketAccessException();
        }
    }
}