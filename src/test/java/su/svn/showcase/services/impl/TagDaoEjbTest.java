/*
 * This file was last modified at 2020.02.13 21:57 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagDaoJpaTest.java
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
import su.svn.showcase.dao.*;
import su.svn.showcase.dao.jpa.TagDaoEjb;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.services.impl.support.EntityManagerFactoryProducer;
import su.svn.showcase.services.impl.support.EntityManagerProducer;
import su.svn.showcase.services.impl.support.JtaEnvironment;
import su.svn.showcase.utils.StringUtil;

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
import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static su.svn.showcase.domain.TestData.clean;
import static su.svn.showcase.domain.TestData.cloneTag1;
import static su.svn.showcase.services.impl.support.EntityManagerFactoryProducer.configure;

@DisplayName("A TagDaoJpaTest unit test cases")
@AddPackages(value = {TagDaoEjb.class})
@ExtendWith({JtaEnvironment.class, WeldJunit5Extension.class})
class TagDaoEjbTest {

    @Inject
    private BeanManager beanManager;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PgPU", configure(beanManager));

    @WeldSetup
    private
    WeldInitiator weld = WeldInitiator.from(
            TagDaoEjb.class,
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
        put(null,                           mockTagDao);
        put(TagDao.class.getName(),         mockTagDao);
    }};

    private Function<InjectionPoint, Object> ejbFactory() {
        return ip -> ejbMap.get(ip.getAnnotated().getBaseType().getTypeName());
    }

    @Inject
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    private Tag entity;

    @BeforeEach
    void createNew() {
        entity = clean(cloneTag1());
    }

        @DisplayName("Can inject entity manager and user transaction")
    @Test
    void canInject_entityManager() {
        assertNotNull(entityManager);
        assertNotNull(userTransaction);
    }

    @DisplayName("Test when TagDaoJpa findById return empty")
    @Test
    void whenTagDao_findById_shouldBeReturnEmptyOptional() throws SystemException, NotSupportedException {
        userTransaction.begin();
        TagDao tagDao = weld.select(TagDaoEjb.class).get();
        Optional<Tag> test= tagDao.findById(StringUtil.generateStringId());
        assertNotNull(test);
        assertFalse(test.isPresent());
        userTransaction.rollback();
    }

    @DisplayName("Test when TagDaoJpa save success")
    @Test
    void whenTagDao_findAll_shouldBeReturnEmptyList() throws SystemException, NotSupportedException {
        userTransaction.begin();
        TagDao tagDao = weld.select(TagDaoEjb.class).get();
        List<Tag> testList = tagDao.findAll();
        assertNotNull(testList);
        assertTrue(testList.isEmpty());
        userTransaction.rollback();
    }

    @DisplayName("Test when TagDaoJpa save is success")
    @Test
    void whenTagDao_save_success() throws SystemException, NotSupportedException {
        userTransaction.begin();
        TagDao dao = weld.select(TagDaoEjb.class).get();
        Tag test = dao.save(entity);
        Assertions.assertNotNull(test);
        Assertions.assertEquals(entity, test);
        userTransaction.rollback();
    }

    @DisplayName("Test when TagDaoJpa save of set is success")
    @Test
    void whenTagDao_save_iterable_success() throws SystemException, NotSupportedException {
        userTransaction.begin();
        TagDao dao = weld.select(TagDaoEjb.class).get();
        List<Tag> testList = new ArrayList<Tag>() {{ add(entity); }};
        Iterable<Tag> result = dao.saveAll(testList);
        assertNotNull(result);
        assertEquals(testList, result);
        userTransaction.rollback();
    }

    @DisplayName("Test when TagDaoJpa delete failed")
    @Test
    void whenTagDao_delete_shouldBeReturnFalse() throws SystemException, NotSupportedException {
        userTransaction.begin();
        TagDao dao = weld.select(TagDaoEjb.class).get();
        dao.delete(StringUtil.generateStringId());
        userTransaction.rollback();
    }

    @DisplayName("Test when TagDaoJpa outer section")
    @Test
    void whenTagDao_outerSection() throws SystemException, NotSupportedException {
        userTransaction.begin();
        TagDao dao = weld.select(TagDaoEjb.class).get();
        dao.save(entity);
        List<String> testTags = new ArrayList<String>() {{
            add("tag1");
            add("tagTest1");
            add("tag2");
        }};
        Set<String> expected = new HashSet<String>() {{
            add("tag1");
            add("tag2");
        }};
        List<String> list = dao.outerSection(testTags);
        Set<String> result = new HashSet<String>(list);
        assertEquals(expected, result);
        userTransaction.rollback();
    }
}
