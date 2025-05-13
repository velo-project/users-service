package com.github.veloproject.userservices.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "tb_roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Integer id;

    @Column(name = "name_role")
    private String name;

    @Getter
    public enum Values {
        USER(1),
        ADMIN(2);

        final Integer value;
        Values(Integer value) {
            this.value = value;
        }
    }
}
