package com.github.veloproject.domain.enums;

import lombok.Getter;

@Getter
public enum TypesOfUser {
    USER(1),
    ENTERPRISE(2),
    ADMIN(3);

    final Integer value;

    TypesOfUser(Integer value) {
        this.value = value;
    }
}
