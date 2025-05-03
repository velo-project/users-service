package com.github.veloproject.userservices.api.controllers.queries;

import com.github.veloproject.userservices.mediators.LoggingMediatorImp;
import com.github.veloproject.userservices.queries.find_user_by_id.FindUserByIdQuery;
import com.github.veloproject.userservices.queries.find_user_by_id.FindUserByIdQueryResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user_services")
public class FindUserByIdController {
    private final LoggingMediatorImp mediator;

    public FindUserByIdController(LoggingMediatorImp mediator) {
        this.mediator = mediator;
    }

    @GetMapping("/find/{userId}")
    public ResponseEntity<FindUserByIdQueryResult> findUserById(@PathVariable Integer userId)
    {
        var command = new FindUserByIdQuery(userId);
        var response = mediator.send(command);
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    @GetMapping("/find")
    public ResponseEntity<FindUserByIdQueryResult> findUserById(@RequestBody FindUserByIdQuery command)
    {
        var response = mediator.send(command);
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }
}
