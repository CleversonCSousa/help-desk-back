package com.cleverson.help_desk.ticket.application.useCases;

import com.cleverson.help_desk.ticket.application.dto.GetTicketDetailsInput;
import com.cleverson.help_desk.ticket.application.dto.GetTicketDetailsResponse;
import com.cleverson.help_desk.ticket.application.dto.TicketQueryGateway;
import com.cleverson.help_desk.ticket.application.exceptions.TicketNotFoundException;
import com.cleverson.help_desk.ticket.application.exceptions.UnauthorizedTicketAccessException;
import com.cleverson.help_desk.user.domain.UserRole;
import org.springframework.stereotype.Service;

@Service
public class GetTicketDetailsUseCase {
    private final TicketQueryGateway ticketQueryGateway;

    public GetTicketDetailsUseCase(TicketQueryGateway ticketQueryGateway) {
        this.ticketQueryGateway = ticketQueryGateway;
    }

    public GetTicketDetailsResponse execute(GetTicketDetailsInput input) {

        GetTicketDetailsResponse ticketDetails = this.ticketQueryGateway.findDetailById(input.ticketId())
                .orElseThrow(TicketNotFoundException::new);

        // if the user is an admin, there is no need to verify the ID
        if(input.role() == UserRole.ADMIN) {
            return ticketDetails;
        }

        // if the user is the technician responsible for the ticket, access is granted
        if (input.role() == UserRole.TECHNICIAN && ticketDetails.technician().id().equals(input.userId())) {
            return ticketDetails;
        }

        // if the user is the one who created the ticket, access is granted
        if (input.role() == UserRole.CUSTOMER && ticketDetails.customer().id().equals(input.userId())) {
            return ticketDetails;
        }

        // if no rule applies, for example, if a user attempts to access a ticket they did not create (as a customer), are not responsible for (as a technician), and do not have admin access, an exception is thrown
        throw new UnauthorizedTicketAccessException();
    }
}