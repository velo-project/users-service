package com.github.veloproject.userservices.application.commands.auth.login_2fa;

import com.github.veloproject.userservices.application.mediators.contracts.Request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUser2FACommand implements Request<LoginUser2FACommandResult> {
    @NotBlank @Size(max = 550)
    private String key;
    @NotBlank @Size(max = 6)
    private String code;
}
