package com.github.veloproject.userservices.commands.user.edit_user_profile_banner;

import com.github.veloproject.userservices.mediators.contracts.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserProfileBannerCommandResult extends Response {

    public EditUserProfileBannerCommandResult(Integer statusCode,
                                              String message) {
        super(statusCode, message);
    }
}
