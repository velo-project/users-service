package com.github.veloproject.userservices.application.commands.user.edit_user_profile_banner;

import com.github.veloproject.userservices.application.mediators.contracts.Request;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class EditUserProfileBannerCommand implements Request<EditUserProfileBannerCommandResult> {
    private MultipartFile file;

    public EditUserProfileBannerCommand(MultipartFile file) {
        this.file = file;
    }
}
