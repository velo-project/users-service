package com.github.veloproject.userservices.commands.admin.delete_user.handler;

import com.github.veloproject.userservices.commands.admin.delete_user.DeleteUserCommand;
import com.github.veloproject.userservices.commands.admin.delete_user.DeleteUserCommandResult;
import com.github.veloproject.userservices.mediators.contracts.handlers.AuthRequestHandler;
import com.github.veloproject.userservices.persistence.repositories.UserRepository;
import com.github.veloproject.userservices.shared.exceptions.IncorrectInformationsProvided;
import com.github.veloproject.userservices.shared.exceptions.InvalidParameterException;
import com.github.veloproject.userservices.shared.utils.UserUtils;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteUserCommandHandler
        extends AuthRequestHandler<DeleteUserCommand, DeleteUserCommandResult> {
    private final UserRepository repository;

    public DeleteUserCommandHandler(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public DeleteUserCommandResult handle(DeleteUserCommand request,
                                          JwtAuthenticationToken token) {
        if (request.getNickname() == null) throw new InvalidParameterException("Nickname must be specified.");

        var user = repository
                .getByNickname(request.getNickname())
                .orElseThrow(IncorrectInformationsProvided::new);
        user.setNickname(UserUtils.generateDeletedUserNickname());
        user.setIsDeleted(true);
        repository.save(user);

        return new DeleteUserCommandResult(
                200,
                "User deleted."
        );
    }
}
