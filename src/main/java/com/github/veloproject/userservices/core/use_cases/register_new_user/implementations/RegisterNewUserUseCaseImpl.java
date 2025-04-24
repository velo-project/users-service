package com.github.veloproject.userservices.core.use_cases.register_new_user.implementations;

import com.github.veloproject.userservices.core.use_cases.register_new_user.RegisterNewUserUseCase;
import com.github.veloproject.userservices.core.use_cases.register_new_user.implementations.ports.NewUserPersistencePort;
import com.github.veloproject.userservices.core.use_cases.register_new_user.io.inputs.RegisterNewUserUseCaseInput;
import com.github.veloproject.userservices.core.use_cases.register_new_user.io.outputs.RegisterNewUserUseCaseOutput;
import org.springframework.stereotype.Service;

@Service
public class RegisterNewUserUseCaseImpl extends RegisterNewUserUseCase {
    private final NewUserPersistencePort persistencePort;

    public RegisterNewUserUseCaseImpl(NewUserPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    protected RegisterNewUserUseCaseOutput applyInternalLogic(RegisterNewUserUseCaseInput input) {
        return null;
    }
}
