package com.github.veloproject.userservices.application.queries.search_user_profile.handler;

import com.github.veloproject.userservices.application.abstractions.repositories.IUserRepository;
import com.github.veloproject.userservices.application.mediators.contracts.handlers.NoAuthRequestHandler;
import com.github.veloproject.userservices.application.queries.search_user_profile.SearchUserProfileQuery;
import com.github.veloproject.userservices.application.queries.search_user_profile.SearchUserProfileQueryResult;
import com.github.veloproject.userservices.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SearchUserProfileQueryHandler extends NoAuthRequestHandler<SearchUserProfileQuery, SearchUserProfileQueryResult> {
    private final IUserRepository repository;

    public SearchUserProfileQueryHandler(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public SearchUserProfileQueryResult handle(SearchUserProfileQuery request) {
        var user = repository.findByNickname(request.getNickname())
                .orElseThrow(() -> new NotFoundException(request.getNickname()));

        return new SearchUserProfileQueryResult(
                200,
                "Usuário encontrado.",
                user
        );
    }
}
