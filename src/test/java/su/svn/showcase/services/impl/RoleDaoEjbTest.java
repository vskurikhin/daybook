/*
 * This file was last modified at 2020.02.18 11:26 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RoleDaoJpaTest.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.jboss.weld.junit5.auto.AddPackages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import su.svn.showcase.dao.RoleDao;
import su.svn.showcase.dao.jpa.RoleDaoEjb;
import su.svn.showcase.domain.Role;
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

@DisplayName("A RoleDaoJpaTest unit test cases")
@AddPackages(value = {RoleDaoEjb.class})
@ExtendWith({JtaEnvironment.class, WeldJunit5Extension.class})
class RoleDaoEjbTest {

    @Inject
    private BeanManager beanManager;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PgPU", configure(beanManager));

    @WeldSetup
    private
    WeldInitiator weld = WeldInitiator.from(
            RoleDaoEjb.class,
            EntityManagerFactoryProducer.class,
            EntityManagerProducer.class)
            .activate(RequestScoped.class)
            .setEjbFactory(ejbFactory())
            .setPersistenceContextFactory(injectionPoint -> emf.createEntityManager())
            .setPersistenceUnitFactory(injectionPoint -> emf)
            .inject(this)
            .build();

    private RoleDao mockDao = mock(RoleDao.class);

    private Map<String, Object> ejbMap = new HashMap<String, Object>() {{
        put(null, mockDao);
        put(RoleDao.class.getName(), mockDao);
    }};

    private Function<InjectionPoint, Object> ejbFactory() {
        return ip -> ejbMap.get(ip.getAnnotated().getBaseType().getTypeName());
    }

    @Inject
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    private Role entity;

    @BeforeEach
    void createNew() {
        entity = TestData.cloneRole1();
    }

    @DisplayName("Can inject entity manager and user transaction")
    @Test
    void canInject_entityManager() {
        assertNotNull(entityManager);
        assertNotNull(userTransaction);
    }

    @DisplayName("Test when RoleDaoJpa findById return empty")
    @Test
    void whenRoleDao_findById_shouldBeReturnEmptyOptional() throws SystemException, NotSupportedException {
        userTransaction.begin();
        RoleDao dao = weld.select(RoleDaoEjb.class).get();
        Optional<Role> test = dao.findById(UUID.randomUUID());
        assertNotNull(test);
        assertFalse(test.isPresent());
        userTransaction.rollback();
    }

    @DisplayName("Test when RoleDaoJpa save success")
    @Test
    void whenRoleDao_findAll_shouldBeReturnEmptyList() throws SystemException, NotSupportedException {
        userTransaction.begin();
        RoleDao dao = weld.select(RoleDaoEjb.class).get();
        List<Role> testList = dao.findAll();
        assertNotNull(testList);
        assertTrue(testList.isEmpty());
        userTransaction.rollback();
    }

    @DisplayName("Test when RoleDaoJpa save is success")
    @Test
    void whenRoleDao_save_success() throws SystemException, NotSupportedException {
        userTransaction.begin();
        RoleDao dao = weld.select(RoleDaoEjb.class).get();
        Optional<Role> test = dao.findById(UUID.randomUUID());
        assertNotNull(test);
        assertFalse(test.isPresent());
        userTransaction.rollback();
    }

    @DisplayName("Test when RoleDaoJpa save of set is success")
    @Test
    void whenRoleDao_save_iterable_success() throws SystemException, NotSupportedException {
        userTransaction.begin();
        RoleDao dao = weld.select(RoleDaoEjb.class).get();
        List<Role> testList = new ArrayList<Role>() {{ add(entity); }};
        Iterable<Role> result = dao.saveAll(testList);
        assertNotNull(result);
        assertEquals(testList, result);
        userTransaction.rollback();
    }

    @DisplayName("Test when RoleDaoJpa delete failed")
    @Test
    void whenRoleDao_delete_shouldBeReturnFalse() throws SystemException, NotSupportedException {
        userTransaction.begin();
        RoleDao dao = weld.select(RoleDaoEjb.class).get();
        dao.delete(UUID.randomUUID());
        userTransaction.rollback();
    }
}
