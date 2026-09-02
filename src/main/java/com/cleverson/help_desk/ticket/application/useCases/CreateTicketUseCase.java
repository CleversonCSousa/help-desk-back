package com.cleverson.help_desk.ticket.application.useCases;

import com.cleverson.help_desk.service.application.exceptions.ServiceNotFoundException;
import com.cleverson.help_desk.service.domain.ServiceRepository;
import com.cleverson.help_desk.technician.application.exceptions.NoTechnicianAvailableException;
import com.cleverson.help_desk.technician.domain.TechnicianRepository;
import com.cleverson.help_desk.ticket.application.dto.CreateTicketInput;
import com.cleverson.help_desk.ticket.domain.Ticket;
import com.cleverson.help_desk.ticket.domain.TicketRepository;
import com.cleverson.help_desk.ticket.domain.TicketStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class CreateTicketUseCase {
    private final TicketRepository ticketRepository;
    private final TechnicianRepository technicianRepository;
    private final ServiceRepository serviceRepository;

    public CreateTicketUseCase(TicketRepository ticketRepository, TechnicianRepository technicianRepository, ServiceRepository serviceRepository) {
        this.ticketRepository = ticketRepository;
        this.technicianRepository = technicianRepository;
        this.serviceRepository = serviceRepository;
    }
    public Ticket execute(CreateTicketInput input) {
        var service = this.serviceRepository.findById(input.serviceId())
                .orElseThrow(() -> new ServiceNotFoundException());

        var currentTime = LocalDateTime.now().toLocalTime();
        var availableTechnicians = this.technicianRepository.findAvailableTechnicianWithLeastLoad(
            currentTime
        );

        if (availableTechnicians.isEmpty()) {
            throw new NoTechnicianAvailableException();
        }

        var technician = availableTechnicians.getFirst();

        var ticket = new Ticket(
                null,
                null,
                input.title(),
                input.description(),
                service.price(),
                TicketStatus.OPEN,
                input.customerId(),
                input.serviceId(),
                technician.getId(),
                null,
                null
        );

        return this.ticketRepository.save(ticket);
    }
}
