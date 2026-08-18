package com.cleverson.help_desk.service.infraestructure;

import com.cleverson.help_desk.service.domain.Service;
import com.cleverson.help_desk.service.domain.ServiceRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ServiceRepositoryImpl implements ServiceRepository {
    private final ServiceJpaRepository repository;

    public ServiceRepositoryImpl(
            ServiceJpaRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public Service save(Service service) {
        ServiceEntity entity = new ServiceEntity(
                service.id(),
                service.title(),
                service.description(),
                service.price(),
                service.isActive()
        );

        ServiceEntity saved = repository.save(entity);

        return new Service(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getPrice(),
                saved.getIsActive()
        );
    }

    @Override
    public Optional<Service> findById(UUID id) {

        return repository.findById(id)
                .map(entity ->
                        new Service(
                                entity.getId(),
                                entity.getTitle(),
                                entity.getDescription(),
                                entity.getPrice(),
                                entity.getIsActive()
                        )
                );
    }

    @Override
    public Optional<Service> findByTitle(String title) {

        return repository.findByTitle(title)
                .map(entity ->
                        new Service(
                                entity.getId(),
                                entity.getTitle(),
                                entity.getDescription(),
                                entity.getPrice(),
                                entity.getIsActive()
                        )
                );
    }
}
