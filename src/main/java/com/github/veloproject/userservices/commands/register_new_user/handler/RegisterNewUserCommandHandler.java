package com.github.veloproject.userservices.commands.register_new_user.handler;

import com.github.veloproject.userservices.commands.register_new_user.RegisterNewUserCommand;
import com.github.veloproject.userservices.commands.register_new_user.RegisterNewUserCommandResult;
import com.github.veloproject.userservices.mediators.contracts.RequestHandler;
import com.github.veloproject.userservices.persistence.entities.UserEntity;
import com.github.veloproject.userservices.persistence.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisterNewUserCommandHandler implements RequestHandler<RegisterNewUserCommand, RegisterNewUserCommandResult> {
    private final UserRepository repository;

    public RegisterNewUserCommandHandler(UserRepository repository) {
        this.repository = repository;
    }

    public RegisterNewUserCommandResult handle(RegisterNewUserCommand command) {
        UserEntity userEntity = new UserEntity(
                command.getName(),
                command.getEmail(),
                command.getPassword()
        );

        var user = repository.save(userEntity);
        return RegisterNewUserCommandResult.builder().createdUserId(user.getId()).build();
    }
}
