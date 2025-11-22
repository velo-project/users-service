package com.github.veloproject.userservices.presentations.controllers.queries;

import com.github.veloproject.userservices.application.mediators.implementations.LoggingMediatorImp;
import com.github.veloproject.userservices.application.queries.get_token_expiration.GetTokenExpirationQuery;
import com.github.veloproject.userservices.application.queries.get_token_expiration.GetTokenExpirationQueryResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class GetTokenExpirationController {
    private final LoggingMediatorImp mediator;

    public GetTokenExpirationController(LoggingMediatorImp mediator) {
        this.mediator = mediator;
    }

    @GetMapping("/v1/get-expiration")
    public ResponseEntity<GetTokenExpirationQueryResult> getTokenExpiration(
            @RequestParam String token
    ) {
        var query = new GetTokenExpirationQuery(token);
        var response = mediator.send(query);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }
}
