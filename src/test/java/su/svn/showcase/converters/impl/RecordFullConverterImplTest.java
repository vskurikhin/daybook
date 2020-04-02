/*
 * This file was last modified at 2020.04.02 18:19 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordFullConverterImplTest.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.jboss.weld.junit5.auto.AddPackages;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import su.svn.showcase.converters.*;
import su.svn.showcase.domain.Article;
import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.domain.NewsLinks;
import su.svn.showcase.domain.Record;
import su.svn.showcase.dto.ArticleFullDto;
import su.svn.showcase.dto.NewsEntryFullDto;
import su.svn.showcase.dto.NewsLinksFullDto;
import su.svn.showcase.dto.RecordFullDto;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.InjectionPoint;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static su.svn.showcase.domain.TestData.cloneNewsEntry0;
import static su.svn.showcase.domain.TestData.cloneRecord0;
import static su.svn.showcase.dto.TestData.*;
import static su.svn.utils.TestData.newSet;

@DisplayName("A RecordBaseConverterImplTest unit test cases")
@AddPackages(value = {RecordConverter.class, RecordFullConverterImpl.class})
@ExtendWith({WeldJunit5Extension.class})
class RecordFullConverterImplTest {

    static ArticleConverter articleFullConverter = new ArticleFullConverterImpl();
    static NewsEntryConverter newsEntryFullConverter = new NewsEntryFullConverterImpl();
    static NewsGroupConverter newsGroupBaseConverter = new NewsGroupBaseConverterImpl();
    static NewsLinksConverter newsLinksFullConverter = new NewsLinksFullConverterImpl();
    static RecordConverter recordFullConverter = new RecordFullConverterImpl();
    static TagConverter tagBaseConverter = new TagBaseConverterImpl();
    static UserLoginConverter userOnlyLoginConverter = new UserOnlyLoginConverterImpl();

    private Map<String, Object> ejbMap = new HashMap<String, Object>() {{
        put("articleFullConverter", articleFullConverter);
        put("newsEntryFullConverter", newsEntryFullConverter);
        put("newsGroupBaseConverter", newsGroupBaseConverter);
        put("newsLinksFullConverter", newsLinksFullConverter);
        put("recordFullConverter", recordFullConverter);
        put("tagBaseConverter", tagBaseConverter);
        put("userOnlyLoginConverter", userOnlyLoginConverter);
    }};

    private Function<InjectionPoint, Object> ejbFactory() {
        return ip -> {
            String name = ip.getAnnotated().getAnnotation(EJB.class).beanName();
            System.err.println("beanName: " + name);
            return ejbMap.get(name);
        };
    }

    @WeldSetup
    private
    WeldInitiator weld = WeldInitiator.from(
            ArticleFullConverterImpl.class,
            LinkBaseConverterImpl.class,
            LinkDescriptionBaseConverterImpl.class,
            NewsEntryFullConverterImpl.class,
            NewsGroupBaseConverterImpl.class,
            NewsLinksFullConverterImpl.class,
            RecordFullConverterImpl.class,
            RoleBaseConverterImpl.class,
            TagBaseConverterImpl.class,
            UserOnlyLoginConverterImpl.class,
            UserRoleBaseConverterImpl.class)
            .activate(ApplicationScoped.class)
            .setEjbFactory(ejbFactory())
            .inject(recordFullConverter)
            .inject(newsEntryFullConverter)
            .inject(this)
            .build();

    @EJB(beanName = "recordFullConverter")
    RecordConverter converter;

    private Record entity;
    private RecordFullDto dto;

    private static void set(Class<?> tClass, String filedName, Object o, Object v) throws Exception {
        Field field = tClass.getDeclaredField(filedName);
        field.setAccessible(true);
        field.set(o, v);
    }

    @BeforeEach
    void setUp() {
        entity = cloneRecord0();
        dto = cloneRecordFullDto0();
    }

    private Record clean(Record entity) {
        entity.setArticle(null);
        entity.setNewsEntry(null);
        entity.setNewsLinks(null);
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
        entity.setNewsEntry(cloneNewsEntry0());
        entity.getNewsEntry().setRecord(entity);
        RecordFullDto test = converter.convert(entity);
        // TODO Assertions.assertEquals(dto, test);
        Assertions.assertTrue(test == ((NewsEntryFullDto) test.getNewsEntry()).getRecord());
    }

    @Test
    void when_convert_DTO_to_Entity() {
        Assertions.assertNotNull(converter);
        dto.setNewsEntry(cloneNewsEntryFullDto0());
        dto.setTags(newSet(cloneTagFullDto0()));
        ((NewsEntryFullDto) dto.getNewsEntry()).setNewsGroup(cloneNewsGroupFullDto0());
        ((NewsEntryFullDto) dto.getNewsEntry()).setRecord(dto);
        Record test = converter.convert(dto);
        entity.setNewsEntry(cloneNewsEntry0());
        // TODO Assertions.assertEquals(entity, test);
        Assertions.assertTrue(test == test.getNewsEntry().getRecord());
    }
}