package com.github.veloproject.userservices.commands.user.edit_user_profile_photo;

import com.github.veloproject.userservices.mediators.contracts.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserProfilePhotoCommandResult extends Response {
    public EditUserProfilePhotoCommandResult(Integer statusCode,
                                             String message) {
        super(statusCode, message);
    }
}
