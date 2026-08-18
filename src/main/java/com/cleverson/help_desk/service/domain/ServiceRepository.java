package com.cleverson.help_desk.service.domain;

import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository {
    Service save(Service service);
    Optional<Service> findByTitle(String title);
    Optional<Service> findById(UUID id);
}
