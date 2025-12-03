package com.github.veloproject.userservices.presentations.grpc;

import com.github.veloproject.userservices.application.mediators.contracts.Mediator;
import com.github.veloproject.userservices.application.queries.get_users_by_id_list.GetUsersByIdListQuery;
import com.github.veloproject.userservices.application.queries.search_user_by_id.SearchUserByIdQuery;
import com.github.veloproject.userservices.application.queries.search_user_profile.SearchUserProfileQuery;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class UserServiceGrpcHandler extends UserServiceGrpc.UserServiceImplBase {
    private final Mediator mediator;

    @Override
    public void userExistsById(UserExistsByIdRequest request,
                               StreamObserver<UserExistsByIdResponse> responseObserver) {
        var query = new SearchUserByIdQuery(request.getId());
        boolean userExists;

        try {
            var userResponse = mediator.send(query);
            userExists = userResponse.getUser() != null;
        } catch (Exception e) {
            responseObserver.onError(Status.NOT_FOUND
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

    @Override
    public void getUserByNickname(GetUserByNicknameRequest request,
                            StreamObserver<GetUserByNicknameResponse> responseObserver) {
        String userNickname = request.getNickname();
        try {
            var userResponse = mediator.send(new SearchUserProfileQuery(userNickname));
            var user = userResponse.getUser();
            if (user == null) {
                responseObserver.onError(io.grpc.Status.NOT_FOUND
                        .withDescription("Usuário não encontrado com nickname: " + userNickname)
                        .asRuntimeException());
                return;
            }

            UserEntity userProto = UserEntity.newBuilder()
                    .setId(user.getId())
                    .setName(user.getName())
                    .setNickname(user.getNickname())
                    .setBannerPhotoUrl(user.getBannerPhotoUrl() != null ? user.getBannerPhotoUrl() : "")
                    .setProfilePhotoUrl(user.getProfilePhotoUrl() != null ? user.getProfilePhotoUrl() : "")
                    .setIsBlocked(user.getIsBlocked())
                    .setIsDeleted(user.getIsDeleted())
                    .build();

            GetUserByNicknameResponse response = GetUserByNicknameResponse.newBuilder()
                    .setUser(userProto)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription("Erro ao buscar usuário: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void getUserById(GetUserByIdRequest request,
                            StreamObserver<GetUserByIdResponse> responseObserver) {
        int userId = request.getId();
        try {
            var userResponse = mediator.send(new SearchUserByIdQuery(userId));
            var user = userResponse.getUser();
            if (user == null) {
                responseObserver.onError(io.grpc.Status.NOT_FOUND
                        .withDescription("Usuário não encontrado com id: " + userId)
                        .asRuntimeException());
                return;
            }

            UserEntity userProto = UserEntity.newBuilder()
                    .setId(user.getId())
                    .setName(user.getName())
                    .setNickname(user.getNickname())
                    .setBannerPhotoUrl(user.getBannerPhotoUrl() != null ? user.getBannerPhotoUrl() : "")
                    .setProfilePhotoUrl(user.getProfilePhotoUrl() != null ? user.getProfilePhotoUrl() : "")
                    .setIsBlocked(user.getIsBlocked())
                    .setIsDeleted(user.getIsDeleted())
                    .build();

            GetUserByIdResponse response = GetUserByIdResponse.newBuilder()
                    .setUser(userProto)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription("Erro ao buscar usuário: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void getUsersByIdList(GetUsersByIdListRequest request,
                                 StreamObserver<GetUsersByIdListResponse> responseObserver) {
        List<Integer> ids = request.getIdList();

        if (ids.isEmpty()) {
            responseObserver.onNext(GetUsersByIdListResponse.newBuilder().build());
            responseObserver.onCompleted();
            return;
        }

        try {
            var queryResponse = mediator.send(new GetUsersByIdListQuery(ids));
            List<com.github.veloproject.userservices.domain.entities.UserEntity> users = queryResponse.getUsers();

            var builder = GetUsersByIdListResponse.newBuilder();
            for (var user : users) {
                UserEntity userProto = UserEntity.newBuilder()
                        .setId(user.getId())
                        .setName(user.getName() != null ? user.getName() : "")
                        .setNickname(user.getNickname() != null ? user.getNickname() : "")
                        .setBannerPhotoUrl(user.getBannerPhotoUrl() != null ? user.getBannerPhotoUrl() : "")
                        .setProfilePhotoUrl(user.getProfilePhotoUrl() != null ? user.getProfilePhotoUrl() : "")
                        .setIsBlocked(user.getIsBlocked())
                        .setIsDeleted(user.getIsDeleted())
                        .build();
                builder.addUser(userProto);
            }

            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Erro ao buscar lista de usuários: " + e.getMessage())
                    .asRuntimeException());
        }
    }
}
