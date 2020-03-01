/*
 * This file was last modified at 2020.03.01 18:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * StringTagSetConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.dto.TagBaseDto;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class StringTagSetConverter {

    public static Set<TagBaseDto> map(String tags) {

        Set<String> setOfLabels = Arrays.stream(tags.split("\\|"))
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .collect(Collectors.toSet());
        return setOfLabels.stream()
                .map(StringTagSetConverter::constructTagBaseDto)
                .collect(Collectors.toSet());
    }

    private static TagBaseDto constructTagBaseDto(String tag) {
        return TagBaseDto.builder()
                .tag(tag)
                .build();
    }
}
//EOF
