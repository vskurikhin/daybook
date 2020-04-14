/*
 * This file was last modified at 2020.04.14 16:50 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordDaoJpaTest_setUp.sql
 * $Id$
 */

INSERT INTO db.db_user_login (id, login, password) VALUES ('00000000-0000-0000-0000-000000000010', 'loginTest10', 'passwordTest10');
INSERT INTO db.db_record (id, db_user_login_id, type) VALUES ('00000000-0000-0000-0000-000000000010', '00000000-0000-0000-0000-000000000010', 'NewsEntryJdo');
