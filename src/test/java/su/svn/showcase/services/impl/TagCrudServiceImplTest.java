/*
 * This file was last modified at 2020.04.14 20:47 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagCrudServiceImplTest.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.jboss.weld.junit5.auto.AddPackages;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import su.svn.showcase.converters.TagConverter;
import su.svn.showcase.converters.impl.TagBaseConverter;
import su.svn.showcase.dao.TagDao;
import su.svn.showcase.dao.jpa.TagDaoEjb;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.jdo.TagJdo;
import su.svn.showcase.services.TagCrudService;
import su.svn.showcase.services.CrudService;
import su.svn.showcase.services.impl.support.EntityManagerFactoryProducer;
import su.svn.showcase.services.impl.support.EntityManagerProducer;
import su.svn.showcase.services.impl.support.JtaEnvironment;
import su.svn.showcase.utils.StringUtil;
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

import static su.svn.showcase.domain.TestData.cloneTag1;
import static su.svn.showcase.dto.TestData.cloneTagFullDto1;
import static su.svn.showcase.services.impl.support.EntityManagerFactoryProducer.configure;

@DisplayName("A TagStorageServiceImplTest unit test cases")
@AddPackages(value = {TagDao.class, CrudService.class})
@ExtendWith({JtaEnvironment.class, WeldJunit5Extension.class})
class TagCrudServiceImplTest {

    static final Class<?> tClass = TagCrudServiceImplTest.class;
    static final String resourceNamePrefix = "/META-INF/sql/" + tClass.getSimpleName();
    static final String SID10 = "tag1000000000010";

    static final TagDao tagDaoEjb = new TagDaoEjb();
    static final TagConverter tagBaseConverter = new TagBaseConverter();
    static final TagCrudService tagCrudService = new TagCrudServiceImpl();

    private final Map<String, Object> ejbMap = new HashMap<String, Object>() {{
        put("TagDaoEjb", tagDaoEjb);
        put("TagBaseConverter", tagBaseConverter);
        put("TagCrudService", tagCrudService);
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
            TagDaoEjb.class,
            TagCrudServiceImpl.class,
            EntityManagerFactoryProducer.class,
            EntityManagerProducer.class)
            .activate(RequestScoped.class)
            .setEjbFactory(ejbFactory())
            .setPersistenceContextFactory(injectionPoint -> emf.createEntityManager())
            .setPersistenceUnitFactory(injectionPoint -> emf)
            .inject(tagDaoEjb)
            .inject(tagBaseConverter)
            .inject(tagCrudService)
            .inject(this)
            .build();

    @Inject
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    @EJB(beanName = "TagCrudService")
    TagCrudService service;

    private Tag entity;
    private TagJdo dto;

    @BeforeEach
    void setUp() throws Exception {
        InputStream is = tClass.getResourceAsStream(resourceNamePrefix + "_setUp.sql");
        userTransaction.begin();
        InputStreamUtil.readAndExecuteLine(is, sql ->
                entityManager.createNativeQuery(sql).executeUpdate());
        userTransaction.commit();

        entity = cloneTag1();
        dto = cloneTagFullDto1();
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

    static final String SID1 = StringUtil.generateStringId();
    static LocalDateTime NOW1 = LocalDateTime.now();

    @Test
    void create() throws Exception {
        TagJdo dto = TagJdo.builder()
                .id(SID1)
                .dateTime(NOW1)
                .tag("tagTest" + SID1)
                .build();
        userTransaction.begin();
        service.create(dto);
        userTransaction.rollback();
    }

    @Test
    void readById() throws Exception {
        userTransaction.begin();
        TagJdo test = service.readById(SID10);
        System.err.println("test = " + test);
        userTransaction.rollback();
    }

    @Test
    void readRange() throws Exception {
        userTransaction.begin();
        List<TagJdo> test = service.readRange(0, Integer.MAX_VALUE);
        System.err.println("test = " + test);
        userTransaction.rollback();
    }

    @Test
    void update() throws Exception {
        TagJdo dto = TagJdo.builder()
                .id(SID10)
                .dateTime(NOW1)
                .tag("groupTest1")
                .build();
        userTransaction.begin();
        service.update(dto);
        userTransaction.rollback();
    }

    @Test
    void delete(TagCrudService service) {
        // TODO
    }
}