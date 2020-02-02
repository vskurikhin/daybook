
CREATE TABLE db.db_user_role (
  id                UUID PRIMARY KEY             NOT NULL  DEFAULT pg_catalog.uuid_generate_v4(),
  db_user_login_id  UUID                         NOT NULL,

                    CONSTRAINT FK_db_user_role_need_user_login_56a0
                    FOREIGN KEY (db_user_login_id)
                    REFERENCES  db.db_user_login (id)
                    ON DELETE CASCADE ON UPDATE CASCADE,

  date_time         TIMESTAMP WITHOUT TIME ZONE  NOT NULL  DEFAULT now(),
  role_name         VARCHAR(32)                  NOT NULL,

                    CONSTRAINT UC_db_user_role_must_be_different_40cf
                    UNIQUE (db_user_login_id, role_name),

                    CONSTRAINT FK_db_user_role_need_user_login_41c5
                    FOREIGN KEY (id, role_name)
                    REFERENCES  db.db_role (id, role_name)
                    ON DELETE CASCADE ON UPDATE CASCADE
);
