package com.github.veloproject.userservices.application.commands.admin.unblock_user.handler;

import com.github.veloproject.userservices.application.abstractions.repositories.IUserRepository;
import com.github.veloproject.userservices.application.commands.admin.unblock_user.UnblockUserCommand;
import com.github.veloproject.userservices.application.commands.admin.unblock_user.UnblockUserCommandResult;
import com.github.veloproject.userservices.application.mediators.contracts.handlers.AuthRequestHandler;
import com.github.veloproject.userservices.domain.exceptions.IncorrectInformationsProvidedException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;

@Service
public class UnblockUserCommandHandler
        extends AuthRequestHandler<UnblockUserCommand, UnblockUserCommandResult> {
    private final IUserRepository repository;

    public UnblockUserCommandHandler(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public UnblockUserCommandResult handle(UnblockUserCommand request, JwtAuthenticationToken token) {
        var user = repository
                .findByNickname(request.getNickname())
                .orElseThrow(IncorrectInformationsProvidedException::new);
        user.unblock();
        repository.save(user);

        return new UnblockUserCommandResult(
                200,
                "User unblocked."
        );
    }
}
