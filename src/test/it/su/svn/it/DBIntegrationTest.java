/*
 * This file was last modified at 2020.02.16 11:20 by Victor N. Skurikhin.
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
import su.svn.showcase.dto.NewsEntryBaseDto;
import su.svn.showcase.dto.RoleBaseDto;
import su.svn.showcase.dto.TagBaseDto;
import su.svn.showcase.services.NewsEntryBaseCrudService;
import su.svn.showcase.services.RoleBaseCrudService;
import su.svn.showcase.services.TagBaseCrudService;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertNotNull;

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
    TagBaseCrudService tagBaseCrudService;

    @Inject
    RoleBaseCrudService roleBaseCrudService;

    @Inject
    NewsEntryBaseCrudService newsEntryBaseCrudService;

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
        List<UserRole> entity = newList(cloneUserRole(1));
        List<UserRole> expected = newList(cloneUserRole(1));
        saveAll(userRoleDao, entity, expected);
    }

    @Test
    @InSequence(2300)
    public void test_newsGroupDao_save() throws Exception {
        NewsGroup entity = clean(cloneNewsGroup(0));
        NewsGroup expected = clean(cloneNewsGroup(0));
        save(newsGroupDao, entity, expected);
    }

    @Test
    @InSequence(2450)
    public void test_recordDao_save() throws Exception {
        Record entity = clean(cloneRecord(0));
        Record expected = clean(cloneRecord(0));
        entity.setUserLogin(cloneUserLogin(0));
        expected.setUserLogin(cloneUserLogin(0));
        System.out.println("entity = " + entity);
        save(recordDao, entity, expected);
    }

    @Test
    @InSequence(2451)
    public void test_recordDao_saveAll() throws Exception {
        Record entity = clean(cloneRecord(1));
        Record expected = clean(cloneRecord(1));
        entity.setUserLogin(cloneUserLogin(1));
        expected.setUserLogin(cloneUserLogin(1));
        List<Record> records = newList(entity);
        List<Record> expectedList = newList(expected);
        System.out.println("entity = " + entity);
        saveAll(recordDao, records, expectedList);
    }

    @Test
    @InSequence(2500)
    public void test_newsEntryDao_save() throws Exception {
        NewsEntry entity = clean(cloneNewsEntry(0));
        NewsEntry expected = clean(cloneNewsEntry(0));
        System.out.println("entity = " + entity);
        save(newsEntryDao, entity, expected);
    }

    @Test
    @InSequence(2501)
    public void test_newsEntryDao_saveAll() throws Exception {
        List<NewsEntry> entity = newList(clean(cloneNewsEntry(1)));
        List<NewsEntry> expected = newList(clean(cloneNewsEntry(1)));
        System.out.println("entity = " + entity);
        saveAll(newsEntryDao, entity, expected);
    }

    /*
    @Test
    @InSequence(2502)
    public void test_newsEntryBaseCrudService_create() throws Exception {
        NewsEntryBaseDto entity = cloneNewsEntryBaseDto(2);
        newsEntryBaseCrudService.create(entity);
    }
     */

    @Test
    @InSequence(99999)
    public void tearDown() throws SystemException, InterruptedException {
        Thread.sleep(99999);
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