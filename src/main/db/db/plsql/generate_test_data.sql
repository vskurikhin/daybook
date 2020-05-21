CREATE OR REPLACE FUNCTION generate_test_data(n INTEGER DEFAULT 10)
RETURNS VOID AS $$
    DECLARE rec1 RECORD;
    DECLARE rec2 RECORD;
    DECLARE rec3 RECORD;
BEGIN
    FOR rec1 IN SELECT uuid_generate_v4() AS id,
                      '57488328-ed26-41ec-9840-9c0150c33ee9'::UUID AS db_user_login_id,
                      now() AS create_date_time,
                      now() AS edit_date_time,
                      (random()*65536)::INT AS index,
                      CASE WHEN (random()*2000000) < 1000000
                           THEN 'NewsEntryJdo'
                           ELSE 'ArticleJdo'
                      END AS type
                 FROM generate_series(1, n)
    LOOP
        INSERT INTO db.db_record
             SELECT rec1.id, rec1.db_user_login_id, rec1.create_date_time, rec1.edit_date_time, rec1.index, rec1.type;
        SELECT word FROM dictionary.words WHERE id > (random()*104118) LIMIT 1 INTO rec3;
        CASE rec1.type
          WHEN 'NewsEntryJdo' THEN
            SELECT rec1.id AS id,
                   '075cf71f-a73b-470f-a78c-eda44d41ac66'::UUID AS db_news_group_id,
                   now() AS date_time,
                   rec3.word AS title,
                   NULL AS content
              INTO rec2;
            INSERT INTO db.db_news_entry
            SELECT rec2.id, rec2.db_news_group_id, rec2.date_time, rec2.title, rec2.content;
            -- RAISE NOTICE '1 % % %', rec1.id, rec1.type, rec2.title;
          WHEN 'ArticleJdo' THEN
            SELECT rec1.id AS id,
                   now() AS date_time,
                   rec3.word AS title,
                   'include-'||rec1.index||'-'||rec3.word AS include,
                   'anchor-'||rec1.index||'-'||rec3.word AS anchor,
                   NULL AS summary
              INTO rec2;
            INSERT INTO db.db_article
            SELECT rec2.id, rec2.date_time, rec2.title, rec2.include, rec2.anchor, rec2.summary;
            -- RAISE NOTICE '2 % % % %', rec1.id, rec1.type, rec2.include, rec2.anchor;
        END CASE;
    END LOOP;
END;
$$ LANGUAGE plpgsql;
