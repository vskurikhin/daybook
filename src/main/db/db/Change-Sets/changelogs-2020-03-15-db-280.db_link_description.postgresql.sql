
CREATE TABLE db.db_link_description (
  id               UUID PRIMARY KEY             NOT NULL DEFAULT pg_catalog.uuid_generate_v4(),

  db_news_links_id UUID                         NOT NULL,
                   CONSTRAINT FK_db_link_description_need_news_links_2044
                   FOREIGN KEY (db_news_links_id)
                   REFERENCES  db.db_news_links (id)
                   ON DELETE CASCADE ON UPDATE CASCADE,

  db_link_id       UUID                         NOT NULL,
                   CONSTRAINT FK_db_link_description_need_link_4549
                   FOREIGN KEY (db_link_id)
                   REFERENCES  db.db_link (id)
                   ON DELETE CASCADE ON UPDATE CASCADE,

  date_time        TIMESTAMP WITHOUT TIME ZONE  NOT NULL DEFAULT now(),
  description      VARCHAR(128)                 NOT NULL,
  details          VARCHAR(8192),

                   CONSTRAINT UC_db_link_description_must_be_different_9745
                   UNIQUE (db_news_links_id, db_link_id)
);
