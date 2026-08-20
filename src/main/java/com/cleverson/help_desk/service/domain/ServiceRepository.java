package com.cleverson.help_desk.service.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository {
    Service save(Service service);
    Optional<Service> findByTitle(String title);
    Optional<Service> findById(UUID id);
    List<Service> findAll();
    List<Service> findAllByIsActive(boolean isActive);

}
