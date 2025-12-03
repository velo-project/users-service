package com.github.veloproject.userservices.application.commands.admin.block_user.handler;

import com.github.veloproject.userservices.application.abstractions.repositories.IUserRepository;
import com.github.veloproject.userservices.application.commands.admin.block_user.BlockUserCommand;
import com.github.veloproject.userservices.application.commands.admin.block_user.BlockUserCommandResult;
import com.github.veloproject.userservices.application.mediators.contracts.handlers.AuthRequestHandler;
import com.github.veloproject.userservices.domain.exceptions.IncorrectInformationsProvidedException;
import jakarta.transaction.Transactional;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

@Service
@Transactional
public class BlockUserCommandHandler
        extends AuthRequestHandler<BlockUserCommand, BlockUserCommandResult> {
    private final IUserRepository repository;

    public BlockUserCommandHandler(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public BlockUserCommandResult handle(BlockUserCommand request,
                                         JwtAuthenticationToken token) {
        var user = repository
                .findByNickname(request.getNickname())
                .orElseThrow(IncorrectInformationsProvidedException::new);
        user.block();
        repository.save(user);

        return new BlockUserCommandResult(
                200,
                "Usuário bloqueado."
        );
    }
}
