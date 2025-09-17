package com.github.veloproject.userservices.presentations.grpc;

import com.github.veloproject.userservices.application.mediators.contracts.Mediator;
import com.github.veloproject.userservices.application.queries.search_user_by_id.SearchUserByIdQuery;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class UserServiceGrpcHandler extends UserServiceGrpc.UserServiceImplBase {

    private final Mediator mediator;

    @Override
    public void userExistsById(UserExistsByIdRequest request, StreamObserver<UserExistsByIdResponse> responseObserver) {
        var query = new SearchUserByIdQuery(request.getId());
        boolean userExists;

        try {
            var userResponse = mediator.send(query);
            userExists = userResponse.getUser() != null;
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription("Error while checking user existence: " + e.getMessage())
                    .asRuntimeException());
            return;
        }

        UserExistsByIdResponse response = UserExistsByIdResponse.newBuilder()
                .setExists(userExists)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
