/*
 * This file was last modified at 2020.02.06 22:27 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TestData.java
 * $Id$
 */

package su.svn.utils;

import su.svn.showcase.domain.*;

import java.time.LocalDateTime;
import java.util.*;


public class TestData {
    public static final LocalDateTime NOW = LocalDateTime.now();

    public static final String ID0 = StringEntity.ZERO;
    public static final String ID1 = "0000000000000001";

    public static final UUID UUID0 = UUIDEntity.ZERO;
    public static final UUID UUID1 = UUID.fromString("00000000-0000-0000-0000-000000000001");

    public static final Set<Tag> EMPTY_TAGS = Collections.emptySet();
    public static final Set<Record> EMPTY_RECORDS = Collections.emptySet();

    public static final List<UserRole> EMPTY_USER_ROLES = Collections.emptyList();
    public static final List<NewsEntry> EMPTY_NEWS_ENTRIES = Collections.emptyList();

    public static final UUID ROLE_UUID0 = UUID0;
    public static final UUID ROLE_UUID1 = UUID1;

    public static final UUID USER_ROLE_UUID0 = UUID0;
    public static final UUID USER_ROLE_UUID1 = UUID1;

    public static final UUID USER_LOGIN_UUID0 = UUID0;
    public static final UUID USER_LOGIN_UUID1 = UUID1;

    public static final String TAG_ID0 = ID0;
    public static final String TAG_ID1 = ID1;

    public static final UUID NEWS_ENTRY_UUID0 = UUID0;
    public static final UUID NEWS_ENTRY_UUID1 = UUID1;

    public static final UUID NEWS_GROUP_UUID0 = UUID0;
    public static final UUID NEWS_GROUP_UUID1 = UUID1;

    public static final UUID RECORD_UUID0 = NEWS_ENTRY_UUID0;
    public static final UUID RECORD_UUID1 = NEWS_ENTRY_UUID1;

    public static <T> List<T> newList(T o) {
        return new ArrayList<T>(){{add(o);}};
    }
    public static <T> Set<T> newSet(T o) {
        return new HashSet<T>(){{add(o);}};
    }
}
//EOF
