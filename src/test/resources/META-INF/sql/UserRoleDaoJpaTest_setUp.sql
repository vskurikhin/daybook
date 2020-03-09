INSERT INTO db.db_role (id, role_name) VALUES ('00000000-0000-0000-0000-000000000010', 'testRole10');
INSERT INTO db.db_user_login (id, login, password) VALUES ('00000000-0000-0000-0000-000000000010', 'loginTest10', 'passwordTest10');
INSERT INTO db.db_user_role (id, db_user_login_id, role_name) VALUES ('00000000-0000-0000-0000-000000000010', '00000000-0000-0000-0000-000000000010', 'testRole10');
