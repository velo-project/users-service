package com.github.veloproject.userservices.commands.register_new_user.handler;

import com.github.veloproject.userservices.commands.register_new_user.RegisterNewUserCommand;
import com.github.veloproject.userservices.commands.register_new_user.RegisterNewUserCommandResult;
import com.github.veloproject.userservices.mediators.contracts.RequestHandler;
import com.github.veloproject.userservices.persistence.entities.UserEntity;
import com.github.veloproject.userservices.persistence.repositories.UserRepository;
import com.github.veloproject.userservices.shared.exceptions.AlreadyExistsException;
import com.github.veloproject.userservices.shared.exceptions.InvalidParameterException;
import com.github.veloproject.userservices.shared.utils.CryptographyUtils;
import org.springframework.stereotype.Service;

@Service
public class RegisterNewUserCommandHandler implements RequestHandler<RegisterNewUserCommand, RegisterNewUserCommandResult> {
    private final UserRepository repository;

    public RegisterNewUserCommandHandler(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public RegisterNewUserCommandResult handle(RegisterNewUserCommand command) {
        var emailIsValid = !repository.existsByEmail(command.getEmail());
        if (!emailIsValid) {
            throw new AlreadyExistsException("email");
        }

        var nameIsValid = (command.getName() != null
                && command.getName().trim().split("\\s+").length >= 2);
        var passwordIsValid = (command.getPassword() != null
                && command.getPassword().length() >= 8
                && command.getPassword().length() <= 20);
        if (!nameIsValid || !passwordIsValid) {
            throw new InvalidParameterException("password");
        }

        var hashedPassword = CryptographyUtils
                .encrypt(command.getPassword());

        UserEntity userEntity = new UserEntity(
                command.getName(),
                command.getEmail(),
                hashedPassword
        );
        userEntity.setIsBlocked(false);

        var userSaved = repository.save(userEntity);

        return new RegisterNewUserCommandResult(
                200,
                "Successfully registered.",
                userSaved.getId());
    }
}
