package com.github.veloproject.userservices.core.use_cases.register_new_user;

import com.github.veloproject.userservices.core.use_cases.register_new_user.io.inputs.RegisterNewUserUseCaseInput;
import com.github.veloproject.userservices.core.use_cases.register_new_user.io.outputs.RegisterNewUserUseCaseOutput;

public abstract class RegisterNewUserUseCase {

    public RegisterNewUserUseCaseOutput execute(RegisterNewUserUseCaseInput input) {
        return applyInternalLogic(input);
    }

    protected abstract RegisterNewUserUseCaseOutput applyInternalLogic(RegisterNewUserUseCaseInput input);
}
