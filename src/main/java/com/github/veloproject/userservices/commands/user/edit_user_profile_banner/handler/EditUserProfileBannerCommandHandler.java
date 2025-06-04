package com.github.veloproject.userservices.commands.user.edit_user_profile_banner.handler;

import com.github.veloproject.userservices.commands.user.edit_user_profile_banner.EditUserProfileBannerCommand;
import com.github.veloproject.userservices.commands.user.edit_user_profile_banner.EditUserProfileBannerCommandResult;
import com.github.veloproject.userservices.mediators.contracts.handlers.AuthRequestHandler;
import com.github.veloproject.userservices.persistence.repositories.UserRepository;
import com.github.veloproject.userservices.shared.exceptions.InternalErrorException;
import com.github.veloproject.userservices.shared.exceptions.InvalidParameterException;
import com.github.veloproject.userservices.shared.file_managers.ImageService;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EditUserProfileBannerCommandHandler
        extends AuthRequestHandler<EditUserProfileBannerCommand, EditUserProfileBannerCommandResult> {
    private final UserRepository repository;
    private final ImageService imageService;

    public EditUserProfileBannerCommandHandler(UserRepository repository,
                                               ImageService imageService) {
        this.repository = repository;
        this.imageService = imageService;
    }

    // TODO Melhorias na legibilidade do código, alteração no tamanho da imagem.
    @Override
    public EditUserProfileBannerCommandResult handle(EditUserProfileBannerCommand request,
                                                     JwtAuthenticationToken token) {
        if (request.getFile() == null || request.getFile().isEmpty()) throw new InvalidParameterException("Image must be uploaded.");
        else if (token == null) throw new InvalidBearerTokenException("Bearer Token must be specified.");

        var fileName = request.getFile().getOriginalFilename();
        if (fileName == null) {
            throw new InvalidParameterException("File name must be specified.");
        }

        var user = repository.getReferenceById(Integer.valueOf(token.getName()));
        try {
            String path = imageService.uploadImage(
                    request.getFile(),
                    "banner.png",
                    user.getId());
            user.setBannerPhotoUrl(path);
        } catch (IOException e) {
            throw new InternalErrorException("Error while reading image.");
        }

        return new EditUserProfileBannerCommandResult(
                200,
                "Banner updated."
        );
    }
}
