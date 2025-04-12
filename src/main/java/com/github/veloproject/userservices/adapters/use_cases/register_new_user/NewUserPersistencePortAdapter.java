package com.github.veloproject.userservices.adapters.use_cases.register_new_user;

import com.github.veloproject.userservices.core.use_cases.register_new_user.implementations.ports.NewUserPersistencePort;
import com.github.veloproject.userservices.core.use_cases.register_new_user.io.inputs.RegisterNewUserUseCaseInput;

import java.util.Optional;

public class NewUserPersistencePortAdapter implements NewUserPersistencePort {
    @Override
    public Optional<NewUserPersistancePortOutput> execute(RegisterNewUserUseCaseInput input) {
        return Optional.empty();
    }
}
