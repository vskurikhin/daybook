/*
 * This file was last modified at 2020.02.10 22:10 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Records.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The base DTO of Records.
 *
 * @author Victor N. Skurikhin
 */
@EqualsAndHashCode
@ToString
public class Records implements Serializable {

    private static final long serialVersionUID = 9991L;

    private static final Set NEWS_ENTRY_SET = Collections.unmodifiableSet(
            new HashSet<String>() {
                private static final long serialVersionUID = 1L;
                {
                    add(NewsEntryBaseDto.class.getSimpleName().toUpperCase());
                    add(NewsEntryFullDto.class.getSimpleName().toLowerCase());
                }
            });

    @Getter
    @NotNull
    private final RecordDto record;

    @Getter
    @NotNull
    private NewsEntryDto newsEntry;

    @Getter
    private String type;

    public Records(@NotNull RecordDto record) {
        this.record = record;
    }

    public Records(@NotNull RecordDto record, @NotNull NewsEntryDto newsEntry) {
        this.record = record;
        this.newsEntry = newsEntry;
        type = NewsEntryFullDto.class.getTypeName();
    }

    public boolean isTypeOfNewsEntry() {
        return NEWS_ENTRY_SET.contains(String.valueOf(type).toUpperCase());
    }

    public void setNewsEntry(@NotNull NewsEntryDto newsEntry) {
        this.newsEntry = newsEntry;
        type = newsEntry.getDtoClass().getSimpleName();
    }
}
//EOF
