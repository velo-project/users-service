package com.github.veloproject.userservices.application.commands.user.edit_user_profile_banner.handler;

import com.github.veloproject.userservices.application.abstractions.repositories.IUserRepository;
import com.github.veloproject.userservices.application.abstractions.services.IImageFileService;
import com.github.veloproject.userservices.application.commands.user.edit_user_profile_banner.EditUserProfileBannerCommand;
import com.github.veloproject.userservices.application.commands.user.edit_user_profile_banner.EditUserProfileBannerCommandResult;
import com.github.veloproject.userservices.application.mediators.contracts.handlers.AuthRequestHandler;
import com.github.veloproject.userservices.domain.exceptions.InternalErrorException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.InvalidParameterException;

@Service
public class EditUserProfileBannerCommandHandler
        extends AuthRequestHandler<EditUserProfileBannerCommand, EditUserProfileBannerCommandResult> {
    private final IUserRepository repository;
    private final IImageFileService imageService;

    public EditUserProfileBannerCommandHandler(IUserRepository repository,
                                               IImageFileService imageService) {
        this.repository = repository;
        this.imageService = imageService;
    }

    @Override
    @Transactional
    public EditUserProfileBannerCommandResult handle(EditUserProfileBannerCommand request,
                                                     JwtAuthenticationToken token) {
        if (request.getFile().isEmpty()) throw new InvalidParameterException("Imagem precisa ser postada.");

        var fileName = request.getFile()
                .getOriginalFilename();
        if (fileName == null) {
            throw new InvalidParameterException("O nome do arquivo deve ser especificado.");
        }

        var user = repository.getReferenceById(Integer.valueOf(token.getName()));
        try {
            String path = imageService.uploadImage(
                    request.getFile(),
                    user.getId());

            user.setBannerPhotoUrl(path);
            repository.save(user);
        } catch (IOException e) {
            throw new InternalErrorException("Erro ao ler imagem.");
        }

        return new EditUserProfileBannerCommandResult(
                200,
                "Banner atualizado."
        );
    }
}
