/*
 * This file was last modified at 2020.04.14 20:47 by Victor N. Skurikhin.
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
import su.svn.showcase.domain.Record;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.dto.jdo.TagJdo;
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
import javax.transaction.*;
import java.util.*;
import java.util.function.Function;

import static su.svn.showcase.domain.TestData.*;
import static su.svn.showcase.dto.TestData.*;
import static su.svn.showcase.services.impl.support.EntityManagerFactoryProducer.configure;
import static su.svn.utils.TestData.newList;

@DisplayName("A RecordTagsStorageServiceImplTest unit test cases")
@AddPackages(value = {RecordDao.class, RecordTagsStorageService.class})
@ExtendWith({JtaEnvironment.class, WeldJunit5Extension.class})
class RecordTagsStorageServiceImplTest {

    @Inject
    private BeanManager beanManager;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PgPU", configure(beanManager));

    @WeldSetup
    private
    WeldInitiator weld = WeldInitiator.from(
            RecordTagsUtxServiceImpl.class,
            EntityManagerFactoryProducer.class,
            EntityManagerProducer.class)
            .activate(RequestScoped.class)
            .setEjbFactory(ejbFactory())
            .setPersistenceContextFactory(injectionPoint -> emf.createEntityManager())
            .setPersistenceUnitFactory(injectionPoint -> emf)
            .inject(this)
            .build();

    private Map<String, Object> ejbMap = new HashMap<String, Object>();

    private Function<InjectionPoint, Object> ejbFactory() {
        return ip -> ejbMap.get(ip.getAnnotated().getBaseType().getTypeName());
    }

    @Inject
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    private Record entity;
    private RecordFullDto dto;
    private TagJdo tagDto;

    @BeforeEach
    void setUp() throws Exception {
        entity = cloneRecord0();
        dto = cloneRecordFullDto0();
        tagDto = cloneTagFullDto1();
        userTransaction.begin();
        entity.setNewsEntry(null);
        entity.setTags(Collections.emptySet());
        entityManager.persist(entity);
        entityManager.flush();
        userTransaction.commit();
    }

    @AfterEach
    void tearDown() throws Exception {
        userTransaction.begin();
        entityManager.createQuery("DELETE FROM Record").executeUpdate();
        entityManager.createQuery("DELETE FROM UserLogin").executeUpdate();
        entityManager.createQuery("DELETE FROM Tag").executeUpdate();
        userTransaction.commit();
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
        service.addTagsToRecord(dto, newList(tagDto));
    }
}
