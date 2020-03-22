CREATE SCHEMA IF NOT EXISTS db;
CREATE SCHEMA IF NOT EXISTS dictionary;

CREATE TABLE db.db_user_login (
  id                    UUID DEFAULT RANDOM_UUID()  NOT NULL  PRIMARY KEY,
  date_time             TIMESTAMP WITHOUT TIME ZONE NOT NULL  DEFAULT now(),
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

CREATE TABLE db.db_news_links (
  id                    UUID DEFAULT RANDOM_UUID()  NOT NULL  PRIMARY KEY,
                        FOREIGN KEY (id)
                        REFERENCES  db.db_record (id),

  db_news_group_id      UUID,
                        FOREIGN KEY (db_news_group_id)
                        REFERENCES  db.db_news_group (id),

  date_time             TIMESTAMP WITHOUT TIME ZONE  NOT NULL  DEFAULT now(),
  title                 VARCHAR(128)                 NOT NULL
);

CREATE TABLE db.db_link (
  id         UUID DEFAULT RANDOM_UUID() NOT NULL  PRIMARY KEY,
  date_time  TIMESTAMP WITHOUT TIME ZONE  NOT NULL  DEFAULT now(),
  visible    BOOLEAN                                DEFAULT false,
  link       VARCHAR(512)                 NOT NULL
             CONSTRAINT UC_link_must_be_different_4fc8
             UNIQUE
);

CREATE TABLE db.db_link_description (
  id               UUID DEFAULT RANDOM_UUID() NOT NULL  PRIMARY KEY,

  db_news_links_id UUID                         NOT NULL,
                   CONSTRAINT FK_db_link_description_need_news_links_2044
                   FOREIGN KEY (db_news_links_id)
                   REFERENCES  db.db_news_links (id),

  db_link_id       UUID                         NOT NULL,
                   CONSTRAINT FK_db_link_description_need_link_4549
                   FOREIGN KEY (db_link_id)
                   REFERENCES  db.db_link (id),

  date_time        TIMESTAMP WITHOUT TIME ZONE  NOT NULL DEFAULT now(),
  description      VARCHAR(128)                 NOT NULL,
  details          VARCHAR(8192),

                   CONSTRAINT UC_db_link_description_must_be_different_9745
                   UNIQUE (db_news_links_id, db_link_id)
);

CREATE TABLE db.db_article (
    id          UUID DEFAULT RANDOM_UUID() NOT NULL  PRIMARY KEY,
                CONSTRAINT FK_db_article_need_record_4021
                FOREIGN KEY (id)
                REFERENCES  db.db_record (id),

    date_time   TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    title       VARCHAR(128)      NOT NULL,
    include     VARCHAR(128)      NOT NULL,
    summary     TEXT
);
