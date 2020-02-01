CREATE SEQUENCE dictionary.codifier_seq START 1;

CREATE FUNCTION dictionary.next_val_codifier_seq() RETURNS TEXT
    IMMUTABLE LANGUAGE SQL
    AS $$ SELECT lpad(to_hex(nextval('codifier_seq')), 16, '0') $$;

CREATE TABLE dictionary.codifier (
  id                    CHAR(16)  PRIMARY KEY  NOT NULL  DEFAULT dictionary.next_val_codifier_seq(),
  code                  VARCHAR(64)
                        CONSTRAINT UC_dictionary_code_must_be_different_81b0
                        UNIQUE,

  value                 TEXT
);
