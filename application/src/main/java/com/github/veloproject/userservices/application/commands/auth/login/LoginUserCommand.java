package com.github.veloproject.userservices.application.commands.auth.login;

import com.github.veloproject.userservices.application.mediators.contracts.Request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserCommand implements Request<LoginUserCommandResult> {
    @NotBlank
    @Email @Size(max = 60)
    private String email;
    @NotBlank @Size(max = 25)
    private String password;
}
