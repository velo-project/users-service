package com.github.veloproject.userservices.application.commands.auth.forgot_my_password.handler;

import com.github.veloproject.userservices.application.abstractions.cache.IMemoryCache;
import com.github.veloproject.userservices.application.abstractions.repositories.IUserRepository;
import com.github.veloproject.userservices.application.abstractions.services.IEmailService;
import com.github.veloproject.userservices.application.commands.auth.forgot_my_password.PasswordRecoveryCommand;
import com.github.veloproject.userservices.application.commands.auth.forgot_my_password.PasswordRecoveryCommandResult;
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
public class PasswordRecoveryCommandHandler extends NoAuthRequestHandler<PasswordRecoveryCommand, PasswordRecoveryCommandResult> {
    private final IUserRepository repository;
    private final IEmailService email;
    private final IMemoryCache cache;
    private final Gson gson;

    public PasswordRecoveryCommandHandler(IUserRepository repository,
                                          IEmailService email,
                                          IMemoryCache cache) {
        this.repository = repository;
        this.email = email;
        this.cache = cache;
        this.gson = new Gson();
    }

    @Override
    public PasswordRecoveryCommandResult handle(PasswordRecoveryCommand request) {
        var user = repository.findByEmail(request.email())
                .orElseThrow(IncorrectInformationsProvidedException::new);

        var key = sendConfirmationCode(user.getEmail());

        return new PasswordRecoveryCommandResult(200, "Waiting for confirmation.", key);
    }

    private String sendConfirmationCode(String email) {
        var htmlTemplate = """
                <!DOCTYPE html>
                <html lang="pt-BR">
                  <head>
                    <link rel="preconnect" href="https://fonts.googleapis.com" />
                    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
                    <link
                      href="https://fonts.googleapis.com/css2?family=Outfit:wght@100..900&family=Roboto:ital,wght@0,100..900;1,100..900&display=swap"
                      rel="stylesheet"
                    />
                    <meta charset="UTF-8" />
                    <title>Código de Verificação 2FA</title>
                    <style>
                      body {
                        font-family: "Outfit", sans-serif;
                        background-color: #fdfef6;
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
                      <img
                        src="https://storage.googleapis.com/velo-static-files/velo-logo.png"
                        class="logo"
                      />
                      <h1>RECUPERAÇÃO DE SENHA</h1>
                      <p>
                        <strong>Olá! 👋</strong><br />Vimos que você está tentando recuperar sua senha do Velo.\s
                        Aqui está seu código de <strong>redefinição de senha 🔑</strong>:
                      </p>
                      <div class="code">{{code}}</div>
                      <p>
                        Este código é válido por <strong>{{expiresIn}} minutos</strong>, então
                        <strong>pedale! 🚵</strong>
                      </p>
                      <div class="footer">
                        <strong><u>NÃO COMPARTILHE esse código com terceiros!</u></strong>
                        Pensando em sua segurança, o objetivo deste e-mail é somente avisar que
                        <strong><u>houve uma tentativa de redefinição de senha</u></strong
                        >. Caso você <strong><u>não reconheça esta tentativa de acesso</u></strong>, ignore este e-mail.
                      </div>
                    </div>
                  </body>
                </html>
        """;
        var code = String.format("%06d", new SecureRandom().nextInt(999999));
        var expiresIn = 10;

        Map<String, String> variables = new HashMap<>();
        variables.put("code", code);
        variables.put("expiresIn", String.valueOf(expiresIn));

        this.email.sendWithTemplate(
                email,
                code + " | Código de recuperação de senha",
                htmlTemplate,
                variables
        );

        var key = UUID.randomUUID().toString();
        var json = gson.toJson(TFACode.builder()
                .code(code)
                .email(email)
                .build());

        cache.save(key, json, Duration.ofMinutes(expiresIn));

        return key;
    }
}
