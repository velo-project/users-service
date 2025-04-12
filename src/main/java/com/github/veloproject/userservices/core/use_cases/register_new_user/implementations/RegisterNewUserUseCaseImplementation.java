package com.github.veloproject.userservices.core.use_cases.register_new_user.implementations;

import com.github.veloproject.userservices.adapters.dao.repositories.UserRepository;
import com.github.veloproject.userservices.core.use_cases.register_new_user.RegisterNewUserUseCase;
import com.github.veloproject.userservices.core.use_cases.register_new_user.implementations.ports.NewUserPersistencePort;
import com.github.veloproject.userservices.core.use_cases.register_new_user.io.inputs.RegisterNewUserUseCaseInput;
import com.github.veloproject.userservices.core.use_cases.register_new_user.io.outputs.RegisterNewUserUseCaseOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterNewUserUseCaseImplementation extends RegisterNewUserUseCase {
    // private final NewUserPersistencePort newUserPersistencePort;
    private final UserRepository userRepository;

    @Override
    protected RegisterNewUserUseCaseOutput applyInternalLogic(RegisterNewUserUseCaseInput input) {
        return null;
    }
}
