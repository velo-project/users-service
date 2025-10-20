package com.github.veloproject.userservices.application.queries.get_users_by_id_list;

import com.github.veloproject.userservices.application.mediators.contracts.Response;
import com.github.veloproject.userservices.domain.entities.UserEntity;
import lombok.Getter;

import java.util.List;

@Getter
public class GetUsersByIdListQueryResult extends Response {
    private final List<UserEntity> users;

    public GetUsersByIdListQueryResult(Integer statusCode,
                                       String message,
                                       List<UserEntity> users) {
        super(statusCode, message);
        this.users = users;
    }
}
