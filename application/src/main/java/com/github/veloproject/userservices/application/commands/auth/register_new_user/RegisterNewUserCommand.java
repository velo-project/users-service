package com.github.veloproject.userservices.application.commands.auth.register_new_user;

import com.github.veloproject.userservices.application.mediators.contracts.Request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterNewUserCommand implements Request<RegisterNewUserCommandResult> {
    @NotBlank
    @Size(max = 100)
    private String name;
    @NotBlank
    @Size(max = 20)
    private String nickname;
    @NotBlank
    @Email
    @Size(max = 60)
    private String email;
    @NotBlank
    @Size(max = 25)
    private String password;
}
