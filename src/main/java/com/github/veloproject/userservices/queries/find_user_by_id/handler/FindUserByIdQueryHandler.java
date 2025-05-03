package com.github.veloproject.userservices.queries.find_user_by_id.handler;

import com.github.veloproject.userservices.mediators.contracts.RequestHandler;
import com.github.veloproject.userservices.persistence.repositories.UserRepository;
import com.github.veloproject.userservices.queries.find_user_by_id.FindUserByIdQuery;
import com.github.veloproject.userservices.queries.find_user_by_id.FindUserByIdQueryResult;
import com.github.veloproject.userservices.shared.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FindUserByIdQueryHandler implements RequestHandler<FindUserByIdQuery, FindUserByIdQueryResult> {
    private final UserRepository repository;

    public FindUserByIdQueryHandler(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public FindUserByIdQueryResult handle(FindUserByIdQuery request) {
        var foundUser = repository.findById(request.getUserId());
        if (foundUser.isEmpty()) {
            throw new NotFoundException("user");
        }

        foundUser.get().setPassword(null);

        return new FindUserByIdQueryResult
                (200, "User has successfully found.", foundUser.get());
    }
}
