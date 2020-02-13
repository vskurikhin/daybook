/*
 * This file was last modified at 2020.02.13 10:42 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordDaoJpaTest.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.jboss.weld.junit5.auto.AddPackages;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import su.svn.showcase.dao.RecordDao;
import su.svn.showcase.dao.jpa.RecordDaoJpa;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.TestData;
import su.svn.showcase.services.impl.support.EntityManagerFactoryProducer;
import su.svn.showcase.services.impl.support.EntityManagerProducer;
import su.svn.showcase.services.impl.support.JtaEnvironment;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static su.svn.showcase.services.impl.support.EntityManagerFactoryProducer.configure;

@DisplayName("A RecordDaoJpaTest unit test cases")
@AddPackages(value = {RecordDaoJpa.class})
@ExtendWith({JtaEnvironment.class, WeldJunit5Extension.class})
class RecordDaoJpaTest {

    @Inject
    private BeanManager beanManager;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PgPU", configure(beanManager));

    @WeldSetup
    private
    WeldInitiator weld = WeldInitiator.from(
            RecordDaoJpa.class,
            EntityManagerFactoryProducer.class,
            EntityManagerProducer.class)
            .activate(RequestScoped.class)
            .setEjbFactory(ejbFactory())
            .setPersistenceContextFactory(injectionPoint -> emf.createEntityManager())
            .setPersistenceUnitFactory(injectionPoint -> emf)
            .inject(this)
            .build();

    private RecordDao mockRecordDao = mock(RecordDao.class);

    private Map<String, Object> ejbMap = new HashMap<String, Object>() {{
        put(null,                           mockRecordDao);
        put(RecordDao.class.getName(),         mockRecordDao);
    }};

    private Function<InjectionPoint, Object> ejbFactory() {
        return ip -> ejbMap.get(ip.getAnnotated().getBaseType().getTypeName());
    }

    @Inject
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    private Record entity;

    @BeforeEach
    void createNew() {
        entity = TestData.getCloneOfRecord1();
    }

        @DisplayName("Can inject entity manager and user transaction")
    @Test
    void canInject_entityManager() {
        assertNotNull(entityManager);
        assertNotNull(userTransaction);
    }

    @DisplayName("Test when RecordDaoJpa findById return empty")
    @Test
    void whenRecordDao_findById_shouldBeReturnEmptyOptional() throws SystemException, NotSupportedException {
        userTransaction.begin();
        RecordDao dao = weld.select(RecordDaoJpa.class).get();
        Optional<Record> test = dao.findById(UUID.randomUUID());
        assertNotNull(test);
        assertFalse(test.isPresent());
        userTransaction.rollback();
    }

    @DisplayName("Test when RecordDaoJpa save success")
    @Test
    void whenRecordDao_findAll_shouldBeReturnEmptyList() throws SystemException, NotSupportedException {
        userTransaction.begin();
        RecordDao dao = weld.select(RecordDaoJpa.class).get();
        List<Record> testList = dao.findAll();
        assertNotNull(testList);
        assertTrue(testList.isEmpty());
        userTransaction.rollback();
    }

    @DisplayName("Test when RecordDaoJpa save is success")
    @Test
    void whenRecordDao_save_success() throws SystemException, NotSupportedException {
        userTransaction.begin();
        RecordDao dao = weld.select(RecordDaoJpa.class).get();
        assertTrue(dao.save(entity));
        userTransaction.rollback();
    }

    @DisplayName("Test when RecordDaoJpa save of set is success")
    @Test
    void whenRecordDao_save_iterable_success() throws SystemException, NotSupportedException {
        userTransaction.begin();
        RecordDao dao = weld.select(RecordDaoJpa.class).get();
        List<Record> testRecords = new ArrayList<Record>() {{ add(entity); }};
        assertTrue(dao.saveAll(testRecords));
        userTransaction.rollback();
    }

    @DisplayName("Test when RecordDaoJpa delete failed")
    @Test
    void whenRecordDao_delete_shouldBeReturnFalse() throws SystemException, NotSupportedException {
        userTransaction.begin();
        RecordDao dao = weld.select(RecordDaoJpa.class).get();
        Assertions.assertFalse(dao.delete(UUID.randomUUID()));
        userTransaction.rollback();
    }

    @DisplayName("Test when RecordDaoJpa findById return empty")
    @Test
    @Disabled
    void whenRecordDao_findById_shouldBeReturnRecord1() throws SystemException, NotSupportedException {
        userTransaction.begin();
        RecordDao dao = weld.select(RecordDaoJpa.class).get();
        assertTrue(dao.save(entity));
        Optional<Record> test = dao.findById(su.svn.utils.TestData.RECORD_UUID1);
        assertNotNull(test);
        assertFalse(test.isPresent());
        userTransaction.rollback();
    }
}
