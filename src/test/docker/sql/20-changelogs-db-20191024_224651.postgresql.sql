--liquibase formatted sql

--changeset vnsk:1571946414226-1
CREATE TABLE db.db_news_entry (id UUID DEFAULT uuid_generate_v4() NOT NULL, db_user_login_id UUID NOT NULL, db_news_group_id UUID, create_date_time TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL, edit_date_time TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL, index INTEGER DEFAULT 1 NOT NULL, title VARCHAR(128) NOT NULL, content VARCHAR(1024), CONSTRAINT db_news_entry_pkey PRIMARY KEY (id));

--changeset vnsk:1571946414226-2
CREATE TABLE db.db_news_group (id UUID DEFAULT uuid_generate_v4() NOT NULL, date_time TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL, "group" VARCHAR(64) NOT NULL, CONSTRAINT db_news_group_pkey PRIMARY KEY (id));

--changeset vnsk:1571946414226-3
CREATE TABLE db.db_news_tag (id UUID DEFAULT uuid_generate_v4() NOT NULL, db_news_entry_id UUID NOT NULL, tag_id UUID NOT NULL, date_time TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL, CONSTRAINT db_news_tag_pkey PRIMARY KEY (id));

--changeset vnsk:1571946414226-4
CREATE TABLE db.db_role (id UUID DEFAULT uuid_generate_v4() NOT NULL, role_name VARCHAR(32) NOT NULL, CONSTRAINT db_role_pkey PRIMARY KEY (id));

--changeset vnsk:1571946414226-5
CREATE TABLE db.db_user_login (id UUID DEFAULT uuid_generate_v4() NOT NULL, date_time TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL, login VARCHAR(64) NOT NULL, password VARCHAR(256) NOT NULL, CONSTRAINT db_user_login_pkey PRIMARY KEY (id));

--changeset vnsk:1571946414226-6
CREATE TABLE db.db_user_role (id UUID DEFAULT uuid_generate_v4() NOT NULL, db_user_login_id UUID NOT NULL, date_time TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL, role_name VARCHAR(32) NOT NULL, CONSTRAINT db_user_role_pkey PRIMARY KEY (id));

--changeset vnsk:1571946414226-7
ALTER TABLE db.db_role ADD CONSTRAINT ucac1d_db_id_role_name_must_be_unique UNIQUE (id, role_name);

--changeset vnsk:1571946414226-8
ALTER TABLE db.db_news_tag ADD CONSTRAINT ucad1a_db_news_entry_tag_must_be_different UNIQUE (db_news_entry_id, tag_id);

--changeset vnsk:1571946414226-9
ALTER TABLE db.db_user_login ADD CONSTRAINT ucb99a_db_user_login_must_be_different UNIQUE (login);

--changeset vnsk:1571946414226-10
ALTER TABLE db.db_user_role ADD CONSTRAINT ucba0c_db_user_role_must_be_different UNIQUE (db_user_login_id, role_name);

--changeset vnsk:1571946414226-11
ALTER TABLE db.db_role ADD CONSTRAINT ucce6b_db_role_name_must_be_unique UNIQUE (role_name);

--changeset vnsk:1571946414226-12
ALTER TABLE db.db_news_group ADD CONSTRAINT uccead_db_news_group_must_be_different UNIQUE ("group");

--changeset vnsk:1571946414226-13
ALTER TABLE db.db_news_tag ADD CONSTRAINT fka43e_db_news_tag_need_news_entry FOREIGN KEY (db_news_entry_id) REFERENCES db.db_news_entry (id) ON UPDATE CASCADE ON DELETE CASCADE;

--changeset vnsk:1571946414226-14
ALTER TABLE db.db_user_role ADD CONSTRAINT fka5cd_db_user_role_need_user_login FOREIGN KEY (id, role_name) REFERENCES db.db_role (id, role_name) ON UPDATE CASCADE ON DELETE CASCADE;

--changeset vnsk:1571946414226-15
ALTER TABLE db.db_user_role ADD CONSTRAINT fka63c_db_user_role_need_user_login FOREIGN KEY (db_user_login_id) REFERENCES db.db_user_login (id) ON UPDATE CASCADE ON DELETE CASCADE;

--changeset vnsk:1571946414226-16
ALTER TABLE db.db_news_entry ADD CONSTRAINT fkb33e_db_news_entry_need_news_group FOREIGN KEY (db_news_group_id) REFERENCES db.db_news_group (id) ON UPDATE CASCADE ON DELETE CASCADE;

--changeset vnsk:1571946414226-17
ALTER TABLE db.db_news_tag ADD CONSTRAINT fkb71f_db_news_tag_need_dictionary_tag FOREIGN KEY (tag_id) REFERENCES dictionary.tag (id) ON UPDATE CASCADE ON DELETE CASCADE;

--changeset vnsk:1571946414226-18
ALTER TABLE db.db_news_entry ADD CONSTRAINT fkb99a_db_news_entry_need_user_login FOREIGN KEY (db_user_login_id) REFERENCES db.db_user_login (id) ON UPDATE CASCADE ON DELETE CASCADE;
