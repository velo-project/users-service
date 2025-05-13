package com.github.veloproject.userservices.commands.edit_user_profile;

import com.github.veloproject.userservices.mediators.contracts.Request;
import com.github.veloproject.userservices.shared.enums.UserProfileUpdatableField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserProfileCommand implements Request<EditUserProfileCommandResult> {
    private UserProfileUpdatableField field;
    private String fieldValue;
}
