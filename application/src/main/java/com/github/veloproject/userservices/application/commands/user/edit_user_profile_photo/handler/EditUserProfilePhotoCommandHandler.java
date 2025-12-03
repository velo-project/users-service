package com.github.veloproject.userservices.application.commands.user.edit_user_profile_photo.handler;

import com.github.veloproject.userservices.application.abstractions.repositories.IUserRepository;
import com.github.veloproject.userservices.application.abstractions.services.IImageFileService;
import com.github.veloproject.userservices.application.commands.user.edit_user_profile_photo.EditUserProfilePhotoCommand;
import com.github.veloproject.userservices.application.commands.user.edit_user_profile_photo.EditUserProfilePhotoCommandResult;
import com.github.veloproject.userservices.application.mediators.contracts.handlers.AuthRequestHandler;
import com.github.veloproject.userservices.domain.exceptions.InternalErrorException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.InvalidParameterException;

@Service
public class EditUserProfilePhotoCommandHandler
        extends AuthRequestHandler<EditUserProfilePhotoCommand, EditUserProfilePhotoCommandResult> {
    private final IUserRepository repository;
    private final IImageFileService imageService;

    public EditUserProfilePhotoCommandHandler(IUserRepository repository,
                                              IImageFileService imageService) {
        this.repository = repository;
        this.imageService = imageService;
    }
    @Override
    @Transactional
    public EditUserProfilePhotoCommandResult handle(EditUserProfilePhotoCommand request,
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
            user.setProfilePhotoUrl(path);
            repository.save(user);
        } catch (IOException e) {
            throw new InternalErrorException("Erro ao ler a imagem.");
        }

        return new EditUserProfilePhotoCommandResult(
                200,
                "Foto de perfil atualizada."
        );
    }
}
