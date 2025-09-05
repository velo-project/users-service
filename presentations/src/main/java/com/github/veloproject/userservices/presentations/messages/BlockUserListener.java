package com.github.veloproject.userservices.presentations.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.veloproject.userservices.application.commands.admin.block_user.BlockUserCommand;
import com.github.veloproject.userservices.application.mediators.implementations.LoggingMediatorImp;
import com.github.veloproject.userservices.domain.exceptions.InternalErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class BlockUserListener {

    private final LoggingMediatorImp mediator;
    private final JwtDecoder jwtDecoder;

    public BlockUserListener(LoggingMediatorImp mediator,
                             JwtDecoder jwtDecoder) {
        this.mediator = mediator;
        this.jwtDecoder = jwtDecoder;
    }

    @RabbitListener(queues = "user-block-queue")
    public void onMessage(@Payload Message message) {
        String strToken = message.getMessageProperties()
                .getHeader("Authorization");
        if (strToken == null) {
            throw new InvalidBearerTokenException("Token is required");
        }
        JwtAuthenticationToken jwtToken = new JwtAuthenticationToken(jwtDecoder.decode(strToken));

        String json = new String(message.getBody(), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        BlockUserCommand command;

        try {
            command = mapper.readValue(json, BlockUserCommand.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new InternalErrorException("An internal error occurred.");
        }

        mediator.send(command, jwtToken);
    }
}
