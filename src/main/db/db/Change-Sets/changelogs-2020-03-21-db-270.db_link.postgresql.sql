
CREATE TABLE db.db_link (
  id         UUID  PRIMARY KEY            NOT NULL  DEFAULT pg_catalog.uuid_generate_v4(),
  date_time  TIMESTAMP WITHOUT TIME ZONE  NOT NULL  DEFAULT now(),
  visible    BOOLEAN                                DEFAULT false,
  link       VARCHAR(512)                 NOT NULL
);
