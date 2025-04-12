package com.github.veloproject.userservices.core.use_cases.register_new_user.io.outputs;

import com.github.veloproject.userservices.common.UseCaseOutputBase;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PUBLIC)
public class RegisterNewUserUseCaseOutput extends UseCaseOutputBase {
    public RegisterNewUserUseCaseOutput(int httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
