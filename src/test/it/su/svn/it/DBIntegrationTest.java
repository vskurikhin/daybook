/*
 * This file was last modified at 2020.03.03 22:49 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * DBIntegrationTest.java
 * $Id$
 */

package su.svn.it;

import javax.inject.Inject;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import junit.framework.TestCase;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import su.svn.showcase.dao.*;
import su.svn.showcase.domain.*;
import su.svn.showcase.dto.*;
import su.svn.showcase.services.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RunWith(Arquillian.class)
public class DBIntegrationTest extends BaseIntegrationTest {

    @Inject
    TagDao tagDao;

    @Inject
    UserLoginDao userLoginDao;

    @Inject
    RoleDao roleDao;

    @Inject
    UserRoleDao userRoleDao;

    @Inject
    NewsGroupDao newsGroupDao;

    @Inject
    RecordDao recordDao;

    @Inject
    NewsEntryDao newsEntryDao;

    @Inject
    ArticleDao articleDao;

    @Inject
    TagBaseCrudService tagBaseCrudService;

    @Inject
    RoleBaseCrudService roleBaseCrudService;

    @Inject
    UserRoleFullCrudService userRoleFullCrudService;

    @Inject
    NewsEntryFullCrudService newsEntryFullCrudService;

    @Inject
    RecordFullCrudService recordFullCrudService;

