/*
 * This file was last modified at 2020.03.15 18:57 by Victor N. Skurikhin.
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
import su.svn.showcase.converters.*;
import su.svn.showcase.converters.impl.*;
import su.svn.showcase.dao.RecordDao;
import su.svn.showcase.dao.UserLoginDao;
import su.svn.showcase.dao.jpa.RecordDaoEjb;
import su.svn.showcase.dao.jpa.UserLoginDaoEjb;
import su.svn.showcase.domain.Article;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.NewsEntryFullDto;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.services.CrudService;
import su.svn.showcase.services.RecordFullCrudService;
import su.svn.showcase.services.impl.support.EntityManagerFactoryProducer;
import su.svn.showcase.services.impl.support.EntityManagerProducer;
import su.svn.showcase.services.impl.support.JtaEnvironment;
import su.svn.utils.InputStreamUtil;

import javax.ejb.EJB;
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
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static su.svn.showcase.domain.TestData.*;
import static su.svn.showcase.dto.TestData.cloneRecordFullDto1;
import static su.svn.showcase.services.impl.support.EntityManagerFactoryProducer.configure;

@SuppressWarnings("Convert2Diamond")
@DisplayName("A RecordFullCrudServiceImplTest unit test cases")
@AddPackages(value = {RecordDao.class, CrudService.class})
@ExtendWith({JtaEnvironment.class, WeldJunit5Extension.class})
class RecordFullCrudServiceImplTest {

    private static final Class<?> tClass = RecordFullCrudServiceImplTest.class;
    private static final String resourceNamePrefix = "/META-INF/sql/" + tClass.getSimpleName();
    private static final UUID UUID10 = UUID.fromString("00000000-0000-0000-0000-000000000010");

    @Inject
    private BeanManager beanManager;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PgPU", configure(beanManager));

    static RecordDao recordDao = new RecordDaoEjb();
    static UserLoginDao userLoginDao = new UserLoginDaoEjb();

    static RecordDao mockDao = mock(RecordDao.class);
    static UserLoginDao mockUserLoginDao = mock(UserLoginDao.class);
    static RecordFullCrudService mockService = mock(RecordFullCrudService.class);

    static ArticleConverter articleFullConverter = new ArticleFullConverter();
    static NewsEntryConverter newsEntryFullConverter = new NewsEntryFullConverter();
    static NewsGroupConverter newsGroupBaseConverter = new NewsGroupBaseConverter();
    static NewsLinksConverter newsLinksFullConverter = new NewsLinksFullConverter();
    static RecordConverter recordFullConverter = new RecordFullConverter();
    static TagConverter tagBaseConverter = new TagBaseConverter();
    static UserLoginConverter userOnlyLoginConverter = new UserOnlyLoginConverter();

    static RecordFullCrudService recordFullCrudService = new RecordFullCrudServiceImpl();

    private Map<String, Object> ejbMap = new HashMap<String, Object>() {{
        put("RecordDaoEjb", recordDao);
        put("UserLoginDaoEjb", userLoginDao);

        put("ArticleFullConverter", articleFullConverter);
        put("NewsEntryFullConverter", newsEntryFullConverter);
        put("NewsGroupBaseConverter", newsGroupBaseConverter);
        put("NewsLinksFullConverter", newsLinksFullConverter);
        put("RecordFullConverter", recordFullConverter);
        put("TagBaseConverter", tagBaseConverter);
        put("UserOnlyLoginConverter", userOnlyLoginConverter);

        put("RecordFullCrudService", recordFullCrudService);
    }};

    private Function<InjectionPoint, Object> ejbFactory() {
        return ip -> {
            String name = ip.getAnnotated().getAnnotation(EJB.class).beanName();
            if (name == null) {
                name = ip.getAnnotated().getBaseType().getTypeName();
            }
            System.err.println("beanName: " + name);
            return ejbMap.get(name);
        };
    }

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
            .inject(recordDao)
            .inject(userLoginDao)
            .inject(recordFullConverter)
            .inject(newsEntryFullConverter)
            .inject(recordFullCrudService)
            .inject(this)
            .build();

    @Inject
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    @EJB(beanName = "RecordFullCrudService")
    RecordFullCrudService service;

    private Record entity;
    private RecordFullDto dto;
    private UserLogin userLogin;


    @BeforeEach
    void setUp() throws Exception {
        InputStream is = tClass.getResourceAsStream(resourceNamePrefix + "_setUp.sql");
        userTransaction.begin();
        InputStreamUtil.readAndExecuteLine(is, sql ->
                entityManager.createNativeQuery(sql).executeUpdate());
        userTransaction.commit();

        entity = cloneRecord1();
        entity.setNewsEntry(cloneNewsEntry1());
        dto = cloneRecordFullDto1();
        ((NewsEntryFullDto) dto.getNewsEntry()).setRecord(dto);
        userLogin = cloneUserLogin1();
    }

    @AfterEach
    void tearDown() throws Exception {
        InputStream is = tClass.getResourceAsStream(resourceNamePrefix + "_tearDown.sql");
        userTransaction.begin();
        InputStreamUtil.readAndExecuteLine(is, sql ->
                entityManager.createNativeQuery(sql).executeUpdate());
        userTransaction.commit();
    }

    @DisplayName("Can inject entity manager and user transaction")
    @Test
    void canInject_entityManager() {
        Assertions.assertNotNull(entityManager);
        Assertions.assertNotNull(userTransaction);
        Assertions.assertNotNull(service);
    }

    @Test
    void create() throws Exception {
        // service.create(dto); // TODO create via children entity service
    }

    @Test
    void readById(RecordFullCrudService service) throws Exception {
         userTransaction.begin();
         service.readById(UUID.fromString("00000000-0000-0000-0000-000000000010"));
         userTransaction.rollback();
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

    @Test
    void count(RecordFullCrudService service) {
        Assertions.assertNotNull(service);
        service.count();
    }

    @Test
    void countByDay(RecordFullCrudService service) {
        Assertions.assertNotNull(service);
        service.countByDay(LocalDate.now());
    }
}
