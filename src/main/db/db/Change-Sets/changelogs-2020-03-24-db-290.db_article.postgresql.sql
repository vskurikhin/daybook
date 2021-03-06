CREATE TABLE db.db_article (
    id          UUID PRIMARY KEY  NOT NULL DEFAULT pg_catalog.uuid_generate_v4(),
                CONSTRAINT FK_db_article_need_record_4021
                FOREIGN KEY (id)
                REFERENCES db.db_record (id)
                ON DELETE CASCADE ON UPDATE CASCADE,

    date_time   TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    title       VARCHAR(128)      NOT NULL,
    include     VARCHAR(128)      NOT NULL,
    anchor      VARCHAR(128)      NOT NULL,
    summary     TEXT
);

