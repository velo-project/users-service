package com.github.veloproject.userservices.commands.admin.block_user.handler;

import com.github.veloproject.userservices.commands.admin.block_user.BlockUserCommand;
import com.github.veloproject.userservices.commands.admin.block_user.BlockUserCommandResult;
import com.github.veloproject.userservices.mediators.contracts.handlers.AuthRequestHandler;
import com.github.veloproject.userservices.persistence.repositories.UserRepository;
import com.github.veloproject.userservices.shared.exceptions.IncorrectInformationsProvided;
import com.github.veloproject.userservices.shared.exceptions.InvalidParameterException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class BlockUserCommandHandler
        extends AuthRequestHandler<BlockUserCommand, BlockUserCommandResult> {
    private final UserRepository repository;

    public BlockUserCommandHandler(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public BlockUserCommandResult handle(BlockUserCommand request,
                                         JwtAuthenticationToken token) {
        if (request.getEmail() == null) throw new InvalidParameterException("Email must be specified.");

        var user = repository
                .getByEmail(request.getEmail())
                .orElseThrow(IncorrectInformationsProvided::new);
        user.setIsBlocked(true);
        repository.save(user);

        return new BlockUserCommandResult(
                200,
                "User blocked."
        );
    }
}
