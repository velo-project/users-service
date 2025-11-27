package com.github.veloproject.userservices.application.commands.admin.delete_user.handler;

import com.github.veloproject.userservices.application.abstractions.repositories.IUserRepository;
import com.github.veloproject.userservices.application.commands.admin.delete_user.DeleteUserCommand;
import com.github.veloproject.userservices.application.commands.admin.delete_user.DeleteUserCommandResult;
import com.github.veloproject.userservices.application.mediators.contracts.handlers.AuthRequestHandler;
import com.github.veloproject.userservices.domain.exceptions.IncorrectInformationsProvidedException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;

@Service
public class DeleteUserCommandHandler
        extends AuthRequestHandler<DeleteUserCommand, DeleteUserCommandResult> {
    private final IUserRepository repository;

    public DeleteUserCommandHandler(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public DeleteUserCommandResult handle(DeleteUserCommand request,
                                          JwtAuthenticationToken token) {
        var user = repository
                .findByNickname(request.getNickname())
                .orElseThrow(IncorrectInformationsProvidedException::new);
        user.delete();
        repository.save(user);

        return new DeleteUserCommandResult(
                200,
                "User deleted."
        );
    }
}
