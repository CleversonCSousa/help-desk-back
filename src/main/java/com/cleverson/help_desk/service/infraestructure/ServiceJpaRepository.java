package com.cleverson.help_desk.service.infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ServiceJpaRepository extends JpaRepository<ServiceEntity, UUID> {
    Optional<ServiceEntity> findById(UUID id);
    Optional<ServiceEntity> findByTitle(String title);
}
