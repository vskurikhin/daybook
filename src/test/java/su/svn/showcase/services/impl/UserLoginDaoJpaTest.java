/*
 * This file was last modified at 2020.02.13 21:57 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginDaoJpaTest.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.jboss.weld.junit5.auto.AddPackages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import su.svn.showcase.dao.UserLoginDao;
import su.svn.showcase.dao.jpa.UserLoginDaoJpa;
import su.svn.showcase.domain.TestData;
import su.svn.showcase.domain.UserLogin;
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
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static su.svn.showcase.services.impl.support.EntityManagerFactoryProducer.configure;

@DisplayName("A UserLoginDaoTest unit test cases")
@AddPackages(value = {UserLoginDao.class})
@ExtendWith({JtaEnvironment.class, WeldJunit5Extension.class})
class UserLoginDaoJpaTest {

    @Inject
    private BeanManager beanManager;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PgPU", configure(beanManager));

    @WeldSetup
    private
    WeldInitiator weld = WeldInitiator.from(
            UserLoginDaoJpa.class,
            EntityManagerFactoryProducer.class,
            EntityManagerProducer.class)
            .activate(RequestScoped.class)
            .setEjbFactory(ejbFactory())
            .setPersistenceContextFactory(injectionPoint -> emf.createEntityManager())
            .setPersistenceUnitFactory(injectionPoint -> emf)
            .inject(this)
            .build();
    private UserLoginDao mockUserLoginDao = mock(UserLoginDao.class);
    private Map<String, Object> ejbMap = new HashMap<String, Object>() {{
        put(null,                      mockUserLoginDao);
        put(UserLoginDao.class.getName(), mockUserLoginDao);
    }};

    private Function<InjectionPoint, Object> ejbFactory() {
        return ip -> ejbMap.get(ip.getAnnotated().getBaseType().getTypeName());
    }

    @Inject
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    private UserLogin entity;

    @BeforeEach
    void createNew() {
        entity = TestData.getCloneOfUserLogin1();
    }

    @DisplayName("Can inject entity manager and user transaction")
    @Test
    void canInject_entityManager() {
        assertNotNull(entityManager);
        assertNotNull(userTransaction);
    }

    @DisplayName("Test when newsEntryDao save success")
    @Test
    void whenUserLoginDao_save_success() throws SystemException, NotSupportedException {
        userTransaction.begin();
        UserLoginDao dao = weld.select(UserLoginDaoJpa.class).get();
        UserLogin test = dao.save(entity);
        Assertions.assertNotNull(test);
        Assertions.assertEquals(entity, test);
        userTransaction.rollback();
    }
}
