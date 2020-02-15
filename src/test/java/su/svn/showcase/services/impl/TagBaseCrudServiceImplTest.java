/*
 * This file was last modified at 2020.02.14 10:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagStorageServiceImplTest.java
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
import su.svn.showcase.services.TagCrudService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static su.svn.showcase.domain.TestData.getTag1;
import static su.svn.showcase.dto.TestData.getTagBaseDto1;
import static su.svn.showcase.services.impl.support.EntityManagerFactoryProducer.configure;

@DisplayName("A TagStorageServiceImplTest unit test cases")
@AddPackages(value = {TagDao.class, TagCrudService.class})
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

    private TagDao mockTagDao = mock(TagDao.class);

    private Map<String, Object> ejbMap = new HashMap<String, Object>() {{
        put(null,                   mockTagDao);
        put(TagDao.class.getName(), mockTagDao);
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
        entity = getTag1();
        dto = getTagBaseDto1();
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("Can inject entity manager and user transaction")
    @Test
    void canInject_entityManager() {
        assertNotNull(entityManager);
        assertNotNull(userTransaction);
    }

    @Test
    void create(TagCrudService service) {
        assertNotNull(service);
        when(mockTagDao.save(any())).thenReturn(entity);
        service.create(dto);
    }

    @Test
    void readById(TagCrudService service) {
        assertNotNull(service);
        when(mockTagDao.findById(any())).thenReturn(Optional.of(entity));
        Assertions.assertEquals(dto, service.readById(entity.getId()));
    }

    @Test
    void readRange(TagCrudService service) {
        assertNotNull(service);
        when(mockTagDao.findById(any())).thenReturn(Optional.of(entity));
        when(mockTagDao.save(any())).thenReturn(entity);
        List<TagBaseDto> testList = (List<TagBaseDto>) service.readRange(0, Integer.MAX_VALUE);
        System.out.println("testList = " + testList);
    }

    @Test
    void update(TagCrudService service) {
        assertNotNull(service);
        when(mockTagDao.save(any())).thenReturn(entity);
        service.create(dto);
    }

    @Test
    void delete(TagCrudService service) {
        assertNotNull(service);
        when(mockTagDao.save(any())).thenReturn(entity);
        service.create(dto);
    }
}