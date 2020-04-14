/*
 * This file was last modified at 2020.04.14 22:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The DTO of Record is interface as a contract.
 *
 * @author Victor N. Skurikhin
 */
public interface RecordDto extends Dto<UUID> {

    LocalDateTime getCreateDateTime();

    void setCreateDateTime(LocalDateTime createDateTime);

    LocalDateTime getEditDateTime();

    void setEditDateTime(LocalDateTime editDateTime);

    Integer getIndex();

    void setIndex(Integer index);

    String getType();

    void setType(String type);
}
//EOF
