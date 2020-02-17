/*
 * This file was last modified at 2020.02.16 11:23 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TestData.java
 * $Id$
 */

package su.svn.it;

import su.svn.shared.Constants;
import su.svn.showcase.domain.*;
import su.svn.showcase.dto.RecordDto;
import su.svn.showcase.dto.TagBaseDto;
import su.svn.showcase.dto.TagFullDto;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class TestData {
    public static final LocalDateTime NOW = LocalDateTime.now();

    public static final String ID0 = "00000000" + "00000000";
    public static final String ID1 = "00000000" + "00000001";
    public static final String ID2 = "00000000" + "00000002";
    public static final String ID3 = "00000000" + "00000003";

    public static final UUID UUID0 = new java.util.UUID(0, 0);
    public static final UUID UUID1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
    public static final UUID UUID2 = UUID.fromString("00000000-0000-0000-0000-000000000002");
    public static final UUID UUID3 = UUID.fromString("00000000-0000-0000-0000-000000000003");

    public static final Set<Tag> EMPTY_TAGS = Collections.emptySet();
    public static final Set<Record> EMPTY_RECORDS = Collections.emptySet();

    public static final Set<TagBaseDto> EMPTY_BASEDTO_TAGS = Collections.emptySet();
    public static final Set<TagFullDto> EMPTY_FULLDTO_TAGS = Collections.emptySet();
    public static final Set<RecordDto> EMPTY_RECORD_DTOS = Collections.emptySet();

    public static final List<UserRole> EMPTY_USER_ROLES = Collections.emptyList();
    public static final List<NewsEntry> EMPTY_NEWS_ENTRIES = Collections.emptyList();

    public static final UUID ROLE_UUID0 = UUID0;
    public static final UUID ROLE_UUID1 = UUID1;
    public static final UUID ROLE_UUID2 = UUID2;
    public static final UUID ROLE_UUID3 = UUID3;

    public static final UUID USER_ROLE_UUID0 = UUID0;
    public static final UUID USER_ROLE_UUID1 = UUID1;
    public static final UUID USER_ROLE_UUID2 = UUID2;
    public static final UUID USER_ROLE_UUID3 = UUID3;

    public static final UUID USER_LOGIN_UUID0 = UUID0;
    public static final UUID USER_LOGIN_UUID1 = UUID1;
    public static final UUID USER_LOGIN_UUID2 = UUID2;
    public static final UUID USER_LOGIN_UUID3 = UUID3;

    public static final String TAG_ID0 = ID0;
    public static final String TAG_ID1 = ID1;
    public static final String TAG_ID2 = ID2;
    public static final String TAG_ID3 = ID3;

    public static final UUID NEWS_ENTRY_UUID0 = UUID0;
    public static final UUID NEWS_ENTRY_UUID1 = UUID1;
    public static final UUID NEWS_ENTRY_UUID2 = UUID2;
    public static final UUID NEWS_ENTRY_UUID3 = UUID3;

    public static final UUID NEWS_GROUP_UUID0 = UUID0;
    public static final UUID NEWS_GROUP_UUID1 = UUID1;

    public static final UUID RECORD_UUID0 = NEWS_ENTRY_UUID0;
    public static final UUID RECORD_UUID1 = NEWS_ENTRY_UUID1;
    public static final UUID RECORD_UUID2 = NEWS_ENTRY_UUID2;
    public static final UUID RECORD_UUID3 = NEWS_ENTRY_UUID3;

    public static <T> List<T> newList(T o) {
        return new ArrayList<T>(){{add(o);}};
    }
    public static <T> Set<T> newSet(T o) {
        return new HashSet<T>(){{add(o);}};
    }

    public static <T extends Serializable> T clone(T o) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // сохраняем состояние в поток и закрываем поток
            try (ObjectOutputStream ous = new ObjectOutputStream(baos)) {
                ous.writeObject(o);
            } catch (IOException e) {
                throw e;
            }
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            // создаём и инициализируем состояние
            //noinspection unchecked
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
//EOF
