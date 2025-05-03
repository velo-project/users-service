package com.github.veloproject.userservices.queries.find_user_by_id;

import com.github.veloproject.userservices.mediators.contracts.Request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindUserByIdQuery implements Request<FindUserByIdQueryResult> {
    private Integer userId;

    public FindUserByIdQuery(Integer userId) {
        this.userId = userId;
    }
}
