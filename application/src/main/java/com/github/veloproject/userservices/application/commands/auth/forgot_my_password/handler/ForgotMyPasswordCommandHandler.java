package com.github.veloproject.userservices.application.commands.auth.forgot_my_password.handler;

import com.github.veloproject.userservices.application.abstractions.cache.IMemoryCache;
import com.github.veloproject.userservices.application.abstractions.repositories.IUserRepository;
import com.github.veloproject.userservices.application.abstractions.services.IEmailService;
import com.github.veloproject.userservices.application.commands.auth.forgot_my_password.ForgotMyPasswordCommand;
import com.github.veloproject.userservices.application.commands.auth.forgot_my_password.ForgotMyPasswordCommandResult;
import com.github.veloproject.userservices.application.mediators.contracts.handlers.NoAuthRequestHandler;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

@Service
public class ForgotMyPasswordCommandHandler extends NoAuthRequestHandler<ForgotMyPasswordCommand, ForgotMyPasswordCommandResult> {
    private final IUserRepository repository;
    private final IEmailService email;
    private final IMemoryCache cache;
    private final Gson gson;

    public ForgotMyPasswordCommandHandler(IUserRepository repository,
                                          IEmailService email,
                                          IMemoryCache cache) {
        this.repository = repository;
        this.email = email;
        this.cache = cache;
        this.gson = new Gson();
    }

    @Override
    public ForgotMyPasswordCommandResult handle(ForgotMyPasswordCommand request) {
        return null;
    }
}
