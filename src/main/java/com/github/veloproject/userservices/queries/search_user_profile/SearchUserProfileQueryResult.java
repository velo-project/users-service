package com.github.veloproject.userservices.queries.search_user_profile;

import com.github.veloproject.userservices.mediators.contracts.Response;
import com.github.veloproject.userservices.persistence.entities.UserEntity;
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
