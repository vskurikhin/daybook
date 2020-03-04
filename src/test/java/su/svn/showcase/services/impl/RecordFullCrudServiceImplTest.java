/*
 * This file was last modified at 2020.03.01 00:04 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordFullCrudServiceImplTest.java
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
import su.svn.showcase.dao.UserLoginDao;
import su.svn.showcase.dao.jpa.RecordDaoEjb;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.services.CrudService;
import su.svn.showcase.services.RecordFullCrudService;
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
import static su.svn.showcase.domain.TestData.cloneRecord1;
import static su.svn.showcase.domain.TestData.cloneUserLogin1;
import static su.svn.showcase.dto.TestData.cloneRecordFullDto1;
import static su.svn.showcase.services.impl.support.EntityManagerFactoryProducer.configure;

@SuppressWarnings("Convert2Diamond")
@DisplayName("A RecordFullCrudServiceImplTest unit test cases")
@AddPackages(value = {RecordDao.class, CrudService.class})
@ExtendWith({JtaEnvironment.class, WeldJunit5Extension.class})
class RecordFullCrudServiceImplTest {

    @Inject
    private BeanManager beanManager;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PgPU", configure(beanManager));

    @WeldSetup
    private
    WeldInitiator weld = WeldInitiator.from(
            RecordDaoEjb.class,
            RecordFullCrudServiceImpl.class,
            EntityManagerFactoryProducer.class,
            EntityManagerProducer.class)
            .activate(RequestScoped.class)
            .setEjbFactory(ejbFactory())
            .setPersistenceContextFactory(injectionPoint -> emf.createEntityManager())
            .setPersistenceUnitFactory(injectionPoint -> emf)
            .inject(this)
            .build();

    private RecordDao mockDao = mock(RecordDao.class);
    private UserLoginDao mockUserLoginDao = mock(UserLoginDao.class);
    private RecordFullCrudService mockService = mock(RecordFullCrudService.class);

    private Map<String, Object> ejbMap = new HashMap<String, Object>() {{
        put(null,                                     mockDao);
        put(RecordDao.class.getName(),             mockDao);
        put(RecordFullCrudService.class.getName(), mockService);
        put(UserLoginDao.class.getName(),             mockUserLoginDao);
    }};

    private Function<InjectionPoint, Object> ejbFactory() {
        return ip -> ejbMap.get(ip.getAnnotated().getBaseType().getTypeName());
    }

    @Inject
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    private Record entity;
    private RecordFullDto dto;
    private UserLogin userLogin;

    @BeforeEach
    void setUp() {
        entity = cloneRecord1();
        dto = cloneRecordFullDto1();
        userLogin = cloneUserLogin1();
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
    void create(RecordFullCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.save(any())).thenReturn(entity);
        when(mockUserLoginDao.findById(any())).thenReturn(Optional.of(userLogin));
        when(mockUserLoginDao.findWhereLogin(any())).thenReturn(Optional.of(userLogin));
        service.create(dto);
    }

    @Test
    void readById(RecordFullCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.findById(any())).thenReturn(Optional.of(entity));
        Assertions.assertEquals(dto, service.readById(entity.getId()));
    }

    @Test
    void readRange(RecordFullCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.findById(any())).thenReturn(Optional.of(entity));
        List<RecordFullDto> testList = service.readRange(0, Integer.MAX_VALUE);
        Assertions.assertTrue(testList.isEmpty());
    }

    @Test
    void update(RecordFullCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.save(any())).thenReturn(entity);
        when(mockDao.findById(any())).thenReturn(Optional.of(entity));
        when(mockUserLoginDao.findById(any())).thenReturn(Optional.of(userLogin));
        when(mockUserLoginDao.findWhereLogin(any())).thenReturn(Optional.of(userLogin));
        service.update(dto);
    }

    @Test
    void delete(RecordFullCrudService service) {
        Assertions.assertNotNull(service);
        service.delete(dto.getId());
    }
}
