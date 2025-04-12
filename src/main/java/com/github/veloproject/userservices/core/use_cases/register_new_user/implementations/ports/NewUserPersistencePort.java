package com.github.veloproject.userservices.core.use_cases.register_new_user.implementations.ports;

import com.github.veloproject.userservices.core.use_cases.register_new_user.io.inputs.RegisterNewUserUseCaseInput;

import java.util.Optional;

public interface NewUserPersistencePort {
    Optional<NewUserPersistancePortOutput> execute(RegisterNewUserUseCaseInput input);

    class NewUserPersistancePortOutput {

    }

    class NewUserPersistancePortInput {

    }
}
