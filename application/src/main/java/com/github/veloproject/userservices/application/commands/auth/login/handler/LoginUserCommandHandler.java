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
import java.util.HashMap;
import java.util.Map;
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
                .orElseThrow(IncorrectInformationsProvidedException::new);

        if (!user.getPassword().compare(request.getPassword())) {
            throw new IncorrectInformationsProvidedException();
        }

        var key = send2FACode(user.getEmail());

        return new LoginUserCommandResult(200, "Waiting for confirmation.", key);
    }

    private String send2FACode(String email) {
        var htmlTemplate = """
        <html lang="pt-BR">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@100..900&family=Roboto:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
        <head>
            <meta charset="UTF-8" />
            <title>Código de Verificação 2FA</title>
            <style>
                body {
                    font-family: "Outfit", sans-serif;
                    background-color: #FDFEF6;
                    padding: 20px;
                }
        
                .logo {
                    width: 35vh;
                }
        
                .container {
                    padding: 10vh 5vh;
                    border-radius: 8px;
                    max-width: 400px;
                    margin: auto;
                    border: 1px solid #23272a5f;
                    text-align: center;
                }
                h1 {
                    color: #333;
                }
                .code {
                    font-size: 2.5rem;
                    font-weight: bold;
                    margin: 20px 0;
                    letter-spacing: 8px;
                    color: #007bff;
                }
                p {
                    color: #555;
                    font-size: 1rem;
                }
                .footer {
                    margin-top: 30px;
                    font-size: 0.85rem;
                    color: #888;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <img src="https://media.discordapp.net/attachments/1226716662485614682/1442022514569121922/velo.png?ex=6923eb9e&is=69229a1e&hm=5373db23378b66d7a84e0c7976d8c6fec985ce2485b69da8ac6f012f0f7fe776&=&format=webp&quality=lossless&width=920&height=791" class="logo">
                <h1>AUTENTICAÇÃO DE DOIS FATORES</h1>
                <p><strong>Olá! 👋</strong><br>Vimos que você está tentando acessar o Velo. Aqui está seu código de autenticação de dois fatores 🔒:</p>
                <div class="code">{{code}}</div>
                <p>Este código é válido por <strong>{{expiresIn}} minutos</strong>, então <strong>pedale! 🚵</strong></p>
                <div class="footer">
                    NÃO COMPARTILHE esse código com terceiros!
                    Pensando em sua segurança, o objetivo deste e-mail é somente avisar que <strong><u>houve uma tentativa de login<u></strong>. Caso você <u>não reconheça este acesso</u>, <u>altere sua senha</u> e entre em contato conosco pela nossa <u>Central de Ajuda</u>.</div>
                </div>
            </div>
        </body>
        </html>
        """;
        var code = String.format("%06d", new SecureRandom().nextInt(999999));
        var expiresIn = 15;

        Map<String, String> variables = new HashMap<>();
        variables.put("code", code);
        variables.put("expiresIn", String.valueOf(expiresIn));

        this.email.sendWithTemplate(email,
                "Código de Autenticação de dois fatores | " + code,
                htmlTemplate,
                variables);

        var key = UUID.randomUUID().toString();
        var json = gson.toJson(TFACode.builder()
                .code(code)
                .email(email)
                .build());

        cache.save(key, json, Duration.ofMinutes(expiresIn));

        return key;
    }
}

