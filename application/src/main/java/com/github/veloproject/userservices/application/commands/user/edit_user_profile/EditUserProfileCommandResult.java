package com.github.veloproject.userservices.application.commands.user.edit_user_profile;

import com.github.veloproject.userservices.application.mediators.contracts.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserProfileCommandResult extends Response {
    public EditUserProfileCommandResult(Integer status, String message) {
        super(status, message);
    }
}
