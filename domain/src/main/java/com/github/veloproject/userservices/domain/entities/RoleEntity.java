package com.github.veloproject.userservices.domain.entities;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoleEntity {
    private Integer id;
    private String name;
}
