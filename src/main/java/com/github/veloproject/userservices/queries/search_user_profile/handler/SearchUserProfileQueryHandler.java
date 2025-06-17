package com.github.veloproject.userservices.queries.search_user_profile.handler;

import com.github.veloproject.userservices.mediators.contracts.handlers.NoAuthRequestHandler;
import com.github.veloproject.userservices.persistence.repositories.UserRepository;
import com.github.veloproject.userservices.queries.search_user_profile.SearchUserProfileQuery;
import com.github.veloproject.userservices.queries.search_user_profile.SearchUserProfileQueryResult;
import com.github.veloproject.userservices.shared.exceptions.InvalidParameterException;
import com.github.veloproject.userservices.shared.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

// TODO Criar DTO Seguro de Usuário.
@Service
public class SearchUserProfileQueryHandler extends NoAuthRequestHandler<SearchUserProfileQuery, SearchUserProfileQueryResult> {
    private final UserRepository repository;

    public SearchUserProfileQueryHandler(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public SearchUserProfileQueryResult handle(SearchUserProfileQuery request) {
        if (request.getNickname() == null) throw new InvalidParameterException("Nickname must be specified.");

        var user = repository.getByNickname(request.getNickname())
                .filter(u -> !u.getIsDeleted())
                .orElseThrow(() -> new NotFoundException(request.getNickname()));

        return new SearchUserProfileQueryResult(
                200,
                "User has been found.",
                user
        );
    }
}
