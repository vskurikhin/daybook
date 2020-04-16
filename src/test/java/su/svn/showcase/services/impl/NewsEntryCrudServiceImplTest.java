/*
 * This file was last modified at 2020.04.15 22:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryCrudServiceImplTest.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.jboss.weld.junit5.auto.AddPackages;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import su.svn.showcase.converters.*;
import su.svn.showcase.converters.base.*;
import su.svn.showcase.converters.news.NewsEntryBaseConverter;
import su.svn.showcase.converters.news.NewsEntryFullConverter;
import su.svn.showcase.converters.news.NewsEntryPartConverter;
import su.svn.showcase.converters.news.NewsGroupBaseConverter;
import su.svn.showcase.converters.record.RecordBaseConverter;
import su.svn.showcase.converters.record.RecordFullConverter;
import su.svn.showcase.converters.record.RecordPartConverter;
import su.svn.showcase.converters.user.UserOnlyLoginBaseConverter;
import su.svn.showcase.dao.*;
import su.svn.showcase.dao.jpa.*;
import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.*;
import su.svn.showcase.dto.jdo.NewsEntryJdo;
import su.svn.showcase.dto.jdo.NewsGroupJdo;
import su.svn.showcase.dto.jdo.RecordJdo;
import su.svn.showcase.services.CrudService;
import su.svn.showcase.services.NewsEntryCrudService;
import su.svn.showcase.services.impl.support.EntityManagerFactoryProducer;
import su.svn.showcase.services.impl.support.EntityManagerProducer;
import su.svn.showcase.services.impl.support.JtaEnvironment;
import su.svn.utils.InputStreamUtil;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

import static org.mockito.internal.util.collections.Sets.newSet;
import static su.svn.showcase.domain.TestData.*;
import static su.svn.showcase.dto.TestData.*;
import static su.svn.showcase.services.impl.support.EntityManagerFactoryProducer.configure;

@SuppressWarnings("Convert2Diamond")
@DisplayName("A NewsEntryFullCrudServiceImplTest unit test cases")
@AddPackages(value = {NewsEntryDao.class, CrudService.class})
@ExtendWith({JtaEnvironment.class, WeldJunit5Extension.class})
class NewsEntryCrudServiceImplTest {

    static final Class<?> tClass = NewsEntryCrudServiceImplTest.class;
    static final String resourceNamePrefix = "/META-INF/sql/" + tClass.getSimpleName();
    static final UUID UUID10 = UUID.fromString("00000000-0000-0000-0000-000000000010");

    static final NewsEntryDao newsEntryDao = new NewsEntryDaoEjb();
    static final RecordDao recordDao = new RecordDaoEjb();
    static final UserLoginDao userLoginDao = new UserLoginDaoEjb();

    static final NewsEntryConverter newsEntryBaseConverter = new NewsEntryBaseConverter();
    static final NewsEntryConverter newsEntryFullConverter = new NewsEntryFullConverter();
    static final NewsEntryConverter newsEntryPartConverter = new NewsEntryPartConverter();
    static final NewsGroupConverter newsGroupBaseConverter = new NewsGroupBaseConverter();
    static final RecordConverter recordBaseConverter = new RecordBaseConverter();
    static final RecordConverter recordFullConverter = new RecordFullConverter();
    static final RecordConverter recordPartConverter = new RecordPartConverter();
    static final TagConverter tagBaseConverter = new TagBaseConverter();
    static final UserOnlyLoginConverter userOnlyLoginConverter = new UserOnlyLoginBaseConverter();

    static final NewsEntryCrudService NEWS_ENTRY_CRUD_SERVICE = new NewsEntryCrudServiceImpl();

    private final Map<String, Object> ejbMap = new HashMap<String, Object>() {{
        put("NewsEntryDaoEjb", newsEntryDao);
        put("RecordDaoEjb", recordDao);
        put("UserLoginDaoEjb", userLoginDao);

        put("NewsEntryBaseConverter", newsEntryBaseConverter);
        put("NewsEntryFullConverter", newsEntryFullConverter);
        put("NewsEntryPartConverter", newsEntryPartConverter);
        put("NewsGroupBaseConverter", newsGroupBaseConverter);
        put("RecordBaseConverter", recordBaseConverter);
        put("RecordFullConverter", recordFullConverter);
        put("RecordPartConverter", recordPartConverter);
        put("TagBaseConverter", tagBaseConverter);
        put("UserOnlyLoginBaseConverter", userOnlyLoginConverter);

        put("NewsEntryCrudService", NEWS_ENTRY_CRUD_SERVICE);
    }};

    private Function<InjectionPoint, Object> ejbFactory() {
        return ip -> {
            String name = ip.getAnnotated().getAnnotation(EJB.class).beanName();
            System.err.println("beanName: " + name);
            return ejbMap.get(name);
        };
    }


    @Inject
    BeanManager beanManager;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PgPU", configure(beanManager));

    @WeldSetup
    WeldInitiator weld = WeldInitiator.from(
            NewsEntryDaoEjb.class,
            NewsEntryCrudServiceImpl.class,
            EntityManagerFactoryProducer.class,
            EntityManagerProducer.class)
            .activate(RequestScoped.class)
            .setEjbFactory(ejbFactory())
            .setPersistenceContextFactory(injectionPoint -> emf.createEntityManager())
            .setPersistenceUnitFactory(injectionPoint -> emf)

            .inject(newsEntryDao)
            .inject(recordDao)
            .inject(userLoginDao)
            .inject(recordBaseConverter)
            .inject(recordFullConverter)
            .inject(recordPartConverter)
            .inject(tagBaseConverter)
            .inject(userOnlyLoginConverter)
            .inject(newsEntryFullConverter)
            .inject(newsEntryPartConverter)
            .inject(NEWS_ENTRY_CRUD_SERVICE)
            .inject(this)

            .build();

    @Inject
    EntityManager entityManager;

    @Inject
    UserTransaction userTransaction;


    @EJB(beanName = "NewsEntryCrudService")
    NewsEntryCrudService service;

    private NewsEntry entity;
    private NewsEntryJdo dto;
    private NewsGroupJdo newsGroupDto;
    private Record record;
    private UserLogin userLogin;
    private UserLoginDto userLoginDto;


    @BeforeEach
    void setUp() throws Exception {
        InputStream is = tClass.getResourceAsStream(resourceNamePrefix + "_setUp.sql");
        userTransaction.begin();
        InputStreamUtil.readAndExecuteLine(is, sql ->
                entityManager.createNativeQuery(sql).executeUpdate());
        userTransaction.commit();

        entity = cloneNewsEntry1();
        entity.getRecord().setNewsEntry(entity);
        dto = cloneNewsEntryJdo1();
        ((RecordJdo) dto.getRecord()).setTags(newSet(cloneTagFullDto1()));
        newsGroupDto = cloneNewsGroupFullDto1();
        dto.setNewsGroup(newsGroupDto);
        record = cloneRecord1();
        userLogin = cloneUserLogin1();
        userLoginDto = cloneUserOnlyLoginBaseDto1();
    }

    @AfterEach
    void tearDown() throws Exception {
        try {
            userTransaction.rollback();
        } catch (Exception ignored) {}

        InputStream is = tClass.getResourceAsStream(resourceNamePrefix + "_tearDown.sql");
        userTransaction.begin();
        InputStreamUtil.readAndExecuteLine(is, sql ->
                entityManager.createNativeQuery(sql).executeUpdate());
        userTransaction.commit();
    }


    @DisplayName("Can inject entity manager and user transaction")
    @Test
    void canInject_entityManager() {
        Assertions.assertNotNull(entityManager);
        Assertions.assertNotNull(userTransaction);
    }

    static UUID UUID1 = UUID.randomUUID();
    static LocalDateTime NOW1 = LocalDateTime.now();

    @Test
    void create() throws Exception {
        RecordJdo recordDto = RecordJdo.builder()
                .id(UUID1)
                .createDateTime(NOW1)
                .editDateTime(NOW1)
                .index(13)
                .type(NewsEntryJdo.class.getSimpleName())
                .userLogin(userLoginDto)
                .build();
        NewsEntryJdo newsEntryDto = NewsEntryJdo.builder()
                .id(UUID1)
                .record(recordDto)
                .dateTime(NOW1)
                .title("titleTest10")
                .content("contentTest10")
                .newsGroup(newsGroupDto)
                .build();
        recordDto.setNewsEntry(newsEntryDto);
        userTransaction.begin();
        service.create(dto);
        userTransaction.rollback();
    }

    @Test
    void readById() throws Exception {
        userTransaction.begin();
        NewsEntryJdo test = service.readById(UUID10);
        userTransaction.rollback();
    }

    @Test
    void readRange() throws Exception {
        userTransaction.begin();
        List<NewsEntryJdo> test= service.readRange(0, Integer.MAX_VALUE);
        userTransaction.rollback();
    }

    @Test
    void update() throws Exception {
        NewsEntryJdo newsEntryDto = NewsEntryJdo.builder()
                .id(UUID10)
                .dateTime(NOW1)
                .title("titleTest10")
                .content("contentTest10")
                .build();
        userTransaction.begin();
        service.create(dto);
        userTransaction.rollback();
    }

    @Test
    void delete(NewsEntryCrudService service) {
        // TODO
    }
}
