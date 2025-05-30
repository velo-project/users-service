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
    @Column(name = "id_user")
    private Integer id;

    @Column(name = "name_user")
    private String name;

    @Column(name = "email_user")
    private String email;

    @JsonIgnore
    @Column(name = "password_user")
    private String password;

    @Column(name = "nickname_user")
    private String prefferedName;

    @Column(name = "banner_photo_url_user")
    private String bannerPhotoUrl;

    @Column(name = "profile_photo_url_user")
    private String profilePhotoUrl;

    @Column(name = "description_user")
    private String description;

    @Column(name = "blocked_user")
    private Boolean isBlocked;

    @Setter(AccessLevel.PRIVATE)
    @CreationTimestamp
    @Column(name = "registered_at")
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

    public UserEntity(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