    @Inject
    UserTransaction userTransaction;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "su.svn.showcase")
                .addAsResource("META-INF/beans.xml")
                .addAsResource("META-INF/it-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("project-it.yaml", "project-defaults.yaml")
                .addAsResource("META-INF/arquillian.xml");
    }

    @Test
    @InSequence(1)
    public void setUp() {
        super.clearDB();
    }

    @Test
    @InSequence(1300)
    public void test_tagDao_save() throws Exception {
        Tag entity = cloneTag(0);
        Tag expected = cloneTag(0);
        save(tagDao, entity, expected);
    }

    @Test
    @InSequence(1301)
    public void test_tagDao_saveAll() throws Exception {
        List<Tag> entity = newList(cloneTag(1));
        List<Tag> expected = newList(cloneTag(1));
        saveAll(tagDao, entity, expected);
    }

    @Test
    @InSequence(1302)
    public void test_tagBaseCrudService_create() throws Exception {
        TagBaseDto entity = cloneTagBaseDto(2);
        tagBaseCrudService.create(entity);
    }

    @Test
    @InSequence(2000)
    public void test_userLoginDao_save() throws Exception {
        UserLogin entity = clean(cloneUserLogin(0)); // no roles yet
        UserLogin expected = clean(cloneUserLogin(0)); // no roles yet
        save(userLoginDao, entity, expected);
    }

    @Test
    @InSequence(2001)
    public void test_userLoginDao_saveAll() throws Exception {
        List<UserLogin> entity = newList(clean(cloneUserLogin(1))); // no roles yet
        List<UserLogin> expected = newList(clean(cloneUserLogin(1))); // no roles yet
        saveAll(userLoginDao, entity, expected);
    }

    @Test
    @InSequence(2002)
    public void test_userLoginDao_save_2() throws Exception {
        UserLogin entity = clean(cloneUserLogin(2)); // TODO no roles yet
        UserLogin expected = clean(cloneUserLogin(2)); // TODO no roles yet
        save(userLoginDao, entity, expected);
    }

    @Test
    @InSequence(2003)
    public void test_userLoginDao_save_3() throws Exception {
        UserLogin entity = clean(cloneUserLogin(3)); // TODO no roles yet
        UserLogin expected = clean(cloneUserLogin(3)); // TODO no roles yet
        save(userLoginDao, entity, expected);
    }

    @Test
    @InSequence(2100)
    public void test_roleDao_save() throws Exception {
        Role entity = cloneRole(0);
        Role expected = cloneRole(0);
        save(roleDao, entity, expected);
    }

    @Test
    @InSequence(2101)
    public void test_roleDao_saveAll() throws Exception {
        List<Role> entity = newList(cloneRole(1));
        List<Role> expected = newList(cloneRole(1));
        saveAll(roleDao, entity, expected);
    }

    @Test
    @InSequence(2102)
    public void test_roleBaseCrudService_create() throws Exception {
        RoleBaseDto entity = cloneRoleBaseDto(2);
        roleBaseCrudService.create(entity);
    }

    @Test
    @InSequence(2200)
    public void test_userRoleDao_save() throws Exception {
        UserRole entity = cloneUserRole(0);
        UserRole expected = cloneUserRole(0);
        save(userRoleDao, entity, expected);
    }

    @Test
    @InSequence(2201)
    public void test_userRoleDao_saveAll() throws Exception {
        List<UserRole> entityList = newList(cloneUserRole(1));
        List<UserRole> expected = newList(cloneUserRole(1));
        saveAll(userRoleDao, entityList, expected);
    }

    @Test
    @InSequence(2202)
    public void test_userRoleFullCrudService_create() throws Exception {
        UserRoleFullDto dto = cloneUserRoleFullDto(2);
        userRoleFullCrudService.create(dto);
    }

    @Test
    @InSequence(2300)
    public void test_newsGroupDao_save() throws Exception {
        NewsGroup entity = clean(cloneNewsGroup(0));
        NewsGroup expected = clean(cloneNewsGroup(0));
        save(newsGroupDao, entity, expected);
    }

    @Test
    @InSequence(2301)
    public void test_newsGroupDao_saveAll() throws Exception {
        List<NewsGroup> entityList = newList(clean(cloneNewsGroup(1)));
        List<NewsGroup> expected = newList(clean(cloneNewsGroup(1)));
        saveAll(newsGroupDao, entityList, expected);
    }

    @Test
    @InSequence(2302)
    public void test_newsGroupDao_save_2() throws Exception {
        NewsGroup entity = clean(cloneNewsGroup(2));
        NewsGroup expected = clean(cloneNewsGroup(2));
        save(newsGroupDao, entity, expected);
    }

    @Test
    @InSequence(2303)
    public void test_newsGroupDao_save_3() throws Exception {
        NewsGroup entity = clean(cloneNewsGroup(3));
        NewsGroup expected = clean(cloneNewsGroup(3));
        save(newsGroupDao, entity, expected);
    }

    @Test
    @InSequence(2450)
    public void test_recordDao_save() throws Exception {
        Record entity = clean(cloneRecord(0));
        Record expected = clean(cloneRecord(0));
        entity.setUserLogin(cloneUserLogin(0));
        expected.setUserLogin(cloneUserLogin(0));
        save(recordDao, entity, expected);
    }

    @Test
    @InSequence(2451)
    public void test_recordDao_saveAll() throws Exception {
        Record entityList = clean(cloneRecord(1));
        Record expected = clean(cloneRecord(1));
        entityList.setUserLogin(cloneUserLogin(1));
        expected.setUserLogin(cloneUserLogin(1));
        List<Record> records = newList(entityList);
        List<Record> expectedList = newList(expected);
        saveAll(recordDao, records, expectedList);
    }

    @Test
    @InSequence(2452)
    public void test_recordFullCrudService_create() throws Exception {
        RecordFullDto dto = cloneRecordFullDto(2);
        dto.setTags(Collections.emptySet());
        recordFullCrudService.create(dto);
    }

    @Test
    @InSequence(2500)
    public void test_newsEntryDao_save() throws Exception {
        NewsEntry entity = clean(cloneNewsEntry(0));
        NewsEntry expected = clean(cloneNewsEntry(0));
        save(newsEntryDao, entity, expected);
    }

    @Test
    @InSequence(2501)
    public void test_newsEntryDao_saveAll() throws Exception {
        List<NewsEntry> entityList = newList(clean(cloneNewsEntry(1)));
        List<NewsEntry> expected = newList(clean(cloneNewsEntry(1)));
        saveAll(newsEntryDao, entityList, expected);
    }

    @Test
    @InSequence(2502)
    public void test_newsEntryFullCrudService_create() throws Exception {
        NewsEntryFullDto dto = cloneNewsEntryFullDto(3);
        newsEntryFullCrudService.create(dto);
    }


    @Test
    @InSequence(2900)
    public void test_articleDao_save() throws Exception {
        Article entity = clean(cloneArticle(0));
        Article expected = clean(cloneArticle(0));
        save(articleDao, entity, expected);
    }


    @Test
    @InSequence(99999)
    public void tearDown() throws SystemException, InterruptedException {
        // Thread.sleep(99999);
        if (userTransaction.getStatus() != Status.STATUS_NO_TRANSACTION) {
            userTransaction.rollback();
        }
        super.clearDB();
    }

    public <D extends Dao<K, E>, E extends DBEntity<K>, K> void save(D dao, E entity, E expected) throws Exception {
        userTransaction.begin();
        E test = dao.save(entity);
        TestCase.assertNotNull(test);
        TestCase.assertEquals(expected, test);
        userTransaction.commit();
    }

    public <D extends Dao<K, E>, E extends DBEntity<K>, K> void saveAll(D dao, Iterable<E> iterable, Iterable<E> expected)
            throws Exception {
        userTransaction.begin();
        Iterable<E> test = dao.saveAll(iterable);
        TestCase.assertNotNull(test);
        TestCase.assertEquals(expected, test);
        userTransaction.commit();
    }

    public <D extends Dao<K, E>, E extends DBEntity<K>, K> void testFindById(D dao, K id, E expected) throws Exception {
        userTransaction.begin();
        E entity = dao.findById(id).get();
        TestCase.assertNotNull(entity);
        TestCase.assertEquals(expected, entity);
        userTransaction.commit();
    }

    public <D extends Dao<K, E>, E extends DBEntity<K>, K> void testFindAll(D dao, E expected) throws Exception {
        userTransaction.begin();
        Collection<E> collection = dao.findAll();
        TestCase.assertNotNull(collection);
        TestCase.assertEquals(1, collection.size());
        TestCase.assertEquals(expected, collection.iterator().next());
        userTransaction.commit();
    }
}
//EOF
