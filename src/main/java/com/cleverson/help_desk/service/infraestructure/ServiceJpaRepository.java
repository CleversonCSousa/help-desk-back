package com.cleverson.help_desk.service.infraestructure;

import com.cleverson.help_desk.service.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceJpaRepository extends JpaRepository<ServiceEntity, UUID> {
    Optional<ServiceEntity> findById(UUID id);
    Optional<ServiceEntity> findByTitle(String title);
    List<ServiceEntity> findAllByIsActive(boolean isActive);
}
