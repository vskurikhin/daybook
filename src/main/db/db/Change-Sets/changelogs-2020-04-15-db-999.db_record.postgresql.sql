/*
 * This file was last modified at 2020.07.09 14:59 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * changelogs-2020-04-15-db-999.db_record.postgresql.sql
 * $Id$
 */

UPDATE db.db_record SET type = 'ArticleJdo' WHERE type = 'ArticleFullDto';
UPDATE db.db_record SET type = 'NewsEntryJdo' WHERE type = 'NewsEntryFullDto';
