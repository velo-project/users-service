package com.github.veloproject.userservices.application.queries.get_users_by_id_list;

import com.github.veloproject.userservices.application.mediators.contracts.Request;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record GetUsersByIdListQuery(
        @NotEmpty List<@NotNull Integer> ids
) implements Request<GetUsersByIdListQueryResult> {
}
