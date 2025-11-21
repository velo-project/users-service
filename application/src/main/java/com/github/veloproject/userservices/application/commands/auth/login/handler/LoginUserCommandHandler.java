package com.github.veloproject.userservices.application.commands.auth.login.handler;

import com.github.veloproject.userservices.application.abstractions.cache.IMemoryCache;
import com.github.veloproject.userservices.application.abstractions.repositories.IUserRepository;
import com.github.veloproject.userservices.application.abstractions.services.IEmailService;
import com.github.veloproject.userservices.application.commands.auth.login.LoginUserCommand;
import com.github.veloproject.userservices.application.commands.auth.login.LoginUserCommandResult;
import com.github.veloproject.userservices.application.dtos.TFACode;
import com.github.veloproject.userservices.application.mediators.contracts.handlers.NoAuthRequestHandler;
import com.github.veloproject.userservices.domain.exceptions.IncorrectInformationsProvidedException;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.UUID;

@Service
public class LoginUserCommandHandler extends NoAuthRequestHandler<LoginUserCommand, LoginUserCommandResult> {
    private final IUserRepository repository;
    private final IEmailService email;
    private final IMemoryCache cache;
    private final Gson gson;

    public LoginUserCommandHandler(IUserRepository repository, IEmailService email, IMemoryCache cache) {
        this.repository = repository;
        this.email = email;
        this.cache = cache;
        gson = new Gson();
    }

    @Override
    public LoginUserCommandResult handle(LoginUserCommand request) {
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(IncorrectInformationsProvidedException::new); // TODO Incorrect Informations Provided Exception

        if (!user.getPassword().compare(request.getPassword())) {
            throw new IncorrectInformationsProvidedException();
        }

        var key = send2FACode(user.getEmail());

        return new LoginUserCommandResult(200, "Waiting for confrmation.", key);
    }

    private String send2FACode(String email) {
        var code = String.format("%06d", new SecureRandom().nextInt(999999));

        this.email.send(email, "Código de autenticação de dois fatores", code);

        var key = UUID.randomUUID().toString();
        var json = gson.toJson(TFACode.builder()
                .code(code)
                .email(email)
                .build());

        cache.save(key, json, Duration.ofMinutes(15));

        return key;
    }
}

