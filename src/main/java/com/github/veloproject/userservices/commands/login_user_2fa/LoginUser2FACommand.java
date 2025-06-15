package com.github.veloproject.userservices.commands.login_user_2fa;

import com.github.veloproject.userservices.mediators.contracts.Request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUser2FACommand implements Request<LoginUser2FACommandResult> {
    private String key;
    private String code;
}
