CREATE TABLE tb_roles (
    id_role INTEGER PRIMARY KEY,
    name_role VARCHAR(50) NOT NULL
);

CREATE TABLE tb_users_roles(
    id_user INTEGER NOT NULL,
    id_role INTEGER NOT NULL,
    CONSTRAINT pk_users_roles PRIMARY KEY (id_user, id_role),
    CONSTRAINT fk_users_roles_user FOREIGN KEY (id_user)
        REFERENCES tb_users (id_user) ON DELETE CASCADE,
    CONSTRAINT fk_users_roles_role FOREIGN KEY (id_role)
        REFERENCES tb_roles (id_role)
);