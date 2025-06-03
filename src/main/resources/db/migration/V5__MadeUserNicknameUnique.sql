ALTER TABLE tb_users
    ADD CONSTRAINT MadeUserEmailUnique UNIQUE (email_user);

ALTER TABLE tb_users
    ADD CONSTRAINT MadeUserNicknameUnique UNIQUE (nickname_user);