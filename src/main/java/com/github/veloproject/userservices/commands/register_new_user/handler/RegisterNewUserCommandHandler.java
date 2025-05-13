package com.github.veloproject.userservices.commands.register_new_user.handler;

import com.github.veloproject.userservices.commands.register_new_user.RegisterNewUserCommand;
import com.github.veloproject.userservices.commands.register_new_user.RegisterNewUserCommandResult;
import com.github.veloproject.userservices.mediators.contracts.handlers.NoAuthRequestHandler;
import com.github.veloproject.userservices.persistence.entities.UserEntity;
import com.github.veloproject.userservices.persistence.repositories.UserRepository;
import com.github.veloproject.userservices.shared.exceptions.AlreadyExistsException;
import com.github.veloproject.userservices.shared.exceptions.InvalidParameterException;
import com.github.veloproject.userservices.shared.utils.CryptographyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterNewUserCommandHandler extends NoAuthRequestHandler<RegisterNewUserCommand, RegisterNewUserCommandResult> {
    private final UserRepository repository;

    public RegisterNewUserCommandHandler(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public RegisterNewUserCommandResult handle(RegisterNewUserCommand command) {
        validateEmail(command.getEmail());
        validateName(command.getName());
        validatePassword(command.getPassword());

        var hashedPassword = CryptographyUtils
                .encrypt(command.getPassword());

        UserEntity userEntity = new UserEntity(
                command.getName(),
                command.getEmail(),
                hashedPassword
        );
        userEntity.setIsBlocked(false);

        var savedUser = repository.save(userEntity);

        return new RegisterNewUserCommandResult(
                200,
                "Successfully registered.",
                savedUser.getId());
    }

    private void validateEmail(String email) throws AlreadyExistsException, InvalidParameterException {
        if (repository.existsByEmail(email)) throw new AlreadyExistsException("email");
        else if (email.contains(" ")
                || !email.contains("@")
                || email.length() < 6
                || email.length() > 60)
            throw new InvalidParameterException("Email address must be valid.");
    }

    private void validateName(String name) throws InvalidParameterException {
        if (name == null)
            throw new InvalidParameterException("Name must be provided.");
        int nameWordsLength = name.trim().split("\\s+").length;

        if (nameWordsLength < 2 || name.length() <= 6 || name.length() > 100)
            throw new InvalidParameterException("Name must be valid.");
    }

    private void validatePassword(String password) throws InvalidParameterException {
        if (password == null
                || password.length() < 8
                || password.length() > 20
                || password.contains(" "))
            throw new InvalidParameterException("Password must have between 8 and 20 characters and no invalid characters.");
    }
}
