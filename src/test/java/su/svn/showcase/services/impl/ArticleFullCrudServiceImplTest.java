/*
 * This file was last modified at 2020.03.15 18:57 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleFullCrudServiceImplTest.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.jboss.weld.junit5.auto.AddPackages;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import su.svn.showcase.dao.ArticleDao;
import su.svn.showcase.dao.RecordDao;
import su.svn.showcase.dao.UserLoginDao;
import su.svn.showcase.dao.jpa.ArticleDaoEjb;
import su.svn.showcase.domain.Article;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.*;
import su.svn.showcase.services.CrudService;
import su.svn.showcase.services.ArticleFullCrudService;
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
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static su.svn.showcase.domain.TestData.*;
import static su.svn.showcase.dto.TestData.*;
import static su.svn.showcase.services.impl.support.EntityManagerFactoryProducer.configure;

@SuppressWarnings("Convert2Diamond")
@DisplayName("A ArticleFullCrudServiceImplTest unit test cases")
@AddPackages(value = {ArticleDao.class, CrudService.class})
@ExtendWith({JtaEnvironment.class, WeldJunit5Extension.class})
class ArticleFullCrudServiceImplTest
{

    @Inject
    private BeanManager beanManager;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PgPU", configure(beanManager));

    @WeldSetup
    private
    WeldInitiator weld = WeldInitiator.from(
            ArticleDaoEjb.class,
            ArticleFullCrudServiceImpl.class,
            EntityManagerFactoryProducer.class,
            EntityManagerProducer.class)
            .activate(RequestScoped.class)
            .setEjbFactory(ejbFactory())
            .setPersistenceContextFactory(injectionPoint -> emf.createEntityManager())
            .setPersistenceUnitFactory(injectionPoint -> emf)
            .inject(this)
            .build();

    private ArticleDao mockDao = mock(ArticleDao.class);
    private RecordDao mockRecordDao = mock(RecordDao.class);
    private UserLoginDao mockUserLoginDao = mock(UserLoginDao.class);
    private ArticleFullCrudService mockService = mock(ArticleFullCrudService.class);

    private Map<String, Object> ejbMap = new HashMap<String, Object>() {{
        put(null,                                     mockDao);
        put(ArticleDao.class.getName(),             mockDao);
        put(RecordDao.class.getName(),                mockRecordDao);
        put(ArticleFullCrudService.class.getName(), mockService);
        put(UserLoginDao.class.getName(),             mockUserLoginDao);
    }};

    private Function<InjectionPoint, Object> ejbFactory() {
        return ip -> ejbMap.get(ip.getAnnotated().getBaseType().getTypeName());
    }

    @Inject
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    private Article entity;
    private ArticleFullDto dto;
    private NewsGroupBaseDto newsGroupDto;
    private Record record;
    private RecordDto recordDto;
    private UserLogin userLogin;
    private UserLoginDto userLoginDto;

    @BeforeEach
    void setUp() {
        entity = cloneArticle1();
        entity.getRecord().setArticle(entity);
        entity.getRecord().setType(ArticleFullDto.class.getSimpleName());
        dto = cloneArticleFullDto1();
        newsGroupDto = cloneNewsGroupBaseDto1();
        record = cloneRecord1();
        recordDto = cloneRecordFullDto1();
        recordDto.setType(ArticleFullDto.class.getSimpleName());
        dto.setRecord(recordDto);
        userLogin = cloneUserLogin1();
        userLoginDto = cloneUserOnlyLoginBaseDto1();
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
    void create(ArticleFullCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.save(any())).thenReturn(entity);
        when(mockRecordDao.save(any())).thenReturn(record);
        when(mockUserLoginDao.findById(any())).thenReturn(Optional.of(userLogin));
        when(mockUserLoginDao.findWhereLogin(any())).thenReturn(Optional.of(userLogin));
        service.create(dto);
    }

    private static final UUID UUID1 = UUID.randomUUID();
    private static final LocalDateTime NOW1 = LocalDateTime.now();

    @Test
    void create_new_random(ArticleFullCrudService service) {

        RecordFullDto recordDto = RecordFullDto.builder()
            .id(UUID1)
            .createDateTime(NOW1)
            .editDateTime(NOW1)
            .index(13)
            .type(ArticleFullDto.class.getSimpleName())
            .userLogin(userLoginDto)
            .build();
        ArticleFullDto newsEntryDto = ArticleFullDto.builder()
            .id(UUID1)
            .record(recordDto)
            .dateTime(NOW1)
            .title("titleTest1")
            .include("includeTest1")
            .summary("summaryTest1")
            .build();
        recordDto.setArticle(newsEntryDto);

        Assertions.assertNotNull(service);
        when(mockDao.save(any())).thenReturn(entity);
        when(mockRecordDao.save(any())).thenReturn(record);
        when(mockUserLoginDao.findById(userLoginDto.getId())).thenReturn(Optional.of(userLogin));
        when(mockUserLoginDao.findWhereLogin(userLoginDto.getLogin())).thenReturn(Optional.of(userLogin));
        service.create(newsEntryDto);
    }

    @Test
    @Disabled
    void readById(ArticleFullCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.findById(any())).thenReturn(Optional.of(entity));
        Assertions.assertEquals(dto, service.readById(entity.getId()));
    }

    @Test
    void readRange(ArticleFullCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.findById(any())).thenReturn(Optional.of(entity));
        List<ArticleFullDto> testList = service.readRange(0, Integer.MAX_VALUE);
        Assertions.assertTrue(testList.isEmpty());
    }

    @Test
    void update(ArticleFullCrudService service) {
        Assertions.assertNotNull(service);
        when(mockDao.save(any())).thenReturn(entity);
        when(mockDao.findById(any())).thenReturn(Optional.of(entity));
        when(mockUserLoginDao.findById(any())).thenReturn(Optional.of(userLogin));
        when(mockUserLoginDao.findWhereLogin(any())).thenReturn(Optional.of(userLogin));
        service.update(dto);
    }

    @Test
    void update2(ArticleFullCrudService service) {

        RecordFullDto recordDto = RecordFullDto.builder()
                .id(UUID1)
                .editDateTime(NOW1)
                .type(ArticleFullDto.class.getSimpleName())
                .userLogin(userLoginDto)
                .build();
        ArticleFullDto ArticleDto = ArticleFullDto.builder()
                .id(UUID1)
                .dateTime(NOW1)
                .title("titleTest1")
                .include("includeTest1")
                .summary("summaryTest1")
                .record(recordDto)
                .build();
        recordDto.setArticle(ArticleDto);

        Assertions.assertNotNull(service);
        when(mockDao.save(any())).thenReturn(entity);
        when(mockDao.findById(ArticleDto.getId())).thenReturn(Optional.of(entity));
        when(mockUserLoginDao.findById(any())).thenReturn(Optional.of(userLogin));
        when(mockUserLoginDao.findWhereLogin(any())).thenReturn(Optional.of(userLogin));
        service.update(ArticleDto);
    }

    @Test
    void delete(ArticleFullCrudService service) {
        Assertions.assertNotNull(service);
        service.delete(dto.getId());
    }
}
