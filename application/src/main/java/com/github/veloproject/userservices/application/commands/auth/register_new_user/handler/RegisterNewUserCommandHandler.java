package com.github.veloproject.userservices.application.commands.auth.register_new_user.handler;

import com.github.veloproject.userservices.application.abstractions.repositories.IRoleRepository;
import com.github.veloproject.userservices.application.abstractions.repositories.IUserRepository;
import com.github.veloproject.userservices.application.commands.auth.register_new_user.RegisterNewUserCommand;
import com.github.veloproject.userservices.application.commands.auth.register_new_user.RegisterNewUserCommandResult;
import com.github.veloproject.userservices.application.mediators.contracts.handlers.NoAuthRequestHandler;
import com.github.veloproject.userservices.domain.entities.UserEntity;
import com.github.veloproject.userservices.domain.enums.TypesOfUser;
import com.github.veloproject.userservices.domain.exceptions.AlreadyExistsException;
import com.github.veloproject.userservices.domain.valueObjects.PasswordValueObject;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RegisterNewUserCommandHandler extends NoAuthRequestHandler<RegisterNewUserCommand, RegisterNewUserCommandResult> {
    private final IUserRepository repository;
    private final IRoleRepository roleRepository;

    public RegisterNewUserCommandHandler(IUserRepository repository, IRoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public RegisterNewUserCommandResult handle(RegisterNewUserCommand command) {
        if (repository.existsByEmail(command.getEmail()))
            throw new AlreadyExistsException("E-mail");
        else if (repository.existsByNickname(command.getNickname())) throw new AlreadyExistsException("Nickname");

        var password = new PasswordValueObject(command.getPassword());
        var user = UserEntity.builder()
                .name(command.getName())
                .nickname(command.getNickname())
                .email(command.getEmail())
                .password(password)
                .isBlocked(false)
                .isDeleted(false)
                .build();

        var role = roleRepository.findByName(TypesOfUser.USER.name());

        user.addRole(role);

        var userId = repository.save(user);

        return new RegisterNewUserCommandResult(
                200,
                "Registrado com sucesso.",
                userId);
    }
}
