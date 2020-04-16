/*
 * This file was last modified at 2020.04.15 22:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserOnlyLoginRoServiceImplTest.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.jboss.weld.junit5.auto.AddPackages;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import su.svn.showcase.converters.UserOnlyLoginConverter;
import su.svn.showcase.converters.user.UserOnlyLoginBaseConverter;
import su.svn.showcase.dao.UserLoginDao;
import su.svn.showcase.dao.jpa.UserLoginDaoEjb;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.UserOnlyLoginDto;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.services.CrudService;
import su.svn.showcase.services.UserOnlyLoginRoService;
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
import javax.transaction.UserTransaction;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static su.svn.showcase.domain.TestData.*;
import static su.svn.showcase.dto.TestData.cloneUserOnlyLoginBaseDto1;
import static su.svn.showcase.services.impl.support.EntityManagerFactoryProducer.configure;

@DisplayName("A UserOnlyLoginRoServiceImplTest unit test cases")
@AddPackages(value = {UserLoginDao.class, CrudService.class})
@ExtendWith({JtaEnvironment.class, WeldJunit5Extension.class})
class UserOnlyLoginRoServiceImplTest {

    static final Class<?> tClass = UserOnlyLoginRoServiceImplTest.class;
    static final String resourceNamePrefix = "/META-INF/sql/" + tClass.getSimpleName();
    static final UUID UUID10 = UUID.fromString("00000000-0000-0000-0000-000000000010");

    static final UserLoginDao userLoginDaoEjb = new UserLoginDaoEjb();
    static final UserOnlyLoginConverter userOnlyLoginConverter = new UserOnlyLoginBaseConverter();
    static final UserOnlyLoginRoService userOnlyLoginRoService = new UserOnlyLoginRoServiceImpl();

    private final Map<String, Object> ejbMap = new HashMap<String, Object>() {{
        put("UserLoginDaoEjb", userLoginDaoEjb);
        put("UserOnlyLoginBaseConverter", userOnlyLoginConverter);
        put("UserOnlyLoginRoService", userOnlyLoginRoService);
    }};

    private Function<InjectionPoint, Object> ejbFactory() {
        return ip -> {
            String name = ip.getAnnotated().getAnnotation(EJB.class).beanName();
            System.err.println("beanName: " + name);
            return ejbMap.get(name);
        };
    }

    @Inject
    private BeanManager beanManager;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PgPU", configure(beanManager));

    @WeldSetup
    private
    WeldInitiator weld = WeldInitiator.from(
            UserLoginDaoEjb.class,
            UserOnlyLoginRoServiceImpl.class,
            EntityManagerFactoryProducer.class,
            EntityManagerProducer.class)
            .activate(RequestScoped.class)
            .setEjbFactory(ejbFactory())
            .setPersistenceContextFactory(injectionPoint -> emf.createEntityManager())
            .setPersistenceUnitFactory(injectionPoint -> emf)
            .inject(userLoginDaoEjb)
            .inject(userOnlyLoginRoService)
            .inject(this)
            .build();

    @Inject
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    @EJB(beanName = "UserOnlyLoginRoService")
    UserOnlyLoginRoService service;


    private UserLogin entity;
    private UserOnlyLoginDto dto;


    @BeforeEach
    void setUp() throws Exception {
        InputStream is = tClass.getResourceAsStream(resourceNamePrefix + "_setUp.sql");
        userTransaction.begin();
        InputStreamUtil.readAndExecuteLine(is, sql ->
                entityManager.createNativeQuery(sql).executeUpdate());
        userTransaction.commit();

        entity = cloneUserLogin1();
        dto = cloneUserOnlyLoginBaseDto1();
    }

    @AfterEach
    void tearDown() throws Exception {
        try {
            userTransaction.rollback();
        } catch (Exception ignored) {}

        InputStream is = tClass.getResourceAsStream(resourceNamePrefix + "_tearDown.sql");
        userTransaction.begin();
        InputStreamUtil.readAndExecuteLine(is, sql ->
                entityManager.createNativeQuery(sql).executeUpdate());
        userTransaction.commit();
    }

    static UUID UUID1 = UUID.randomUUID();
    static LocalDateTime NOW1 = LocalDateTime.now();

    @Test
    void create() throws Exception {
        UserOnlyLoginDto loginDto = UserOnlyLoginDto.builder()
                .id(UUID1)
                .login("loginTest" + UUID1)
                .build();
        userTransaction.begin();
        Assertions.assertThrows(ErrorCase.class, () -> service.create(loginDto));
        userTransaction.rollback();
    }

    @Test
    void readById() throws Exception {
        userTransaction.begin();
        UserOnlyLoginDto test = service.readById(UUID10);
        userTransaction.rollback();
    }

    @Test
    void readByLogin() throws Exception {
        userTransaction.begin();
        UserOnlyLoginDto test = service.readByLogin("loginTest10");
        userTransaction.rollback();
        System.out.println("test = " + test);
    }

    @Test
    void readRange() throws Exception {
        userTransaction.begin();
        List<UserOnlyLoginDto> test = service.readRange(0, Integer.MAX_VALUE);
        userTransaction.rollback();

    }

    @Test
    void update() throws Exception {
        UserOnlyLoginDto loginDto = UserOnlyLoginDto.builder()
                .id(UUID10)
                .login("loginTest" + UUID10)
                .build();
        userTransaction.begin();
        Assertions.assertThrows(ErrorCase.class, () -> service.update(loginDto));
        userTransaction.rollback();
    }

    @Test
    void delete() throws Exception {
        userTransaction.begin();
        Assertions.assertThrows(ErrorCase.class, () -> service.delete(UUID10));
        userTransaction.rollback();
    }

    @Test
    void count() throws Exception {
        userTransaction.begin();
        Assertions.assertEquals(1, service.count());
        userTransaction.rollback();
    }
}