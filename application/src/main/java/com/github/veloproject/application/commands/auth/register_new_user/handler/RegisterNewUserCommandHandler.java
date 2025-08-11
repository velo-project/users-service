package com.github.veloproject.application.commands.auth.register_new_user.handler;

import com.github.veloproject.application.abstractions.repositories.IRoleRepository;
import com.github.veloproject.application.abstractions.repositories.IUserRepository;
import com.github.veloproject.application.commands.auth.register_new_user.RegisterNewUserCommand;
import com.github.veloproject.application.commands.auth.register_new_user.RegisterNewUserCommandResult;
import com.github.veloproject.application.mediators.contracts.handlers.NoAuthRequestHandler;
import com.github.veloproject.domain.entities.UserEntity;
import com.github.veloproject.domain.enums.TypesOfUser;
import com.github.veloproject.domain.valueObjects.PasswordValueObject;
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
    public RegisterNewUserCommandResult handle(RegisterNewUserCommand command) {
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
                "Successfully registered.",
                userId);
    }
}
