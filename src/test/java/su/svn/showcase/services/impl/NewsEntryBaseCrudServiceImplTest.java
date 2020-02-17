/*
 * This file was last modified at 2020.02.16 00:14 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryBaseCrudServiceImplTest.java$
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
import su.svn.showcase.dto.NewsEntryBaseDto;
import su.svn.showcase.services.CrudService;
import su.svn.showcase.services.NewsEntryBaseCrudService;
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
import static su.svn.showcase.domain.TestData.getNewsEntry1;
import static su.svn.showcase.dto.TestData.getNewsEntryBaseDto1;
import static su.svn.showcase.services.impl.support.EntityManagerFactoryProducer.configure;

@DisplayName("A NewsEntryBaseCrudServiceImplTest unit test cases")
@AddPackages(value = {NewsEntryDao.class, CrudService.class})
@ExtendWith({JtaEnvironment.class, WeldJunit5Extension.class})
class NewsEntryBaseCrudServiceImplTest {

    @Inject
    private BeanManager beanManager;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PgPU", configure(beanManager));

    @WeldSetup
    private
    WeldInitiator weld = WeldInitiator.from(
            NewsEntryDaoJpa.class,
            NewsEntryBaseCrudServiceImpl.class,
            EntityManagerFactoryProducer.class,
            EntityManagerProducer.class)
            .activate(RequestScoped.class)
            .setEjbFactory(ejbFactory())
            .setPersistenceContextFactory(injectionPoint -> emf.createEntityManager())
            .setPersistenceUnitFactory(injectionPoint -> emf)
            .inject(this)
            .build();

    private NewsEntryDao mockDao = mock(NewsEntryDao.class);

    private Map<String, Object> ejbMap = new HashMap<String, Object>() {{
        put(null,                         mockDao);
        put(NewsEntryDao.class.getName(), mockDao);
    }};

    private Function<InjectionPoint, Object> ejbFactory() {
        return ip -> ejbMap.get(ip.getAnnotated().getBaseType().getTypeName());
    }

    @Inject
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    private NewsEntry entity;
    private NewsEntryBaseDto dto;

    @BeforeEach
    void setUp() {
        entity = getNewsEntry1();
        dto = getNewsEntryBaseDto1();
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
    void create(NewsEntryBaseCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.save(any())).thenReturn(entity);
        service.create(dto);
    }

    @Test
    void readById(NewsEntryBaseCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.findById(any())).thenReturn(Optional.of(entity));
        Assertions.assertEquals(dto, service.readById(entity.getId()));
    }

    @Test
    void readRange(NewsEntryBaseCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.findById(any())).thenReturn(Optional.of(entity));
        List<NewsEntryBaseDto> testList = service.readRange(0, Integer.MAX_VALUE);
        Assertions.assertTrue(testList.isEmpty());
    }

    @Test
    void update(NewsEntryBaseCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.save(any())).thenReturn(entity);
        service.update(dto);
    }

    @Test
    void delete(NewsEntryBaseCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.save(any())).thenReturn(entity);
        service.delete(dto.getId());
    }
}