package com.github.veloproject.userservices.commands.user.edit_user_profile_photo;

import com.github.veloproject.userservices.mediators.contracts.Request;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class EditUserProfilePhotoCommand implements Request<EditUserProfilePhotoCommandResult> {
    private MultipartFile file;

    public EditUserProfilePhotoCommand(MultipartFile file) {
        this.file = file;
    }
}
