/*
 * This file was last modified at 2020.04.01 22:50 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordBaseConverterImplTest.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.jboss.weld.junit5.auto.AddPackages;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.domain.Record;
import su.svn.showcase.dto.RecordFullDto;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.InjectionPoint;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static su.svn.showcase.domain.TestData.cloneRecord0;
import static su.svn.showcase.dto.TestData.cloneRecordFullDto0;

@DisplayName("A RecordBaseConverterImplTest unit test cases")
@AddPackages(value = {RecordConverter.class, RecordBaseConverterImpl.class})
@ExtendWith({WeldJunit5Extension.class})
class RecordBaseConverterImplTest {

    static RecordConverter recordBaseConverter = new RecordBaseConverterImpl();

    private Map<String, Object> ejbMap = new HashMap<String, Object>() {{
        put("RecordBaseConverter", recordBaseConverter);
    }};

    private Function<InjectionPoint, Object> ejbFactory() {
        return ip -> ejbMap.get(ip.getAnnotated().getAnnotation(EJB.class).beanName());
    }

    @WeldSetup
    private
    WeldInitiator weld = WeldInitiator.from(
            RecordBaseConverterImpl.class)
            .activate(RequestScoped.class)
            .setEjbFactory(ejbFactory())
            .inject(this)
            .build();

    @EJB(beanName = "RecordBaseConverter")
    RecordConverter converter;

    private Record entity;
    private RecordFullDto dto;

    @BeforeEach
    void setUp() {
        entity = cloneRecord0();
        dto = cloneRecordFullDto0();
    }

    private Record clean(Record entity) {
        entity.setArticle(null);
        entity.setNewsEntry(null);
        entity.setNewsLinks(null);
        entity.setUserLogin(null);
        entity.setTags(null);

        return entity;
    }

    private RecordFullDto clean(RecordFullDto dto) {
        dto.setArticle(null);
        dto.setNewsEntry(null);
        dto.setNewsLinks(null);
        dto.setUserLogin(null);
        dto.setTags(null);

        return dto;
    }

    @Test
    void when_convert_Entity_to_DTO() {
        Assertions.assertNotNull(converter);
        RecordFullDto expected = clean(dto);
        RecordFullDto test = converter.convert(entity);
        Assertions.assertEquals(expected, test);
    }

    @Test
    void when_convert_DTO_to_Entity() {
        Assertions.assertNotNull(converter);
        Record expected = clean(entity);
        Record test = converter.convert(dto);
        Assertions.assertEquals(expected, test);
    }
}