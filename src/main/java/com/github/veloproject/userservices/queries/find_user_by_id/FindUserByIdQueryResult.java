package com.github.veloproject.userservices.queries.find_user_by_id;

import com.github.veloproject.userservices.mediators.contracts.Response;
import com.github.veloproject.userservices.persistence.entities.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindUserByIdQueryResult extends Response {
    private UserEntity foundUser;

    public FindUserByIdQueryResult(Integer status, String message, UserEntity foundUser) {
        super(status, message);
        this.foundUser = foundUser;
    }
}
