/*
 * This file was last modified at 2020.04.06 22:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleFullCrudServiceImplTest.java
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
import su.svn.showcase.converters.impl.*;
import su.svn.showcase.dao.ArticleDao;
import su.svn.showcase.dao.LinkDao;
import su.svn.showcase.dao.RecordDao;
import su.svn.showcase.dao.UserLoginDao;
import su.svn.showcase.dao.jpa.ArticleDaoEjb;
import su.svn.showcase.dao.jpa.LinkDaoEjb;
import su.svn.showcase.dao.jpa.RecordDaoEjb;
import su.svn.showcase.dao.jpa.UserLoginDaoEjb;
import su.svn.showcase.domain.Article;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.*;
import su.svn.showcase.services.CrudService;
import su.svn.showcase.services.ArticleFullCrudService;
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

import static su.svn.showcase.domain.TestData.*;
import static su.svn.showcase.dto.TestData.*;
import static su.svn.showcase.services.impl.support.EntityManagerFactoryProducer.configure;

@SuppressWarnings("Convert2Diamond")
@DisplayName("A ArticleFullCrudServiceImplTest unit test cases")
@AddPackages(value = {ArticleDao.class, CrudService.class})
@ExtendWith({JtaEnvironment.class, WeldJunit5Extension.class})
class ArticleFullCrudServiceImplTest {

    static final Class<?> tClass = ArticleFullCrudServiceImplTest.class;
    static final String resourceNamePrefix = "/META-INF/sql/" + tClass.getSimpleName();
    static final UUID UUID10 = UUID.fromString("00000000-0000-0000-0000-000000000010");

    static final ArticleDao articleDao = new ArticleDaoEjb();
    static final RecordDao recordDao = new RecordDaoEjb();
    static final LinkDao linkDao = new LinkDaoEjb();
    static final UserLoginDao userLoginDao = new UserLoginDaoEjb();

    static final ArticleConverter articleFullConverter = new ArticleFullConverter();
    static final ArticleConverter articlePartConverter = new ArticlePartConverter();
    static final LinkBaseConverter linkBaseConverter = new LinkBaseConverter();
    static final NewsLinksFullConverter newsLinksFullConverter = new NewsLinksFullConverter();
    static final RecordConverter recordBaseConverter = new RecordBaseConverter();
    static final RecordConverter recordFullConverter = new RecordFullConverter();
    static final TagConverter tagBaseConverter = new TagBaseConverter();
    static final UserLoginConverter userOnlyLoginConverter = new UserOnlyLoginConverter();

    static final ArticleFullCrudService articleFullCrudService = new ArticleFullCrudServiceImpl();

    private final Map<String, Object> ejbMap = new HashMap<String, Object>() {{
        put("ArticleDaoEjb", articleDao);
        put("RecordDaoEjb", recordDao);
        put("LinkDaoEjb", linkDao);
        put("UserLoginDaoEjb", userLoginDao);

        put("ArticleFullConverter", articleFullConverter);
        put("ArticlePartConverter", articlePartConverter);
        put("LinkBaseConverter", linkBaseConverter);
        put("NewsLinksFullConverter", newsLinksFullConverter);
        put("RecordBaseConverter", recordBaseConverter);
        put("RecordFullConverter", recordFullConverter);
        put("TagBaseConverter", tagBaseConverter);
        put("UserOnlyLoginConverter", userOnlyLoginConverter);

        put("ArticleFullCrudService", articleFullCrudService);
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
            ArticleDaoEjb.class,
            ArticleFullCrudServiceImpl.class,
            EntityManagerFactoryProducer.class,
            EntityManagerProducer.class)
            .activate(RequestScoped.class)
            .setEjbFactory(ejbFactory())
            .setPersistenceContextFactory(injectionPoint -> emf.createEntityManager())
            .setPersistenceUnitFactory(injectionPoint -> emf)

            .inject(articleDao)
            .inject(recordDao)
            .inject(linkDao)
            .inject(userLoginDao)
            .inject(newsLinksFullConverter)
            .inject(recordBaseConverter)
            .inject(recordFullConverter)
            .inject(tagBaseConverter)
            .inject(userOnlyLoginConverter)
            .inject(articleFullConverter)
            .inject(articlePartConverter)
            .inject(articleFullCrudService)
            .inject(this)

            .build();

    @Inject
    EntityManager entityManager;

    @Inject
    UserTransaction userTransaction;

    @EJB(beanName = "ArticleFullCrudService")
    ArticleFullCrudService service;

    private Article entity;
    private ArticleFullDto dto;
    private NewsGroupBaseDto newsGroupDto;
    private Record record;
    private RecordDto recordDto;
    private UserLogin userLogin;
    private UserLoginDto userLoginDto;


    @BeforeEach
    void setUp() throws Exception {
        InputStream is = tClass.getResourceAsStream(resourceNamePrefix + "_setUp.sql");
        userTransaction.begin();
        InputStreamUtil.readAndExecuteLine(is, sql ->
                entityManager.createNativeQuery(sql).executeUpdate());
        userTransaction.commit();

        entity = cloneArticle1();
        entity.getRecord().setArticle(entity);
        entity.getRecord().setType(ArticleFullDto.class.getSimpleName());
        dto = cloneArticleFullDto1();
        newsGroupDto = cloneNewsGroupBaseDto1();
        record = cloneRecord1();
        recordDto = cloneRecordFullDto1();
        recordDto.setType(ArticleFullDto.class.getSimpleName());
        dto.setRecord(recordDto);
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

    static final UUID UUID1 = UUID.randomUUID();
    static final LocalDateTime NOW1 = LocalDateTime.now();

    @Test
    void create() throws Exception {
        RecordFullDto recordDto = RecordFullDto.builder()
                .id(UUID1)
                .createDateTime(NOW1)
                .editDateTime(NOW1)
                .index(13)
                .type(ArticleFullDto.class.getSimpleName())
                .userLogin(userLoginDto)
                .build();
        ArticleFullDto newsEntryDto = ArticleFullDto.builder()
                .id(UUID1)
                .record(recordDto)
                .dateTime(NOW1)
                .title("titleTest1")
                .include("includeTest1")
                .anchor("anchorTest1")
                .summary("summaryTest1")
                .build();
        recordDto.setArticle(newsEntryDto);
        userTransaction.begin();
        service.create(newsEntryDto);
        userTransaction.rollback();
    }

    @Test
    void readById() throws Exception {
        userTransaction.begin();
        ArticleFullDto test = service.readById(UUID10);
        userTransaction.rollback();
    }

    @Test
    void readRange() throws Exception {
        userTransaction.begin();
        List<ArticleFullDto> test= service.readRange(0, Integer.MAX_VALUE);
        userTransaction.rollback();
    }

    @Test
    void update() throws Exception {
        ArticleFullDto articleDto = ArticleFullDto.builder()
                .id(UUID10)
                .dateTime(NOW1)
                .title("titleTest10")
                .include("includeTest10")
                .anchor("anchorTest10")
                .summary("summaryTest10")
                .record(recordDto)
                .build();
        userTransaction.begin();
        service.update(articleDto);
        userTransaction.rollback();
    }

    @Test
    void delete(ArticleFullCrudService service) {
        // TODO
    }
}
