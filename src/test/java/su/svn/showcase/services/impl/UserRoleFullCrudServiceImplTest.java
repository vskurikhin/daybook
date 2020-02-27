/*
 * This file was last modified at 2020.02.21 22:20 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleFullCrudServiceImplTest.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.jboss.weld.junit5.auto.AddPackages;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import su.svn.showcase.dao.UserRoleDao;
import su.svn.showcase.dao.jpa.UserRoleDaoJpa;
import su.svn.showcase.domain.UserRole;
import su.svn.showcase.dto.UserRoleFullDto;
import su.svn.showcase.services.CrudService;
import su.svn.showcase.services.UserRoleFullCrudService;
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
import static su.svn.showcase.domain.TestData.cloneUserRole1;
import static su.svn.showcase.dto.TestData.cloneUserRoleFullDto1;
import static su.svn.showcase.services.impl.support.EntityManagerFactoryProducer.configure;

@DisplayName("A UserRoleFullCrudServiceImplTest unit test cases")
@AddPackages(value = {UserRoleDao.class, CrudService.class})
@ExtendWith({JtaEnvironment.class, WeldJunit5Extension.class})
class UserRoleFullCrudServiceImplTest {

    @Inject
    private BeanManager beanManager;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PgPU", configure(beanManager));

    @WeldSetup
    private
    WeldInitiator weld = WeldInitiator.from(
            UserRoleDaoJpa.class,
            UserRoleFullCrudServiceImpl.class,
            EntityManagerFactoryProducer.class,
            EntityManagerProducer.class)
            .activate(RequestScoped.class)
            .setEjbFactory(ejbFactory())
            .setPersistenceContextFactory(injectionPoint -> emf.createEntityManager())
            .setPersistenceUnitFactory(injectionPoint -> emf)
            .inject(this)
            .build();

    private UserRoleDao mockDao = mock(UserRoleDao.class);
    private UserRoleFullCrudService mockService = mock(UserRoleFullCrudService.class);

    private Map<String, Object> ejbMap = new HashMap<String, Object>() {{
        put(null,                                    mockDao);
        put(UserRoleDao.class.getName(),             mockDao);
        put(UserRoleFullCrudService.class.getName(), mockService);
    }};

    private Function<InjectionPoint, Object> ejbFactory() {
        return ip -> ejbMap.get(ip.getAnnotated().getBaseType().getTypeName());
    }

    @Inject
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    private UserRole entity;
    private UserRoleFullDto dto;

    @BeforeEach
    void setUp() {
        entity = cloneUserRole1();
        dto = cloneUserRoleFullDto1();
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
    void create(UserRoleFullCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.save(any())).thenReturn(entity);
        Assertions.assertThrows(su.svn.showcase.exceptions.ErrorCase.class, () -> service.create(dto));
    }

    @Test
    @Disabled
    void readById(UserRoleFullCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.findById(any())).thenReturn(Optional.of(entity));
        Assertions.assertEquals(dto, service.readById(entity.getId())); // TODO fix
    }

    @Test
    void readRange(UserRoleFullCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.findById(any())).thenReturn(Optional.of(entity));
        List<UserRoleFullDto> testList = service.readRange(0, Integer.MAX_VALUE);
        Assertions.assertTrue(testList.isEmpty());
    }

    @Test
    void update(UserRoleFullCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.save(any())).thenReturn(entity);
        Assertions.assertThrows(su.svn.showcase.exceptions.ErrorCase.class, () -> service.update(dto));
    }

    @Test
    void delete(UserRoleFullCrudService service) {
        Assertions.assertNotNull(service);
        service.delete(dto.getId());
    }
}