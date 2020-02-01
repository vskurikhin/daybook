CREATE SEQUENCE dictionary.tag_seq START 1;

CREATE FUNCTION dictionary.next_val_tag_seq() RETURNS TEXT
    IMMUTABLE LANGUAGE SQL
    AS $$ SELECT lpad(to_hex(nextval('tag_seq')), 16, '0') $$;

CREATE TABLE dictionary.tag (
  id                    CHAR(16)   PRIMARY KEY        NOT NULL  DEFAULT dictionary.next_val_tag_seq(),
  date_time             TIMESTAMP  WITHOUT TIME ZONE  NOT NULL  DEFAULT now(),
  visible               BOOLEAN                                 DEFAULT false,
  tag                   VARCHAR(128)                  NOT NULL
                        CONSTRAINT UC_dictionary_tag_must_be_different_85b5
                        UNIQUE
);
