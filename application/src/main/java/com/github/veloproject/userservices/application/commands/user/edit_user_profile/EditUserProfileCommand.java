package com.github.veloproject.userservices.application.commands.user.edit_user_profile;

import com.github.veloproject.userservices.application.commands.user.edit_user_profile.EditUserProfileCommandResult;
import com.github.veloproject.userservices.application.mediators.contracts.Request;
import com.github.veloproject.userservices.domain.enums.UserProfileUpdatableField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserProfileCommand implements Request<EditUserProfileCommandResult> {
    private UserProfileUpdatableField field;
    private String fieldValue;
}
