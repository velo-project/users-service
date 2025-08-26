package com.github.veloproject.userservices.domain.entities;

import com.github.veloproject.userservices.domain.valueObjects.PasswordValueObject;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
public class UserEntity {
    private Integer id;
    private String name;
    private String email;
    /**
     * @apiNote Por favor, muito cuidado ao tirar a anotação @JsonIgnore deste atributo.
     */
    // @JsonIgnore
    private PasswordValueObject password;
    private String nickname;
    private String bannerPhotoUrl;
    private String profilePhotoUrl;
    private String description;

    @Setter(AccessLevel.PRIVATE)
    private Boolean isBlocked;

    @Setter(AccessLevel.PRIVATE)
    private Boolean isDeleted;

    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime registeredAt;

    @Setter(AccessLevel.PRIVATE)
    private Set<RoleEntity> roles;

    public void block() { this.isBlocked = true; }
    public void unblock() { this.isBlocked = false; }

    private void generateDeletedUserNickname() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        nickname = "deleted_" + uuid.substring(0, 12);
    }

    // Ação irreversivel
    public void delete() {
        this.isDeleted = true;
        generateDeletedUserNickname();
    }

    public void addRole(RoleEntity role) {
        this.roles.add(role);
    }
    public void deleteRole(RoleEntity role) {
        var roleExists = this.roles.stream()
                .anyMatch(r -> r == role);

        if (roleExists) {
            this.roles.remove(role);
        }
    }
}
