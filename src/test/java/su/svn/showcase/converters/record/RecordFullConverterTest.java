/*
 * This file was last modified at 2020.04.15 22:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordFullConverterTest.java
 * $Id$
 */

package su.svn.showcase.converters.record;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.jboss.weld.junit5.auto.AddPackages;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import su.svn.showcase.converters.*;
import su.svn.showcase.converters.article.ArticleFullConverter;
import su.svn.showcase.converters.base.LinkBaseConverter;
import su.svn.showcase.converters.base.LinkDescriptionBaseConverter;
import su.svn.showcase.converters.base.RoleBaseConverter;
import su.svn.showcase.converters.base.TagBaseConverter;
import su.svn.showcase.converters.news.NewsEntryFullConverter;
import su.svn.showcase.converters.news.NewsGroupBaseConverter;
import su.svn.showcase.converters.news.NewsLinksFullConverter;
import su.svn.showcase.converters.user.UserOnlyLoginBaseConverter;
import su.svn.showcase.converters.user.UserRoleBaseConverter;
import su.svn.showcase.domain.Record;
import su.svn.showcase.dto.jdo.NewsEntryJdo;
import su.svn.showcase.dto.jdo.RecordJdo;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.InjectionPoint;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static su.svn.showcase.domain.TestData.cloneNewsEntry0;
import static su.svn.showcase.domain.TestData.cloneRecord0;
import static su.svn.showcase.dto.TestData.*;
import static su.svn.utils.TestData.newSet;

@DisplayName("A RecordBaseConverterImplTest unit test cases")
@AddPackages(value = {RecordConverter.class, RecordFullConverter.class})
@ExtendWith({WeldJunit5Extension.class})
class RecordFullConverterTest {

    static ArticleConverter articleFullConverter = new ArticleFullConverter();
    static NewsEntryConverter newsEntryFullConverter = new NewsEntryFullConverter();
    static NewsGroupConverter newsGroupBaseConverter = new NewsGroupBaseConverter();
    static NewsLinksConverter newsLinksFullConverter = new NewsLinksFullConverter();
    static RecordConverter recordFullConverter = new RecordFullConverter();
    static TagConverter tagBaseConverter = new TagBaseConverter();
    static UserOnlyLoginConverter userOnlyLoginConverter = new UserOnlyLoginBaseConverter();

    private Map<String, Object> ejbMap = new HashMap<String, Object>() {{
        put("ArticleFullConverter", articleFullConverter);
        put("NewsEntryFullConverter", newsEntryFullConverter);
        put("NewsGroupBaseConverter", newsGroupBaseConverter);
        put("NewsLinksFullConverter", newsLinksFullConverter);
        put("RecordFullConverter", recordFullConverter);
        put("TagBaseConverter", tagBaseConverter);
        put("UserOnlyLoginBaseConverter", userOnlyLoginConverter);
    }};

    private Function<InjectionPoint, Object> ejbFactory() {
        return ip -> {
            String name = ip.getAnnotated().getAnnotation(EJB.class).beanName();
            // System.err.println("beanName: " + name);
            return ejbMap.get(name);
        };
    }

    @WeldSetup
    private
    WeldInitiator weld = WeldInitiator.from(
            ArticleFullConverter.class,
            LinkBaseConverter.class,
            LinkDescriptionBaseConverter.class,
            NewsEntryFullConverter.class,
            NewsGroupBaseConverter.class,
            NewsLinksFullConverter.class,
            RecordFullConverter.class,
            RoleBaseConverter.class,
            TagBaseConverter.class,
            UserOnlyLoginBaseConverter.class,
            UserRoleBaseConverter.class)
            .activate(ApplicationScoped.class)
            .setEjbFactory(ejbFactory())
            .inject(recordFullConverter)
            .inject(newsEntryFullConverter)
            .inject(this)
            .build();

    @EJB(beanName = "RecordFullConverter")
    RecordConverter converter;

    private Record entity;
    private RecordJdo dto;

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

    private RecordJdo clean(RecordJdo dto) {
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
        entity.setType(NewsEntryJdo.class.getSimpleName());
        RecordJdo test = converter.convert(entity);
        // TODO Assertions.assertEquals(dto, test);
        Assertions.assertTrue(test == ((NewsEntryJdo) test.getNewsEntry()).getRecord());
    }

    @Test
    void when_convert_DTO_to_Entity() {
        Assertions.assertNotNull(converter);
        dto.setNewsEntry(cloneNewsEntryJdo0());
        dto.setTags(newSet(cloneTagFullDto0()));
        ((NewsEntryJdo) dto.getNewsEntry()).setNewsGroup(cloneNewsGroupFullDto0());
        ((NewsEntryJdo) dto.getNewsEntry()).setRecord(dto);
        Record test = converter.convert(dto);
        entity.setNewsEntry(cloneNewsEntry0());
        // TODO Assertions.assertEquals(entity, test);
        Assertions.assertTrue(test == test.getNewsEntry().getRecord());
    }
}