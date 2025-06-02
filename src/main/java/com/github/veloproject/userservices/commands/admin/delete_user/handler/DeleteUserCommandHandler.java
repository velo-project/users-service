package com.github.veloproject.userservices.commands.admin.delete_user.handler;

import com.github.veloproject.userservices.commands.admin.delete_user.DeleteUserCommand;
import com.github.veloproject.userservices.commands.admin.delete_user.DeleteUserCommandResult;
import com.github.veloproject.userservices.mediators.contracts.handlers.AuthRequestHandler;
import com.github.veloproject.userservices.persistence.repositories.UserRepository;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserCommandHandler
        extends AuthRequestHandler<DeleteUserCommand, DeleteUserCommandResult> {
    private final UserRepository repository;

    public DeleteUserCommandHandler(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public DeleteUserCommandResult handle(DeleteUserCommand request, JwtAuthenticationToken token) {
        return null;
    }
}
