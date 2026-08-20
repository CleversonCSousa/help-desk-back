package com.cleverson.help_desk.service.application.useCases;

import com.cleverson.help_desk.service.application.dto.ListServicesInput;
import com.cleverson.help_desk.service.domain.Service;
import com.cleverson.help_desk.service.domain.ServiceRepository;

import java.util.List;

@org.springframework.stereotype.Service
public class ListServicesUseCase {
    private final ServiceRepository serviceRepository;

    public ListServicesUseCase(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<Service> execute(ListServicesInput input) {

        // returns all services (inactive and active)
        if(input.isActive() == null) {
            return this.serviceRepository.findAllByOrderByCreatedAtDesc();
        }

        return this.serviceRepository.findAllByIsActive(input.isActive());
    }

}
