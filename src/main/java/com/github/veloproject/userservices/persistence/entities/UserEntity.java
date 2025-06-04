package com.github.veloproject.userservices.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "tb_users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id_user"
    )
    private Integer id;

    @Column(
            name = "name_user",
            length = 100,
            nullable = false
    )
    private String name;

    @Column(
            name = "email_user",
            length = 60,
            nullable = false,
            unique = true
    )
    private String email;

    /**
     * @apiNote Por favor, muito cuidado ao tirar a anotação @JsonIgnore deste atributo.
     */
    @JsonIgnore
    @Column(
            name = "password_user",
            length = 60,
            nullable = false
    )
    private String password;

    @Column(
            name = "nickname_user",
            length = 20,
            nullable = false,
            unique = true
    )
    private String nickname;

    @Column(
            name = "banner_photo_url_user",
            columnDefinition = "TEXT"
    )
    private String bannerPhotoUrl;

    @Column(
            name = "profile_photo_url_user",
            columnDefinition = "TEXT"
    )
    private String profilePhotoUrl;

    @Column(
            name = "description_user",
            length = 255
    )
    private String description;

    @Column(
            name = "blocked_user"
    )
    private Boolean isBlocked;

    @Column(
            name = "deleted_user"
    )
    private Boolean isDeleted;

    @Setter(AccessLevel.PRIVATE)
    @CreationTimestamp
    @Column(
            name = "registered_at"
    )
    private LocalDateTime registeredAt;

    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "tb_users_roles",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role")
    )
    private Set<RoleEntity> roles;

    public UserEntity(String name, String nickname, String email, String password) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }
}
