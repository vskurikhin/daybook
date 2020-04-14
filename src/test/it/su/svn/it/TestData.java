/*
 * This file was last modified at 2020.04.14 20:47 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TestData.java
 * $Id$
 */

package su.svn.it;

import su.svn.showcase.domain.*;
import su.svn.showcase.dto.RecordDto;
import su.svn.showcase.dto.jdo.TagJdo;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class TestData {
    public static final LocalDateTime NOW = LocalDateTime.now();

    public static final String SID0 = "00000000" + "00000000";
    public static final String SID1 = "00000000" + "00000001";
    public static final String SID2 = "00000000" + "00000002";
    public static final String SID3 = "00000000" + "00000003";

    public static final UUID UUID0 = new java.util.UUID(0, 0);
    public static final UUID UUID1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
    public static final UUID UUID2 = UUID.fromString("00000000-0000-0000-0000-000000000002");
    public static final UUID UUID3 = UUID.fromString("00000000-0000-0000-0000-000000000003");
    public static final UUID UUID4 = UUID.fromString("00000000-0000-0000-0000-000000000003");
    public static final UUID UUID5 = UUID.fromString("00000000-0000-0000-0000-000000000003");
    public static final UUID UUID6 = UUID.fromString("00000000-0000-0000-0000-000000000003");
    public static final UUID UUID7 = UUID.fromString("00000000-0000-0000-0000-000000000003");

    public static final Set<Tag> EMPTY_TAGS = Collections.emptySet();
    public static final Set<Record> EMPTY_RECORDS = Collections.emptySet();

    public static final Set<TagJdo> EMPTY_FULLDTO_TAGS = Collections.emptySet();
    public static final Set<RecordDto> EMPTY_RECORD_DTOS = Collections.emptySet();

    public static final List<UserRole> EMPTY_USER_ROLES = Collections.emptyList();
    public static final List<NewsEntry> EMPTY_NEWS_ENTRIES = Collections.emptyList();

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
