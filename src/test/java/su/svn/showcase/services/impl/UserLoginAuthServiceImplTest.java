/*
 * This file was last modified at 2020.04.14 20:47 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginAuthServiceImplTest.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.jboss.weld.junit5.auto.AddPackages;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import su.svn.showcase.dao.UserLoginDao;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.TestData;
import su.svn.showcase.dto.UserLoginAuthDto;
import su.svn.showcase.services.CrudService;
import su.svn.showcase.services.UserLoginAuthService;
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
import javax.transaction.UserTransaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static su.svn.showcase.domain.TestData.cloneUserLogin1;
import static su.svn.showcase.dto.TestData.cloneUserLoginFullDto1;
import static su.svn.showcase.services.impl.support.EntityManagerFactoryProducer.configure;

@DisplayName("A UserLoginAuthServiceImplTest unit test cases")
@AddPackages(value = {UserLoginAuthService.class, CrudService.class})
@ExtendWith({JtaEnvironment.class, WeldJunit5Extension.class})
class UserLoginAuthServiceImplTest {

    @Inject
    private BeanManager beanManager;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PgPU", configure(beanManager));

    @WeldSetup
    private
    WeldInitiator weld = WeldInitiator.from(
            UserLoginAuthServiceImpl.class,
            EntityManagerFactoryProducer.class,
            EntityManagerProducer.class)
            .activate(RequestScoped.class)
            .setEjbFactory(ejbFactory())
            .setPersistenceContextFactory(injectionPoint -> emf.createEntityManager())
            .setPersistenceUnitFactory(injectionPoint -> emf)
            .inject(this)
            .build();


    private UserLoginDao mockDao = mock(UserLoginDao.class);
    private UserLoginAuthService mockService = mock(UserLoginAuthService.class);

    private Map<String, Object> ejbMap = new HashMap<String, Object>() {{
        put(null,                                 mockService);
        put(UserLoginAuthService.class.getName(), mockService);
        put(UserLoginDao.class.getName(),         mockDao);
    }};

    private Function<InjectionPoint, Object> ejbFactory() {
        return ip -> ejbMap.get(ip.getAnnotated().getBaseType().getTypeName());
    }

    @Inject
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    private UserLogin entity;
    private UserLoginAuthDto dto;

    @BeforeEach
    void setUp() {
        entity = cloneUserLogin1();
        dto = cloneUserLoginFullDto1();
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("Can inject entity manager and user transaction")
    @Test
    void canInject_entityManager() {
        Assertions.assertNotNull(entityManager);
        Assertions.assertNotNull(userTransaction);
    }

    @Test
    void create(UserLoginAuthService service) {
        Assertions.assertNotNull(service);
        when(mockDao.save(any())).thenReturn(entity);
        when(mockDao.findWhereLogin(any())).thenReturn(Optional.empty());
        service.create(dto);
    }

    @Test
    void readById(UserLoginAuthService service) {
        Assertions.assertNotNull(service);
        when(mockDao.findById(any())).thenReturn(Optional.of(entity));
        Assertions.assertEquals(TestData.USER_LOGIN_JDO_1, service.readById(entity.getId())); // TODO fix
    }

    @Test
    void readRange(UserLoginAuthService service) {
        Assertions.assertNotNull(service);
        when(mockDao.findById(any())).thenReturn(Optional.of(entity));
        List<UserLoginAuthDto> testList = service.readRange(0, Integer.MAX_VALUE);
        Assertions.assertTrue(testList.isEmpty());
    }

    @Test
    void update(UserLoginAuthService service) {
        Assertions.assertNotNull(service);
        when(mockDao.save(any())).thenReturn(entity);
        when(mockDao.findWhereLogin(any())).thenReturn(Optional.of(entity));
        service.update(dto);
    }

    @Test
    void delete(UserLoginAuthService service) {
        Assertions.assertNotNull(service);
        service.delete(dto.getId());
    }
}
//EOF
