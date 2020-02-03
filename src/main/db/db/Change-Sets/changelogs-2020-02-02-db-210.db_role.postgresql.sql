
CREATE TABLE db.db_role (
  id         UUID  PRIMARY KEY  NOT NULL  DEFAULT pg_catalog.uuid_generate_v4(),
  role_name  VARCHAR(32)        NOT NULL
             CONSTRAINT UC_db_role_name_must_be_unique_31e0
             UNIQUE,

             CONSTRAINT UC_db_id_role_name_must_be_unique_77db
             UNIQUE (id, role_name)
);
