/*
 * This file was last modified at 2020.04.14 19:52 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupCrudServiceImplTest.java
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
import su.svn.showcase.dao.NewsGroupDao;
import su.svn.showcase.dao.jpa.NewsGroupDaoEjb;
import su.svn.showcase.dto.jdo.NewsGroupJdo;
import su.svn.showcase.services.CrudService;
import su.svn.showcase.services.NewsGroupCrudService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static su.svn.showcase.services.impl.support.EntityManagerFactoryProducer.configure;

@DisplayName("A NewsGroupBaseCrudServiceImplTest unit test cases")
@AddPackages(value = {NewsGroupDao.class, CrudService.class})
@ExtendWith({JtaEnvironment.class, WeldJunit5Extension.class})
class NewsGroupCrudServiceImplTest {

    static final Class<?> tClass = NewsGroupCrudServiceImplTest.class;
    static final String resourceNamePrefix = "/META-INF/sql/" + tClass.getSimpleName();
    static final UUID UUID10 = UUID.fromString("00000000-0000-0000-0000-000000000010");

    static final NewsGroupDao newsGroupDaoEjb = new NewsGroupDaoEjb();
    static final NewsGroupConverter newsGroupBaseConverter = new NewsGroupBaseConverter();
    static final NewsGroupCrudService newsGroupCrudService = new NewsGroupCrudServiceImpl();

    private final Map<String, Object> ejbMap = new HashMap<String, Object>() {{
        put("NewsGroupDaoEjb", newsGroupDaoEjb);
        put("NewsGroupBaseConverter", newsGroupBaseConverter);
        put("NewsGroupCrudService", newsGroupCrudService);
    }};

    private Function<InjectionPoint, Object> ejbFactory() {
        return ip -> {
            String name = ip.getAnnotated().getAnnotation(EJB.class).beanName();
            System.err.println("beanName: " + name);
            return ejbMap.get(name);
        };
    }

    @Inject
    private BeanManager beanManager;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PgPU", configure(beanManager));

    @WeldSetup
    private
    WeldInitiator weld = WeldInitiator.from(
            NewsGroupDaoEjb.class,
            NewsGroupCrudServiceImpl.class,
            EntityManagerFactoryProducer.class,
            EntityManagerProducer.class)
            .activate(RequestScoped.class)
            .setEjbFactory(ejbFactory())
            .setPersistenceContextFactory(injectionPoint -> emf.createEntityManager())
            .setPersistenceUnitFactory(injectionPoint -> emf)
            .inject(newsGroupDaoEjb)
            .inject(newsGroupCrudService)
            .inject(this)
            .build();

    @Inject
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    @EJB(beanName = "NewsGroupCrudService")
    NewsGroupCrudService service;


    @BeforeEach
    void setUp() throws Exception {
        InputStream is = tClass.getResourceAsStream(resourceNamePrefix + "_setUp.sql");
        userTransaction.begin();
        InputStreamUtil.readAndExecuteLine(is, sql ->
                entityManager.createNativeQuery(sql).executeUpdate());
        userTransaction.commit();
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
        NewsGroupJdo newsEntryDto = NewsGroupJdo.builder()
                .id(UUID1)
                .dateTime(NOW1)
                .group("groupTest" + UUID1)
                .build();
        userTransaction.begin();
        service.create(newsEntryDto);
        userTransaction.rollback();
    }

    @Test
    void readById() throws Exception {
        userTransaction.begin();
        NewsGroupJdo test = service.readById(UUID10);
        userTransaction.rollback();
    }

    @Test
    void readRange() throws Exception {
        userTransaction.begin();
        List<NewsGroupJdo> test= service.readRange(0, Integer.MAX_VALUE);
        userTransaction.rollback();
    }

    @Test
    void update() throws Exception {
        NewsGroupJdo newsEntryDto = NewsGroupJdo.builder()
                .id(UUID10)
                .dateTime(NOW1)
                .group("groupTest1")
                .build();
        userTransaction.begin();
        service.update(newsEntryDto);
        userTransaction.rollback();
    }

    @Test
    void delete(NewsGroupCrudService service) {
        // TODO
    }
}