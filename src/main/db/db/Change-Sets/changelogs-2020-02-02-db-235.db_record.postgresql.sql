
CREATE TABLE db.db_record (
  id                UUID  PRIMARY KEY            NOT NULL  DEFAULT pg_catalog.uuid_generate_v4(),

  db_user_login_id  UUID                         NOT NULL,
                    CONSTRAINT FK_db_news_entry_need_user_login_43a9
                    FOREIGN KEY (db_user_login_id)
                    REFERENCES  db.db_user_login (id)
                    ON DELETE CASCADE ON UPDATE CASCADE,

  create_date_time  TIMESTAMP WITHOUT TIME ZONE  NOT NULL  DEFAULT now(),
  edit_date_time    TIMESTAMP WITHOUT TIME ZONE  NOT NULL  DEFAULT now(),
  "index"           INTEGER                      NOT NULL  DEFAULT 1,
  type              VARCHAR(255)                 NOT NULL
);
