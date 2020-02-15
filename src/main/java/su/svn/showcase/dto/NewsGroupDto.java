/*
 * This file was last modified at 2020.02.10 21:22 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.domain.NewsGroup;
import su.svn.showcase.interfaces.Updating;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The DTO of NewsGroup is interface as a contract.
 *
 * @author Victor N. Skurikhin
 */
public interface NewsGroupDto extends Dto<UUID>, Updating<NewsGroup> {

    LocalDateTime getDateTime();

    void setDateTime(LocalDateTime dateTime);

    String getGroup();

    void setGroup(String group);

    default NewsGroup update(@NotNull NewsGroup entity, Map<String, Object> values) {
        assert entity != null;
        convertListIfContainsKey(NewsEntry.class, values, "newsEntries").ifPresent(entity::setNewsEntries);
        return update(entity);
    }
}
//EOF
