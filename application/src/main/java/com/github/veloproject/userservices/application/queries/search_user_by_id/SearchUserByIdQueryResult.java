package com.github.veloproject.userservices.application.queries.search_user_by_id;

import com.github.veloproject.userservices.application.mediators.contracts.Response;
import com.github.veloproject.userservices.domain.entities.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchUserByIdQueryResult extends Response {
    private final UserEntity user;

    public SearchUserByIdQueryResult(Integer statusCode, String message, UserEntity user) {
        super(statusCode, message);
        this.user = user;
    }
}
