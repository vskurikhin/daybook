/*
 * This file was last modified at 2020.03.03 22:49 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagBaseCrudServiceImplTest.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.jboss.weld.junit5.auto.AddPackages;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import su.svn.showcase.dao.TagDao;
import su.svn.showcase.dao.jpa.TagDaoJpa;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.TagBaseDto;
import su.svn.showcase.services.TagBaseCrudService;
import su.svn.showcase.services.CrudService;
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
import static su.svn.showcase.domain.TestData.cloneTag1;
import static su.svn.showcase.dto.TestData.cloneTagBaseDto1;
import static su.svn.showcase.services.impl.support.EntityManagerFactoryProducer.configure;

@DisplayName("A TagStorageServiceImplTest unit test cases")
@AddPackages(value = {TagDao.class, CrudService.class})
@ExtendWith({JtaEnvironment.class, WeldJunit5Extension.class})
class TagBaseCrudServiceImplTest {

    @Inject
    private BeanManager beanManager;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PgPU", configure(beanManager));

    @WeldSetup
    private
    WeldInitiator weld = WeldInitiator.from(
            TagDaoJpa.class,
            TagBaseCrudServiceImpl.class,
            EntityManagerFactoryProducer.class,
            EntityManagerProducer.class)
            .activate(RequestScoped.class)
            .setEjbFactory(ejbFactory())
            .setPersistenceContextFactory(injectionPoint -> emf.createEntityManager())
            .setPersistenceUnitFactory(injectionPoint -> emf)
            .inject(this)
            .build();

    private TagDao mockDao = mock(TagDao.class);

    private Map<String, Object> ejbMap = new HashMap<String, Object>() {{
        put(null,                   mockDao);
        put(TagDao.class.getName(), mockDao);
    }};

    private Function<InjectionPoint, Object> ejbFactory() {
        return ip -> ejbMap.get(ip.getAnnotated().getBaseType().getTypeName());
    }

    @Inject
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    private Tag entity;
    private TagBaseDto dto;

    @BeforeEach
    void setUp() {
        entity = cloneTag1();
        dto = cloneTagBaseDto1();
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
    void create(TagBaseCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.save(any())).thenReturn(entity);
        service.create(dto);
    }

    @Test
    void readById(TagBaseCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.findById(any())).thenReturn(Optional.of(entity));
        Assertions.assertEquals(dto, service.readById(entity.getId()));
    }

    @Test
    void readRange(TagBaseCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.findById(any())).thenReturn(Optional.of(entity));
        List<TagBaseDto> testList = service.readRange(0, Integer.MAX_VALUE);
        Assertions.assertTrue(testList.isEmpty());
    }

    @Test
    void update(TagBaseCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.save(any())).thenReturn(entity);
        Assertions.assertThrows(su.svn.showcase.exceptions.ErrorCase.class, () -> service.update(dto));
    }

    @Test
    void delete(TagBaseCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.save(any())).thenReturn(entity);
        service.delete(dto.getId());
    }
}