package com.github.veloproject.userservices.application.queries.search_user_by_id.handler;

import com.github.veloproject.userservices.application.abstractions.repositories.IUserRepository;
import com.github.veloproject.userservices.application.mediators.contracts.handlers.NoAuthRequestHandler;
import com.github.veloproject.userservices.application.queries.search_user_by_id.SearchUserByIdQuery;
import com.github.veloproject.userservices.application.queries.search_user_by_id.SearchUserByIdQueryResult;
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
        var user = repository.findById(request.getUserId());
        if (user.isEmpty())
            throw new NotFoundException("User");

        var userGet = user.get();

        return new SearchUserByIdQueryResult(
                200,
                "Found.",
                SearchUserByIdQueryResult.UserDto.builder()
                        .id(userGet.getId())
                        .name(userGet.getName())
                        .nickname(userGet.getNickname())
                        .bannerPhotoUrl(userGet.getBannerPhotoUrl())
                        .profilePhotoUrl(userGet.getProfilePhotoUrl())
                        .description(userGet.getDescription())
                        .email(userGet.getEmail())
                        .isBlocked(userGet.getIsBlocked())
                        .isDeleted(userGet.getIsDeleted())
                        .build()
        );
    }
}
