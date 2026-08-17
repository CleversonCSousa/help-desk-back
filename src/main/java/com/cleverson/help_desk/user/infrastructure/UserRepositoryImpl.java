package com.cleverson.help_desk.user.infrastructure;

import com.cleverson.help_desk.user.domain.User;
import com.cleverson.help_desk.user.domain.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository repository;

    public UserRepositoryImpl(
            UserJpaRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {

        UserEntity entity = new UserEntity(
                user.id(),
                user.name(),
                user.email(),
                user.password(),
                null,
                user.role()

        );

        UserEntity saved = repository.save(entity);

        return new User(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getPassword(),
                saved.getAvatarUrl(),
                saved.getRole()
        );
    }

    @Override
    public Optional<User> findById(UUID id) {

        return repository.findById(id)
                .map(entity ->
                        new User(
                                entity.getId(),
                                entity.getName(),
                                entity.getEmail(),
                                entity.getPassword(),
                                entity.getAvatarUrl(),
                                entity.getRole()
                        )
                );
    }

    @Override
    public Optional<User> findByEmail(String email) {

        return repository.findByEmail(email)
                .map(entity ->
                        new User(
                                entity.getId(),
                                entity.getName(),
                                entity.getEmail(),
                                entity.getPassword(),
                                entity.getAvatarUrl(),
                                entity.getRole()
                        )
                );
    }
}