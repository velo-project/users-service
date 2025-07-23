package com.github.veloproject.userservices.api.messaging;

import com.github.veloproject.userservices.commands.admin.block_user.BlockUserCommand;
import com.github.veloproject.userservices.mediators.implementations.LoggingMediatorImp;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class UserBlockListener {
    private final LoggingMediatorImp mediator;
    private final JwtDecoder jwtDecoder;

    public UserBlockListener(LoggingMediatorImp mediator,
                             JwtDecoder jwtDecoder) {
        this.mediator = mediator;
        this.jwtDecoder = jwtDecoder;
    }


    @RabbitListener(queues = "user-block-queue")
    public void listenUserBlockQueue(@Payload BlockUserCommand dto,
                                     @Header("Authorization") String authToken) {
        Jwt jwt = jwtDecoder.decode(authToken);  // decodifica direto
        JwtAuthenticationToken jwtAuthToken = new JwtAuthenticationToken(jwt);

        mediator.send(dto, jwtAuthToken);
    }

}
