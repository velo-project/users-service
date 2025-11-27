package com.github.veloproject.userservices.application.commands.user.edit_user_profile;

import com.github.veloproject.userservices.application.commands.user.edit_user_profile.EditUserProfileCommandResult;
import com.github.veloproject.userservices.application.mediators.contracts.Request;
import com.github.veloproject.userservices.domain.enums.UserProfileUpdatableField;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserProfileCommand implements Request<EditUserProfileCommandResult> {
    @NotNull
    private UserProfileUpdatableField field;
    @NotBlank
    @Size(min = 1, max = 100)
    private String fieldValue;
}
