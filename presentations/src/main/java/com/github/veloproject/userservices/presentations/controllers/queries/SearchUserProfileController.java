package com.github.veloproject.userservices.presentations.controllers.queries;

import com.github.veloproject.userservices.application.mediators.implementations.LoggingMediatorImp;
import com.github.veloproject.userservices.application.queries.search_user_profile.SearchUserProfileQuery;
import com.github.veloproject.userservices.application.queries.search_user_profile.SearchUserProfileQueryResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class SearchUserProfileController {
    private final LoggingMediatorImp mediator;

    public SearchUserProfileController(LoggingMediatorImp mediator) {
        this.mediator = mediator;
    }

    @GetMapping("/v1/search")
    public ResponseEntity<SearchUserProfileQueryResult> searchUserProfile(
            @RequestParam @Valid @NotBlank @Size(max = 25) String nickname) {
        var query = new SearchUserProfileQuery(nickname);
        var response = mediator.send(query);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }
}
