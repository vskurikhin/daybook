/*
 * This file was last modified at 2020.04.07 23:20 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleDaoJpaTest_setUp.sql
 * $Id$
 */

INSERT INTO db.db_user_login (id, login, password) VALUES ('00000000-0000-0000-0000-000000000010', 'loginTest10', 'passwordTest10');
INSERT INTO db.db_record (id, db_user_login_id, type) VALUES ('00000000-0000-0000-0000-000000000010', '00000000-0000-0000-0000-000000000010', 'ArticleFullDto');
INSERT INTO db.db_link (id, link) VALUES ('00000000-0000-0000-0000-000000000010', 'titleLink10');
INSERT INTO db.db_article (id, title, include, anchor) VALUES ('00000000-0000-0000-0000-000000000010', 'titleTest10', 'includeTest10', 'anchorTest10');
