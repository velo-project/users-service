package com.github.veloproject.userservices.adapters.use_cases.register_new_user;

import com.github.veloproject.userservices.adapters.dao.repositories.UserRepository;
import com.github.veloproject.userservices.adapters.dao.tables.UserTable;
import com.github.veloproject.userservices.adapters.dao.tables.mappers.UserTableMapper;
import com.github.veloproject.userservices.core.entities.UserEntity;
import com.github.veloproject.userservices.core.use_cases.register_new_user.implementations.ports.NewUserPersistencePort;
import com.github.veloproject.userservices.core.use_cases.register_new_user.io.inputs.RegisterNewUserUseCaseInput;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NewUserPersistencePortAdapter implements NewUserPersistencePort {
    private final UserRepository repository;

    public NewUserPersistencePortAdapter(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<NewUserPersistancePortOutput> execute(RegisterNewUserUseCaseInput input) {
        Optional<UserTable> existingUser = repository.findByEmail(input.getEmail());

        if (existingUser.isPresent()) {
            return Optional.empty();
        }

        var userEntity = createUserEntityFromInput(input);
        var userTable = UserTableMapper.toTable(userEntity);
        var savedUser = repository.save(userTable);

        return Optional.of(NewUserPersistancePortOutput.builder()
                .userId(savedUser.getUserId())
                .build());
    }

    private UserEntity createUserEntityFromInput(RegisterNewUserUseCaseInput input) {
        return UserEntity.builder()
                .fullName(input.getFullName())
                .preferredName(input.getPrefferedName())
                .email(input.getEmail())
                .password(input.getPassword())
                .build();
    }
}
