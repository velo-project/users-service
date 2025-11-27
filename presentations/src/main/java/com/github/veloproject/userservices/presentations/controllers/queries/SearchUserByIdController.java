package com.github.veloproject.userservices.presentations.controllers.queries;

import com.github.veloproject.userservices.application.mediators.implementations.LoggingMediatorImp;
import com.github.veloproject.userservices.application.queries.search_user_by_id.SearchUserByIdQuery;
import com.github.veloproject.userservices.application.queries.search_user_by_id.SearchUserByIdQueryResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class SearchUserByIdController {
    private final LoggingMediatorImp mediator;

    public SearchUserByIdController(LoggingMediatorImp mediator) {
        this.mediator = mediator;
    }

    @GetMapping("/v2/search")
    public ResponseEntity<SearchUserByIdQueryResult> searchUserById(
            @RequestParam @Valid @NotNull Integer id
    ) {
        var query = new SearchUserByIdQuery(id);
        var response = mediator.send(query);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }
}
