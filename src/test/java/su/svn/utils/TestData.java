/*
 * This file was last modified at 2020.03.15 12:34 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TestData.java
 * $Id$
 */

package su.svn.utils;

import su.svn.shared.Constants;
import su.svn.showcase.domain.*;
import su.svn.showcase.dto.RecordDto;
import su.svn.showcase.dto.TagBaseDto;
import su.svn.showcase.dto.TagFullDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class TestData {
    public static final LocalDateTime NOW = LocalDateTime.now();

    public static final String ID0 = Constants.Types.String.ZERO;
    public static final String ID1 = "0000000000000001";

    public static final UUID UUID0 = Constants.Types.UUID.ZERO;
    public static final UUID UUID1 = UUID.fromString("00000000-0000-0000-0000-000000000001");

    public static final Set<Tag> EMPTY_TAGS = Collections.emptySet();
    public static final Set<Record> EMPTY_RECORDS = Collections.emptySet();

    public static final Set<TagBaseDto> EMPTY_BASEDTO_TAGS = Collections.emptySet();
    public static final Set<TagFullDto> EMPTY_FULLDTO_TAGS = Collections.emptySet();
    public static final Set<RecordDto> EMPTY_RECORD_DTOS = Collections.emptySet();

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

    public static <T extends Serializable> T assertClone(T o) {
        T c = SerializeUtil.clone(o);
        assert c != null;
        return c;
    }
}
//EOF
