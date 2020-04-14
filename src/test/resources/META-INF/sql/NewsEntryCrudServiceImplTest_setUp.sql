INSERT INTO db.db_user_login (id, login, password) VALUES ('00000000-0000-0000-0000-000000000001', 'loginTest1', 'passwordTest1');
INSERT INTO db.db_user_login (id, login, password) VALUES ('00000000-0000-0000-0000-000000000010', 'loginTest10', 'passwordTest10');
INSERT INTO db.db_record (id, db_user_login_id, type) VALUES ('00000000-0000-0000-0000-000000000010', '00000000-0000-0000-0000-000000000010', 'NewsEntryJdo');
INSERT INTO db.db_news_group (id, "group") VALUES ('00000000-0000-0000-0000-000000000010', 'groupTest10');
INSERT INTO db.db_news_entry (id, db_news_group_id, title, content) VALUES ('00000000-0000-0000-0000-000000000010', '00000000-0000-0000-0000-000000000010', 'titleTest10', 'contentTest10');
