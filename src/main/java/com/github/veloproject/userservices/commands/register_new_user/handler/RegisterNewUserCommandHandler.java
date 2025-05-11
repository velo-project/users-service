package com.github.veloproject.userservices.commands.register_new_user.handler;

import com.github.veloproject.userservices.commands.register_new_user.RegisterNewUserCommand;
import com.github.veloproject.userservices.commands.register_new_user.RegisterNewUserCommandResult;
import com.github.veloproject.userservices.mediators.contracts.RequestHandler;
import com.github.veloproject.userservices.persistence.entities.UserEntity;
import com.github.veloproject.userservices.persistence.repositories.UserRepository;
import com.github.veloproject.userservices.shared.exceptions.InvalidParameterException;
import com.github.veloproject.userservices.shared.utils.CryptographyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterNewUserCommandHandler implements RequestHandler<RegisterNewUserCommand, RegisterNewUserCommandResult> {
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

    private void validateEmail(String email) throws InvalidParameterException {
        if (repository.existsByEmail(email)
                || email.contains(" ")
                || !email.contains("@")
                || email.length() < 6
                || email.length() > 60)
            throw new InvalidParameterException("email");
    }

    private void validateName(String name) throws InvalidParameterException {
        if (name == null)
            throw new InvalidParameterException("name");
        int nameWordsLength = name.trim().split("\\s+").length;

        if (nameWordsLength < 2 || name.length() <= 6 || name.length() > 100)
            throw new InvalidParameterException("name");
    }

    private void validatePassword(String password) throws InvalidParameterException {
        if (password == null
                || password.length() < 8
                || password.length() > 20
                || password.contains(" "))
            throw new InvalidParameterException("password");
    }
}
