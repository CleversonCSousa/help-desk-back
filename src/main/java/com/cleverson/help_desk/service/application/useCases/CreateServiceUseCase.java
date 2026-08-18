package com.cleverson.help_desk.service.application.useCases;

import com.cleverson.help_desk.service.application.dto.CreateServiceInput;
import com.cleverson.help_desk.service.application.exceptions.ServiceAlreadyExistsException;
import com.cleverson.help_desk.service.domain.Service;
import com.cleverson.help_desk.service.domain.ServiceRepository;

@org.springframework.stereotype.Service
public class CreateServiceUseCase {
    private final ServiceRepository serviceRepository;

    public CreateServiceUseCase(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public Service execute(CreateServiceInput input) {
        // Checking if a service with the same title exists, if so, a runtime exception (ServiceAlreadyExistsException) is thrown.
        if (this.serviceRepository.findByTitle(input.title()).isPresent()) {
            throw new ServiceAlreadyExistsException();
        }

        Service service = new Service(
                null,
                input.title(),
                input.description(),
                input.price(),
                true
        );

        return serviceRepository.save(service);
    }
}
