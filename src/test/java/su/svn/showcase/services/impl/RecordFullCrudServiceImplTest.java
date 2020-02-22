/*
 * This file was last modified at 2020.02.22 17:45 by Victor N. Skurikhin.
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
import su.svn.showcase.dao.NewsEntryDao;
import su.svn.showcase.dao.jpa.NewsEntryDaoJpa;
import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.dto.NewsEntryFullDto;
import su.svn.showcase.services.CrudService;
import su.svn.showcase.services.NewsEntryFullCrudService;
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
import static su.svn.showcase.domain.TestData.cloneNewsEntry1;
import static su.svn.showcase.dto.TestData.cloneNewsEntryFullDto1;
import static su.svn.showcase.services.impl.support.EntityManagerFactoryProducer.configure;

@DisplayName("A NewsEntryFullCrudServiceImplTest unit test cases")
@AddPackages(value = {NewsEntryDao.class, CrudService.class})
@ExtendWith({JtaEnvironment.class, WeldJunit5Extension.class})
class RecordFullCrudServiceImplTest {

    @Inject
    private BeanManager beanManager;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PgPU", configure(beanManager));

    @WeldSetup
    private
    WeldInitiator weld = WeldInitiator.from(
            NewsEntryDaoJpa.class,
            NewsEntryFullCrudServiceImpl.class,
            EntityManagerFactoryProducer.class,
            EntityManagerProducer.class)
            .activate(RequestScoped.class)
            .setEjbFactory(ejbFactory())
            .setPersistenceContextFactory(injectionPoint -> emf.createEntityManager())
            .setPersistenceUnitFactory(injectionPoint -> emf)
            .inject(this)
            .build();

    private NewsEntryDao mockDao = mock(NewsEntryDao.class);
    private NewsEntryFullCrudService mockService = mock(NewsEntryFullCrudService.class);

    private Map<String, Object> ejbMap = new HashMap<String, Object>() {{
        put(null,                                     mockDao);
        put(NewsEntryDao.class.getName(),             mockDao);
        put(NewsEntryFullCrudService.class.getName(), mockService);
    }};

    private Function<InjectionPoint, Object> ejbFactory() {
        return ip -> ejbMap.get(ip.getAnnotated().getBaseType().getTypeName());
    }

    @Inject
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    private NewsEntry entity;
    private NewsEntryFullDto dto;

    @BeforeEach
    void setUp() {
        entity = cloneNewsEntry1();
        dto = cloneNewsEntryFullDto1();
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
    void create(NewsEntryFullCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.save(any())).thenReturn(entity);
        System.out.println("dto = " + dto);
        service.create(dto);
    }

    @Test
    void readById(NewsEntryFullCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.findById(any())).thenReturn(Optional.of(entity));
        Assertions.assertEquals(dto, service.readById(entity.getId()));
    }

    @Test
    void readRange(NewsEntryFullCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.findById(any())).thenReturn(Optional.of(entity));
        List<NewsEntryFullDto> testList = service.readRange(0, Integer.MAX_VALUE);
        Assertions.assertTrue(testList.isEmpty());
    }

    @Test
    void update(NewsEntryFullCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.save(any())).thenReturn(entity);
        service.update(dto);
    }

    @Test
    void delete(NewsEntryFullCrudService service) {
        Assertions.assertNotNull(service);
        service.delete(dto.getId());
    }
}
