CREATE SEQUENCE dictionary.vocabulary_seq START 1;

CREATE FUNCTION dictionary.next_val_vocabulary_seq() RETURNS TEXT
    IMMUTABLE LANGUAGE SQL
    AS $$ SELECT lpad(to_hex(nextval('vocabulary_seq')), 16, '0') $$;

CREATE TABLE dictionary.vocabulary (
  id                    CHAR(16)  PRIMARY KEY  NOT NULL  DEFAULT dictionary.next_val_vocabulary_seq(),
  words_id              BIGINT                 NOT NULL,
                        CONSTRAINT FK_db_news_tag_need_dictionary_tag_80e4
                        FOREIGN KEY (words_id)
                        REFERENCES  dictionary.words (id)
                        ON DELETE CASCADE ON UPDATE CASCADE,

  value                 TEXT
);
