/*
 * This file was last modified at 2020.03.09 15:28 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupDaoJpaTest.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.jboss.weld.junit5.auto.AddPackages;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import su.svn.showcase.dao.NewsGroupDao;
import su.svn.showcase.dao.jpa.NewsGroupDaoJpa;
import su.svn.showcase.domain.NewsGroup;
import su.svn.showcase.services.impl.support.EntityManagerFactoryProducer;
import su.svn.showcase.services.impl.support.EntityManagerProducer;
import su.svn.showcase.services.impl.support.JtaEnvironment;
import su.svn.utils.InputStreamUtil;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;
import java.io.InputStream;
import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static su.svn.showcase.domain.TestData.clean;
import static su.svn.showcase.domain.TestData.cloneNewsGroup1;
import static su.svn.showcase.services.impl.support.EntityManagerFactoryProducer.configure;

@DisplayName("A NewsGroupDaoJpaTest unit test cases")
@AddPackages(value = {NewsGroupDaoJpa.class})
@ExtendWith({JtaEnvironment.class, WeldJunit5Extension.class})
class NewsGroupDaoJpaTest {

    private static final Class<?> tClass = NewsGroupDaoJpaTest.class;
    private static final String resourceNamePrefix = "/META-INF/sql/" + tClass.getSimpleName();
    private static final UUID UUID10 = UUID.fromString("00000000-0000-0000-0000-000000000010");

    @Inject
    private BeanManager beanManager;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PgPU", configure(beanManager));

    @WeldSetup
    private
    WeldInitiator weld = WeldInitiator.from(
            NewsGroupDaoJpa.class,
            EntityManagerFactoryProducer.class,
            EntityManagerProducer.class)
            .activate(RequestScoped.class)
            .setEjbFactory(ejbFactory())
            .setPersistenceContextFactory(injectionPoint -> emf.createEntityManager())
            .setPersistenceUnitFactory(injectionPoint -> emf)
            .inject(this)
            .build();

    private NewsGroupDao mockDao = mock(NewsGroupDao.class);

    private Map<String, Object> ejbMap = new HashMap<String, Object>() {{
        put(null, mockDao);
    }};

    private Function<InjectionPoint, Object> ejbFactory() {
        return ip -> ejbMap.get(ip.getAnnotated().getBaseType().getTypeName());
    }

    @Inject
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    private NewsGroup entity;

    @BeforeEach
    void setUp() throws Exception {
        InputStream is = tClass.getResourceAsStream(resourceNamePrefix + "_setUp.sql");
        userTransaction.begin();
        InputStreamUtil.readAndExecuteLine(is, sql ->
                entityManager.createNativeQuery(sql).executeUpdate());
        userTransaction.commit();

        entity = clean(cloneNewsGroup1());
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
        assertNotNull(entityManager);
        assertNotNull(userTransaction);
    }

    @DisplayName("Test when NewsGroupDaoJpa findById return empty")
    @Test
    void whenDao_findById_shouldBeReturnEmptyOptional() throws Exception {
        userTransaction.begin();
        NewsGroupDao dao = new NewsGroupDaoJpa(entityManager);
        Optional<NewsGroup> test = dao.findById(UUID.randomUUID());
        assertNotNull(test);
        assertFalse(test.isPresent());
        userTransaction.commit();
    }

    @DisplayName("Test when NewsGroupDaoJpa findById return empty")
    @Test
    void whenDao_findById_shouldBeReturnUserNewsGroup() throws Exception {
        userTransaction.begin();
        NewsGroupDao dao = new NewsGroupDaoJpa(entityManager);
        Optional<NewsGroup> test = dao.findById(UUID10);
        assertNotNull(test);
        assertTrue(test.isPresent());
        userTransaction.commit();
    }

    @DisplayName("Test when NewsGroupDaoJpa ")
    @Test
    void whenDao_findAll_shouldBeReturnListOfOneEntity() throws Exception {
        userTransaction.begin();
        NewsGroupDao dao = new NewsGroupDaoJpa(entityManager);
        List<NewsGroup> testList = dao.findAll();
        assertNotNull(testList);
        assertFalse(testList.isEmpty());
        assertEquals(1, testList.size());
        userTransaction.commit();
    }

    @DisplayName("Test when NewsGroupDaoJpa ")
    @Test
    void whenDao_save_success() throws Exception {
        userTransaction.begin();
        NewsGroupDao dao = new NewsGroupDaoJpa(entityManager);
        Optional<NewsGroup> test = dao.findById(UUID.randomUUID());
        assertNotNull(test);
        assertFalse(test.isPresent());
        userTransaction.commit();
    }

    @DisplayName("Test when NewsGroupDaoJpa save of set is success")
    @Test
    void whenDao_save_iterable_success() throws Exception {
        userTransaction.begin();
        NewsGroupDao dao = new NewsGroupDaoJpa(entityManager);
        List<NewsGroup> testList = new ArrayList<NewsGroup>() {{ add(entity); }};
        Iterable<NewsGroup> result = dao.saveAll(testList);
        assertNotNull(result);
        assertEquals(testList, result);
        userTransaction.commit();
    }

    @DisplayName("Test when NewsGroupDaoJpa delete failed")
    @Test
    void whenDao_delete() throws Exception {
        userTransaction.begin();
        NewsGroupDao dao = new NewsGroupDaoJpa(entityManager);
        dao.delete(UUID.randomUUID());
        userTransaction.commit();
    }
}
