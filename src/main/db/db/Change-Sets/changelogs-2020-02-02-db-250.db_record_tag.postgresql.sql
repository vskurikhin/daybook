
CREATE TABLE db.db_record_tag (
  id            UUID PRIMARY KEY            NOT NULL DEFAULT pg_catalog.uuid_generate_v4(),

  db_record_id  UUID                        NOT NULL,
                CONSTRAINT FK_db_tag_need_record_40b3
                FOREIGN KEY (db_record_id)
                REFERENCES  db.db_record (id)
                ON DELETE CASCADE ON UPDATE CASCADE,

  tag_id        CHAR(16)                    NOT NULL,
                CONSTRAINT FK_db_tag_need_dictionary_tag_47d3
                FOREIGN KEY (tag_id)
                REFERENCES  dictionary.tag (id)
                ON DELETE CASCADE ON UPDATE CASCADE,

  date_time     TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),

                CONSTRAINT UC_db_news_entry_tag_must_be_different_49f9
                UNIQUE (db_record_id, tag_id)
);
