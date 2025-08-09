package com.github.veloproject.application.commands.auth.login_2fa;

import com.github.veloproject.application.mediators.contracts.Request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUser2FACommand implements Request<LoginUser2FACommandResult> {
    private String key;
    private String code;
}
