package com.github.veloproject.userservices.application.queries.search_user_by_id;

import com.github.veloproject.userservices.application.mediators.contracts.Response;
import com.github.veloproject.userservices.domain.entities.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchUserByIdQueryResult extends Response {
    private final UserDto user;

    public SearchUserByIdQueryResult(Integer statusCode, String message, UserDto user) {
        super(statusCode, message);
        this.user = user;
    }

    @Builder
    @Getter
    public static class UserDto {
        private Integer id;
        private String nickname;
        private String name;
        private String description;
        private String email;
        private String bannerPhotoUrl;
        private String profilePhotoUrl;
        private Boolean isDeleted;
        private Boolean isBlocked;
    }
}
