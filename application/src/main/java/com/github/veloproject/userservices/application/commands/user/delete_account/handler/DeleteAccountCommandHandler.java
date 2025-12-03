package com.github.veloproject.userservices.application.commands.user.delete_account.handler;

import com.github.veloproject.userservices.application.abstractions.repositories.IUserRepository;
import com.github.veloproject.userservices.application.commands.user.delete_account.DeleteAccountCommand;
import com.github.veloproject.userservices.application.commands.user.delete_account.DeleteAccountCommandResult;
import com.github.veloproject.userservices.application.mediators.contracts.handlers.AuthRequestHandler;
import com.github.veloproject.userservices.domain.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class DeleteAccountCommandHandler extends AuthRequestHandler<DeleteAccountCommand, DeleteAccountCommandResult> {
    private final IUserRepository userRepository;

    public DeleteAccountCommandHandler(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public DeleteAccountCommandResult handle(DeleteAccountCommand request, JwtAuthenticationToken token) {
        var userId = Integer.valueOf(token.getToken().getSubject());

        userRepository.findById(userId).ifPresentOrElse(
                userEntity -> {
                    userEntity.delete();
                    userRepository.save(userEntity);
                },
                () -> { throw new NotFoundException("Usuário"); }
        );

        return new DeleteAccountCommandResult(
                200,
                "Usuário deletado."
        );
    }
}
