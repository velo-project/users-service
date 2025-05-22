package com.github.veloproject.userservices.queries.search_user_profile;

import com.github.veloproject.userservices.mediators.contracts.Request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchUserProfileQuery implements Request<SearchUserProfileQueryResult> {
    private String nickname;

    public SearchUserProfileQuery(String nickname) {
        this.nickname = nickname;
    }
}
