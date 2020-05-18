INSERT INTO db.db_user_login (id, login, password) VALUES ('00000000-0000-0000-0000-000000000010', 'loginTest10', 'passwordTest10');

INSERT INTO db.db_record (id, db_user_login_id, type) VALUES ('00000000-0000-0000-0000-000000000010', '00000000-0000-0000-0000-000000000010', 'ArticleJdo');
INSERT INTO db.db_link (id, link) VALUES ('00000000-0000-0000-0000-000000000010', 'titleLink10');
INSERT INTO db.db_article (id, title, include, anchor) VALUES ('00000000-0000-0000-0000-000000000010', 'titleTest10', 'includeTest10', 'anchorTest10');

INSERT INTO db.db_news_group (id, "group") VALUES ('00000000-0000-0000-0000-000000000010', 'groupTest11');

INSERT INTO db.db_record (id, db_user_login_id, type) VALUES ('00000000-0000-0000-0000-000000000011', '00000000-0000-0000-0000-000000000010', 'NewsEntryJdo');
INSERT INTO db.db_news_entry (id, db_news_group_id, title, content) VALUES ('00000000-0000-0000-0000-000000000011', '00000000-0000-0000-0000-000000000010', 'titleTest11', 'contentTest11');

INSERT INTO db.db_record (id, db_user_login_id, type) VALUES ('00000000-0000-0000-0000-000000000012', '00000000-0000-0000-0000-000000000010', 'NewsLinksJdo');
INSERT INTO db.db_news_links (id, db_news_group_id, title) VALUES ('00000000-0000-0000-0000-000000000012', '00000000-0000-0000-0000-000000000010', 'titleTest11');
