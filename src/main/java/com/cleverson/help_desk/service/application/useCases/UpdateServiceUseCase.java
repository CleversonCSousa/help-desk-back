package com.cleverson.help_desk.service.application.useCases;

import com.cleverson.help_desk.service.application.dto.UpdateServiceInput;
import com.cleverson.help_desk.service.application.exceptions.ServiceAlreadyExistsException;
import com.cleverson.help_desk.service.application.exceptions.ServiceNotFoundException;
import com.cleverson.help_desk.service.domain.Service;
import com.cleverson.help_desk.service.domain.ServiceRepository;

@org.springframework.stereotype.Service
public class UpdateServiceUseCase {
    private final ServiceRepository serviceRepository;

    public UpdateServiceUseCase(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public void execute(UpdateServiceInput input) {
        // checks if service exists
        Service currentService = serviceRepository.findById(input.id())
                .orElseThrow(() -> new ServiceNotFoundException());

        // checks if the id and the title are the same
        serviceRepository.findByTitle(input.title()).ifPresent(existingService -> {
            if (!existingService.id().equals(input.id())) {
                throw new ServiceAlreadyExistsException();
            }
        });

        Service updatedService = new Service(
                currentService.id(),
                input.title(),
                input.description(),
                input.price(),
                currentService.isActive(),
                currentService.createdAt()
        );

        serviceRepository.update(updatedService);
    }
}
