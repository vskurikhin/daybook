
CREATE TABLE db.db_news_group (
  id         UUID  PRIMARY KEY            NOT NULL  DEFAULT pg_catalog.uuid_generate_v4(),
  date_time  TIMESTAMP WITHOUT TIME ZONE  NOT NULL  DEFAULT now(),
  "group"    VARCHAR(64)                  NOT NULL
             CONSTRAINT UC_db_news_group_must_be_different_47b0
             UNIQUE
);
