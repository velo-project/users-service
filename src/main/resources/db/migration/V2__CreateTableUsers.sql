CREATE TABLE tb_users (
    id_user SERIAL PRIMARY KEY,
    name_user VARCHAR(100) NOT NULL,
    email_user VARCHAR(60) NOT NULL,
    password_user VARCHAR(60) NOT NULL,
    nickname_user VARCHAR(20),
    banner_photo_url_user TEXT,
    profile_photo_url_user TEXT,
    description_user TEXT,
    blocked_user BOOLEAN,
    registered_at TIMESTAMP WITHOUT TIME ZONE
);