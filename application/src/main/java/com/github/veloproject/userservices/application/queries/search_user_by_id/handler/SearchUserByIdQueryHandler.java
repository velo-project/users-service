package com.github.veloproject.userservices.application.queries.search_user_by_id.handler;

import com.github.veloproject.userservices.application.abstractions.repositories.IUserRepository;
import com.github.veloproject.userservices.application.mediators.contracts.handlers.NoAuthRequestHandler;
import com.github.veloproject.userservices.application.queries.search_user_by_id.SearchUserByIdQuery;
import com.github.veloproject.userservices.application.queries.search_user_by_id.SearchUserByIdQueryResult;
import com.github.veloproject.userservices.domain.exceptions.InvalidParameterException;
import com.github.veloproject.userservices.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SearchUserByIdQueryHandler extends NoAuthRequestHandler<SearchUserByIdQuery, SearchUserByIdQueryResult> {
    private final IUserRepository repository;

    public SearchUserByIdQueryHandler(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public SearchUserByIdQueryResult handle(SearchUserByIdQuery request) {
        if (request.getUserId() == null) throw new InvalidParameterException("userId must be specified.");

        var user = repository.getReferenceById(request.getUserId());
        if (user == null) throw new NotFoundException("User not found.");

        return new SearchUserByIdQueryResult(
                200,
                "Found.",
                user
        );
    }
}
