
CREATE TABLE db.db_user_login (
  id         UUID  PRIMARY KEY            NOT NULL  DEFAULT pg_catalog.uuid_generate_v4(),
  date_time  TIMESTAMP WITHOUT TIME ZONE  NOT NULL  DEFAULT now(),
  login      VARCHAR(64)                  NOT NULL
             CONSTRAINT UC_db_user_login_must_be_different_94b9
             UNIQUE,

  password   VARCHAR(256)                 NOT NULL
);
