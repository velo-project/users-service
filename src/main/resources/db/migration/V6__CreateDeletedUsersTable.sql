CREATE TABLE tb_deleted_users (
    user_id INTEGER NOT NULL,
    CONSTRAINT fk_deleted_user FOREIGN KEY (user_id) REFERENCES tb_users(id_user),
    CONSTRAINT pk_deleted_user PRIMARY KEY (user_id)
);