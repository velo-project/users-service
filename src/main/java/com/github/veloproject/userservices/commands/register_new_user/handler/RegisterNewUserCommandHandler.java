package com.github.veloproject.userservices.commands.register_new_user.handler;

import com.github.veloproject.userservices.commands.register_new_user.RegisterNewUserCommand;
import com.github.veloproject.userservices.commands.register_new_user.RegisterNewUserCommandResult;
import com.github.veloproject.userservices.mediators.contracts.RequestHandler;
import com.github.veloproject.userservices.persistence.entities.UserEntity;
import com.github.veloproject.userservices.persistence.repositories.UserRepository;
import com.github.veloproject.userservices.shared.exceptions.AlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public class RegisterNewUserCommandHandler implements RequestHandler<RegisterNewUserCommand, RegisterNewUserCommandResult> {
    private final UserRepository repository;

    public RegisterNewUserCommandHandler(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public RegisterNewUserCommandResult handle(RegisterNewUserCommand command) {
        if (repository.existsByEmail(command.getEmail())) {
            throw new AlreadyExistsException("email");
        }

        UserEntity userEntity = new UserEntity(
                command.getName(),
                command.getEmail(),
                command.getPassword()
        );
        userEntity.setIsBlocked(false);

        var user = repository.save(userEntity);

        return new RegisterNewUserCommandResult(
                200,
                "Successfully registered.",
                user.getId());
    }
}
