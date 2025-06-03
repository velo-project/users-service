package com.github.veloproject.userservices.commands.register_new_user.handler;

import com.github.veloproject.userservices.commands.register_new_user.RegisterNewUserCommand;
import com.github.veloproject.userservices.commands.register_new_user.RegisterNewUserCommandResult;
import com.github.veloproject.userservices.mediators.contracts.handlers.NoAuthRequestHandler;
import com.github.veloproject.userservices.persistence.entities.RoleEntity;
import com.github.veloproject.userservices.persistence.entities.UserEntity;
import com.github.veloproject.userservices.persistence.repositories.RoleRepository;
import com.github.veloproject.userservices.persistence.repositories.UserRepository;
import com.github.veloproject.userservices.shared.exceptions.AlreadyExistsException;
import com.github.veloproject.userservices.shared.exceptions.InvalidParameterException;
import com.github.veloproject.userservices.shared.utils.CryptographyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class RegisterNewUserCommandHandler extends NoAuthRequestHandler<RegisterNewUserCommand, RegisterNewUserCommandResult> {
    private final UserRepository repository;
    private final RoleRepository roleRepository;

    public RegisterNewUserCommandHandler(UserRepository repository,
                                        RoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public RegisterNewUserCommandResult handle(RegisterNewUserCommand command) {
        validateEmail(command.getEmail());
        validateName(command.getName());
        validateNickname(command.getNickname());
        validatePassword(command.getPassword());

        var hashedPassword = CryptographyUtils
                .encrypt(command.getPassword());

        UserEntity userEntity = new UserEntity(
                command.getName(),
                command.getNickname(),
                command.getEmail(),
                hashedPassword
        );
        userEntity.setIsBlocked(false);
        userEntity.setIsDeleted(false);
        userEntity.setRoles(Set.of(roleRepository.findByName(
                RoleEntity
                .Values
                .USER.name())));

        var savedUser = repository.save(userEntity);

        return new RegisterNewUserCommandResult(
                200,
                "Successfully registered.",
                savedUser.getId());
    }

    private void validateEmail(String email) throws AlreadyExistsException, InvalidParameterException {
        String regex = "^(?=.{1,60}$)[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        if (repository.existsByEmail(email)) throw new AlreadyExistsException("email");
        else if (email == null || !email.matches(regex)) throw new InvalidParameterException("Email address must be valid.");
    }

    private void validateName(String name) throws InvalidParameterException {
        String regex = "^[A-Za-zÀ-ÿ](?:[A-Za-zÀ-ÿ ]{0,98}[A-Za-zÀ-ÿ])?$";

        if (name == null || !name.matches(regex)) throw new InvalidParameterException("Name must be valid.");
    }

    private void validateNickname(String nickname) throws InvalidParameterException {
        String regex = "^[a-zA-Z0-9._]{2,20}$";

        if (repository.existsByNickname(nickname)) throw new InvalidParameterException("Nickname already exists.");
        else if (nickname == null || !nickname.matches(regex)) throw new InvalidParameterException("Nickname must be valid.");
    }

    private void validatePassword(String password) throws InvalidParameterException {
        String regex = "^[^\\\\s]{8,20}$";

        if (password == null || !password.matches(regex))
            throw new InvalidParameterException("Password must have between 8 and 20 characters and no invalid characters.");
    }
}
