ALTER TABLE tb_users
    ALTER COLUMN nickname_user SET NOT NULL;

ALTER TABLE tb_users
    ALTER COLUMN description_user TYPE VARCHAR(255);