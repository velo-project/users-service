package com.github.veloproject.userservices.adapters.dao.tables.mappers;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PUBLIC)
@Builder
@Entity
@Table(name = "tb_users")
public class UserTableRowMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long userId;

    @Column(
            name = "user_fullname",
            length = 100
    )
    private String fullName;

    @Column(
            name = "user_preferredname",
            length = 50
    )
    private String preferredName;

    @Column(
            name = "user_email",
            nullable = false,
            length = 100,
            unique = true
    )
    private String email;

    @Column(
            name = "user_hashpassword",
            nullable = false,
            length = 60
    )
    private String hashPassword;
}
