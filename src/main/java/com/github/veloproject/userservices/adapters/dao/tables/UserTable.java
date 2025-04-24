package com.github.veloproject.userservices.adapters.dao.tables;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_users")
public class UserTable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long userId;

    @Column(
            name = "user_fullname",
            nullable = false,
            length = 100
    )
    private String fullName;

    @Column(
            name = "user_preferredname",
            nullable = false,
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
            name = "user_password",
            nullable = false,
            length = 60
    )
    private String password;
}
