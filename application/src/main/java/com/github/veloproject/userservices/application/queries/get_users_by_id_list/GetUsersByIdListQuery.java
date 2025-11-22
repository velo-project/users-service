package com.github.veloproject.userservices.application.queries.get_users_by_id_list;

import com.github.veloproject.userservices.application.mediators.contracts.Request;

import java.util.List;

public record GetUsersByIdListQuery(
        List<Integer> ids
) implements Request<GetUsersByIdListQueryResult> {
}
