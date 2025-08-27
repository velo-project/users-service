package com.github.veloproject.userservices.application.queries.search_user_profile;

import com.github.veloproject.userservices.application.mediators.contracts.Response;
import com.github.veloproject.userservices.domain.entities.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchUserProfileQueryResult extends Response {
    private final UserEntity user;

    public SearchUserProfileQueryResult(Integer statusCode, String message, UserEntity user) {
        super(statusCode, message);
        this.user = user;
    }
}
