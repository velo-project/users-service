package com.github.veloproject.userservices.application.queries.get_users_by_id_list.handler;

import com.github.veloproject.userservices.application.abstractions.repositories.IUserRepository;
import com.github.veloproject.userservices.application.mediators.contracts.handlers.NoAuthRequestHandler;
import com.github.veloproject.userservices.application.queries.get_users_by_id_list.GetUsersByIdListQuery;
import com.github.veloproject.userservices.application.queries.get_users_by_id_list.GetUsersByIdListQueryResult;
import com.github.veloproject.userservices.domain.exceptions.InvalidParameterException;
import org.springframework.stereotype.Service;

@Service
public class GetUsersByIdListQueryHandler extends NoAuthRequestHandler<GetUsersByIdListQuery, GetUsersByIdListQueryResult> {
    private final IUserRepository userRepository;

    public GetUsersByIdListQueryHandler(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public GetUsersByIdListQueryResult handle(GetUsersByIdListQuery query) {
        if (query.ids().isEmpty()) throw new InvalidParameterException("Parâmetro 'ids' é inválido.");

        var userList = userRepository.findAllByIdIn(query.ids());
        return new GetUsersByIdListQueryResult(200, "Sucesso.", userList);

    }
}
