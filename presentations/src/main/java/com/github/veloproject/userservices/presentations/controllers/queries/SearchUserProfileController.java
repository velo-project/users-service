package com.github.veloproject.userservices.presentations.controllers.queries;

import com.github.veloproject.userservices.application.mediators.implementations.LoggingMediatorImp;
import com.github.veloproject.userservices.application.queries.search_user_profile.SearchUserProfileQuery;
import com.github.veloproject.userservices.application.queries.search_user_profile.SearchUserProfileQueryResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user_services")
public class SearchUserProfileController {
    private final LoggingMediatorImp mediator;

    public SearchUserProfileController(LoggingMediatorImp mediator) {
        this.mediator = mediator;
    }

    @GetMapping("/search")
    public ResponseEntity<SearchUserProfileQueryResult> searchUserProfile(@RequestParam String nickname) {
        var query = new SearchUserProfileQuery(nickname);
        var response = mediator.send(query);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }
}
