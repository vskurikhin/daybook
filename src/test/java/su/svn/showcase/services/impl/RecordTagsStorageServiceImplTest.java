/*
 * This file was last modified at 2020.03.03 22:49 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordTagsStorageServiceImplTest.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.jboss.weld.junit5.auto.AddPackages;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import su.svn.showcase.dao.RecordDao;
import su.svn.showcase.dao.TagDao;
import su.svn.showcase.dao.jpa.RecordDaoJpa;
import su.svn.showcase.dao.jpa.TagDaoJpa;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.dto.TagBaseDto;
import su.svn.showcase.services.RecordTagsStorageService;
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
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static su.svn.showcase.domain.TestData.*;
import static su.svn.showcase.dto.TestData.*;
import static su.svn.showcase.services.impl.support.EntityManagerFactoryProducer.configure;
import static su.svn.utils.TestData.newList;

@DisplayName("A NewsEntryTagsStorageServiceImplTest unit test cases")
@AddPackages(value = {RecordDao.class, RecordTagsStorageService.class})
@ExtendWith({JtaEnvironment.class, WeldJunit5Extension.class})
class RecordTagsStorageServiceImplTest {

    @Inject
    private BeanManager beanManager;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PgPU", configure(beanManager));

    @WeldSetup
    private
    WeldInitiator weld = WeldInitiator.from(
            RecordDaoJpa.class,
            TagDaoJpa.class,
            RecordTagsUtxServiceImpl.class,
            EntityManagerFactoryProducer.class,
            EntityManagerProducer.class)
            .activate(RequestScoped.class)
            .setEjbFactory(ejbFactory())
            .setPersistenceContextFactory(injectionPoint -> emf.createEntityManager())
            .setPersistenceUnitFactory(injectionPoint -> emf)
            .inject(this)
            .build();

    private TagDao tagDao = mock(TagDao.class);
    private RecordDao mockDao = mock(RecordDao.class);
    private RecordTagsStorageService mockService = mock(RecordTagsStorageService.class);

    private Map<String, Object> ejbMap = new HashMap<String, Object>() {{
        put(null,                                     mockDao);
        put(RecordDao.class.getName(),                mockDao);
        put(TagDao.class.getName(),                   tagDao);
        put(RecordTagsStorageService.class.getName(), mockService);
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
    private Tag tag;
    private TagBaseDto tagDto;

    @BeforeEach
    void setUp() {
        entity = cloneRecord0();
        dto = cloneRecordFullDto0();
        tag = cloneTag1();
        tagDto = cloneTagBaseDto1();
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
    void create(RecordTagsStorageService service) {
        Assertions.assertNotNull(service);
        when(mockDao.fetchById(any())).thenReturn(Optional.of(entity));
        when(tagDao.outerSection(any())).thenReturn(newList(tag.getTag()));
        when(tagDao.findAllByTagIn(any())).thenReturn(newList(tag));
        service.addTagsToRecord(dto, newList(tagDto) );
    }
}
