package com.cleverson.help_desk.service.application.useCases;

import com.cleverson.help_desk.service.application.dto.ToggleServiceIsActiveInput;
import com.cleverson.help_desk.service.application.exceptions.ServiceNotFoundException;
import com.cleverson.help_desk.service.domain.Service;
import com.cleverson.help_desk.service.domain.ServiceRepository;

@org.springframework.stereotype.Service
public class ToggleServiceIsActiveUseCase {
    private final ServiceRepository serviceRepository;

    public ToggleServiceIsActiveUseCase(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public void execute(ToggleServiceIsActiveInput input) {
        Service currentService = serviceRepository.findById(input.id())
                .orElseThrow(() -> new ServiceNotFoundException());

        Service updatedService = new Service(
                currentService.id(),
                currentService.title(),
                currentService.description(),
                currentService.price(),
                !currentService.isActive()
        );

        serviceRepository.update(updatedService);
    }
}
