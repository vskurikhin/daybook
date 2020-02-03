--liquibase formatted sql

--changeset vnsk:1571946412689-1
CREATE TABLE dictionary.codifier (id UUID DEFAULT uuid_generate_v4() NOT NULL, code VARCHAR(64), value TEXT, CONSTRAINT codifier_pkey PRIMARY KEY (id));

--changeset vnsk:1571946412689-2
CREATE TABLE dictionary.tag (id UUID DEFAULT uuid_generate_v4() NOT NULL, date_time TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL, visible BOOLEAN DEFAULT FALSE, tag VARCHAR(128) NOT NULL, CONSTRAINT tag_pkey PRIMARY KEY (id));

--changeset vnsk:1571946412689-3
CREATE TABLE dictionary.vocabulary (id UUID DEFAULT uuid_generate_v4() NOT NULL, word VARCHAR(256) NOT NULL, value TEXT, CONSTRAINT vocabulary_pkey PRIMARY KEY (id));

--changeset vnsk:1571946412689-4
ALTER TABLE dictionary.codifier ADD CONSTRAINT ucb99a_dictionary_code_must_be_different UNIQUE (code);

--changeset vnsk:1571946412689-5
ALTER TABLE dictionary.tag ADD CONSTRAINT ucb99a_dictionary_tag_must_be_different UNIQUE (tag);

--changeset vnsk:1571946412689-6
ALTER TABLE dictionary.vocabulary ADD CONSTRAINT ucb99a_dictionary_word_must_be_different UNIQUE (word);

