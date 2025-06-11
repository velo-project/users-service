package com.github.veloproject.userservices.commands.admin.unblock_user.handler;

import com.github.veloproject.userservices.commands.admin.unblock_user.UnblockUserCommand;
import com.github.veloproject.userservices.commands.admin.unblock_user.UnblockUserCommandResult;
import com.github.veloproject.userservices.mediators.contracts.handlers.AuthRequestHandler;
import com.github.veloproject.userservices.persistence.repositories.UserRepository;
import com.github.veloproject.userservices.shared.exceptions.IncorrectInformationsProvided;
import com.github.veloproject.userservices.shared.exceptions.InvalidParameterException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class UnblockUserCommandHandler
        extends AuthRequestHandler<UnblockUserCommand, UnblockUserCommandResult> {
    private final UserRepository repository;

    public UnblockUserCommandHandler(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UnblockUserCommandResult handle(UnblockUserCommand request, JwtAuthenticationToken token) {
        if (request.getNickname() == null) throw new InvalidParameterException("Nickname must be specified.");

        var user = repository
                .getByNickname(request.getNickname())
                .orElseThrow(IncorrectInformationsProvided::new);
        user.setIsBlocked(false);
        repository.save(user);

        return new UnblockUserCommandResult(
                200,
                "User unblocked."
        );
    }
}
