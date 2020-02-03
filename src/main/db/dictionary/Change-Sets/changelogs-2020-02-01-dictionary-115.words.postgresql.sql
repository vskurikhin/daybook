CREATE SEQUENCE dictionary.words_seq START 1;

CREATE TABLE dictionary.words (
  id                    BIGINT  PRIMARY KEY  NOT NULL  DEFAULT nextval('words_seq'),
  word                  VARCHAR(256)         NOT NULL
                        CONSTRAINT UC_dictionary_word_must_be_different_63ce
                        UNIQUE
);
