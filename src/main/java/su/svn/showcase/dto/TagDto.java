/*
 * This file was last modified at 2020.03.01 00:04 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.interfaces.Updating;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * The DTO of Tag is interface as a contract.
 *
 * @author Victor N. Skurikhin
 */
public interface TagDto extends Dto<String>, Updating<Tag> {

    String getTag();

    void setTag(String tag);

    Boolean getVisible();

    void setVisible(Boolean visible);

    LocalDateTime getDateTime();

    void setDateTime(LocalDateTime dateTime);

    default Tag update(@Nonnull Tag entity, @Nonnull Map<String, Object> values) {
        convertSetIfContainsKey(Record.class, values, "records").ifPresent(entity::setRecords);
        return update(entity);
    }
}
//EOF
