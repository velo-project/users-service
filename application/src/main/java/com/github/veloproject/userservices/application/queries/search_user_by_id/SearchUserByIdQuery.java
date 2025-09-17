package com.github.veloproject.userservices.application.queries.search_user_by_id;

import com.github.veloproject.userservices.application.mediators.contracts.Request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchUserByIdQuery implements Request<SearchUserByIdQueryResult> {
    private Integer userId;

    public SearchUserByIdQuery(Integer userId) {
        this.userId = userId;
    }
}
