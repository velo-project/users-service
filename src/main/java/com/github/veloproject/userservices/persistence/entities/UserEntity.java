package com.github.veloproject.userservices.persistence.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @Column(name = "name_user")
    private String name;

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

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;
}
