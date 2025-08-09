package com.github.veloproject.application.commands.auth.login.handler;

import com.github.veloproject.application.abstractions.cache.IMemoryCache;
import com.github.veloproject.application.abstractions.repositories.IUserRepository;
import com.github.veloproject.application.abstractions.services.IEmailService;
import com.github.veloproject.application.commands.auth.login.LoginUserCommand;
import com.github.veloproject.application.commands.auth.login.LoginUserCommandResult;
import com.github.veloproject.application.mediators.contracts.handlers.NoAuthRequestHandler;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.UUID;

@Service
public class LoginUserCommandHandler extends NoAuthRequestHandler<LoginUserCommand, LoginUserCommandResult> {
    private final IUserRepository repository;
    private final IEmailService email;
    private final IMemoryCache cache;

    public LoginUserCommandHandler(IUserRepository repository, IEmailService email, IMemoryCache cache) {
        this.repository = repository;
        this.email = email;
        this.cache = cache;
    }

    @Override
    public LoginUserCommandResult handle(LoginUserCommand request) {
        var user = repository.findByEmail(request.getEmail())
                .filter(u -> u.getPassword().compare(request.getPassword()))
                .orElseThrow(RuntimeException::new); // TODO Incorrect Informations Provided Exception

        var key = send2FACode(user.getEmail());

        return new LoginUserCommandResult(200, "Waiting for confrmation.", key);
    }

    private String send2FACode(String email) {
        var code = String.format("%06d", new SecureRandom().nextInt(999999));

        this.email.send(email, "Código de autenticação de dois fatores", code);

        var key = UUID.randomUUID().toString();
        cache.save(key, code, Duration.ofMinutes(15));

        return key;
    }
}
