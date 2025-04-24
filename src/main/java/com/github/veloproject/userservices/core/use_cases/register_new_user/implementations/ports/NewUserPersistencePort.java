package com.github.veloproject.userservices.core.use_cases.register_new_user.implementations.ports;

import com.github.veloproject.userservices.core.use_cases.register_new_user.io.inputs.RegisterNewUserUseCaseInput;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

public interface NewUserPersistencePort {
    Optional<NewUserPersistancePortOutput> execute(RegisterNewUserUseCaseInput input);

    @Getter
    @Builder
    class NewUserPersistancePortInput {
        private String fullName;
        private String preferedName;
        private String email;
        private String password;
    }

    @Getter
    @Builder
    class NewUserPersistancePortOutput {
        private Long userId;
    }

}
