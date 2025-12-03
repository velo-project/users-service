package com.github.veloproject.userservices.application.commands.auth.forgot_my_password_2fa.handler;

import com.github.veloproject.userservices.application.abstractions.cache.IMemoryCache;
import com.github.veloproject.userservices.application.abstractions.repositories.IUserRepository;
import com.github.veloproject.userservices.application.commands.auth.forgot_my_password_2fa.PasswordRecoveryConfirmationCommand;
import com.github.veloproject.userservices.application.commands.auth.forgot_my_password_2fa.PasswordRecoveryConfirmationCommandResult;
import com.github.veloproject.userservices.application.dtos.PRConfirmationCode;
import com.github.veloproject.userservices.application.mediators.contracts.handlers.NoAuthRequestHandler;
import com.github.veloproject.userservices.domain.exceptions.IncorrectInformationsProvidedException;
import com.github.veloproject.userservices.domain.exceptions.InternalErrorException;
import com.github.veloproject.userservices.domain.exceptions.NotFoundException;
import com.github.veloproject.userservices.domain.valueObjects.PasswordValueObject;
import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordRecoveryConfirmationCommandHandler extends NoAuthRequestHandler<PasswordRecoveryConfirmationCommand, PasswordRecoveryConfirmationCommandResult> {
    private final IUserRepository repository;
    private final IMemoryCache cache;
    private final Gson gson;

    public PasswordRecoveryConfirmationCommandHandler(IUserRepository repository, IMemoryCache cache, JwtEncoder tokenEncoder) {
        this.repository = repository;
        this.cache = cache;
        gson = new Gson();
    }

    @Override
    @Transactional
    public PasswordRecoveryConfirmationCommandResult handle(PasswordRecoveryConfirmationCommand request) {
        var code = cache.get(request.key());
        if (code == null) throw new IncorrectInformationsProvidedException();

        var codeObject = gson.fromJson(code, PRConfirmationCode.class);

        if (!request.code().equals(codeObject.getCode()))
            throw new IncorrectInformationsProvidedException();

        var isDeleted = cache.delete(request.key());
        if (!isDeleted)
            throw new InternalErrorException("Ocorreu um erro interno.");

        var user = repository.findByEmail(codeObject.getEmail())
                .orElseThrow(() -> new NotFoundException("Usuário"));

        user.setPassword(new PasswordValueObject(request.newPassword(), true));
        repository.save(user);

        return new PasswordRecoveryConfirmationCommandResult(
                200,
                "Senha redefinida."
        );
    }
}
