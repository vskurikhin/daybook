CREATE SCHEMA IF NOT EXISTS db;
CREATE SCHEMA IF NOT EXISTS dictionary;

CREATE TABLE db.db_user_login (
  id                    UUID DEFAULT RANDOM_UUID()  NOT NULL  PRIMARY KEY,
  date_time             TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  login                 VARCHAR(64)                 NOT NULL
                        UNIQUE,

  password              VARCHAR(256)                NOT NULL
);

CREATE TABLE db.db_role (
  id                    UUID DEFAULT RANDOM_UUID()  NOT NULL  PRIMARY KEY,
  role_name             VARCHAR(32)                 NOT NULL
                        UNIQUE,
                        UNIQUE (id, role_name)
);

CREATE TABLE db.db_user_role (
  id                    UUID DEFAULT RANDOM_UUID()  NOT NULL  PRIMARY KEY,
  db_user_login_id      UUID                        NOT NULL,
                        FOREIGN KEY (db_user_login_id)
                        REFERENCES  db.db_user_login (id),
  date_time             TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  role_name             VARCHAR(32)                 NOT NULL,
                        UNIQUE (db_user_login_id, role_name),
                        FOREIGN KEY (id, role_name)
                        REFERENCES  db.db_role (id, role_name)
);

CREATE TABLE db.db_news_group (
  id                    UUID DEFAULT RANDOM_UUID()  NOT NULL PRIMARY KEY,
  date_time             TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  "group"               VARCHAR(64)                 NOT NULL
                        UNIQUE
);

CREATE TABLE db.db_record (
  id                    UUID DEFAULT RANDOM_UUID()  NOT NULL  PRIMARY KEY,

  db_user_login_id      UUID                        NOT NULL,
                        FOREIGN KEY (db_user_login_id)
                        REFERENCES  db.db_user_login (id),

  create_date_time      TIMESTAMP WITHOUT TIME ZONE NOT NULL  DEFAULT now(),
  edit_date_time        TIMESTAMP WITHOUT TIME ZONE NOT NULL  DEFAULT now(),
  index                 INTEGER                     NOT NULL  DEFAULT 1,
  type                  VARCHAR(255)                NOT NULL
);

CREATE TABLE db.db_news_entry (
  id                    UUID DEFAULT RANDOM_UUID()  NOT NULL  PRIMARY KEY,
                        FOREIGN KEY (id)
                        REFERENCES  db.db_record (id),

  db_news_group_id      UUID,
                        FOREIGN KEY (db_news_group_id)
                        REFERENCES  db.db_news_group (id),

  date_time             TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  title                 VARCHAR(128)                NOT NULL,
  content               VARCHAR(1024)
);

CREATE TABLE dictionary.tag (
  id                    CHAR(16) PRIMARY KEY        NOT NULL,
  date_time             TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  visible               BOOLEAN                              DEFAULT false,
  tag                   VARCHAR(128)                NOT NULL
                        CONSTRAINT UCb99a_dictionary_tag_must_be_different
                        UNIQUE
);

CREATE TABLE db.db_record_tag (
  id            UUID DEFAULT RANDOM_UUID() NOT NULL  PRIMARY KEY,

  db_record_id  UUID                        NOT NULL,
                FOREIGN KEY (db_record_id)
                REFERENCES  db.db_record (id),

  tag_id        CHAR(16)                    NOT NULL,
                FOREIGN KEY (tag_id)
                REFERENCES  dictionary.tag (id),

  date_time     TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
                UNIQUE (db_record_id, tag_id)
);
