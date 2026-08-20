package com.cleverson.help_desk.service.infraestructure;

import com.cleverson.help_desk.service.domain.Service;
import com.cleverson.help_desk.service.domain.ServiceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
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
                service.isActive(),
                null
        );

        ServiceEntity saved = repository.save(entity);

        return new Service(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getPrice(),
                saved.getIsActive(),
                saved.getCreatedAt()
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
                                entity.getIsActive(),
                                entity.getCreatedAt()
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
                                entity.getIsActive(),
                                entity.getCreatedAt()
                        )
                );
    }

    @Override
    public List<Service> findAll() {
        return repository.findAll().stream().map(entity -> new Service(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getIsActive(),
                entity.getCreatedAt()
        )).toList();
    }

    @Override
    public List<Service> findAllByIsActive(boolean isActive) {
        return repository.findAllByIsActive(isActive).stream().map(entity -> new Service(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getIsActive(),
                entity.getCreatedAt()
        )).toList();
    }

    @Override
    public Service update(Service service) {
        ServiceEntity entity = new ServiceEntity(
                service.id(),
                service.title(),
                service.description(),
                service.price(),
                service.isActive(),
                service.createdAt()
        );

        ServiceEntity updated = repository.save(entity);

        return new Service(
                updated.getId(),
                updated.getTitle(),
                updated.getDescription(),
                updated.getPrice(),
                updated.getIsActive(),
                updated.getCreatedAt()
        );
    }

    @Override
    public List<Service> findAllByOrderByCreatedAtDesc() {
        return repository.findAllByOrderByCreatedAtDesc().stream().map(entity -> new Service(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getIsActive(),
                entity.getCreatedAt()
        )).toList();
    }


}
