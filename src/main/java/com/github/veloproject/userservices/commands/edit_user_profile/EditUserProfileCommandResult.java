package com.github.veloproject.userservices.commands.edit_user_profile;

import com.github.veloproject.userservices.mediators.contracts.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserProfileCommandResult extends Response {
    public EditUserProfileCommandResult(Integer status, String message) {
        super(status, message);
    }
}
