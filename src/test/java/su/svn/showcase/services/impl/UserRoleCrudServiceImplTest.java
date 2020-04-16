/*
 * This file was last modified at 2020.04.15 22:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleCrudServiceImplTest.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.jboss.weld.junit5.auto.AddPackages;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import su.svn.showcase.converters.RoleConverter;
import su.svn.showcase.converters.UserOnlyLoginConverter;
import su.svn.showcase.converters.UserRoleConverter;
import su.svn.showcase.converters.base.RoleBaseConverter;
import su.svn.showcase.converters.user.UserOnlyLoginBaseConverter;
import su.svn.showcase.converters.user.UserRolePartConverter;
import su.svn.showcase.dao.UserRoleDao;
import su.svn.showcase.dao.jpa.UserRoleDaoEjb;
import su.svn.showcase.dto.jdo.RoleJdo;
import su.svn.showcase.dto.jdo.UserLoginJdo;
import su.svn.showcase.dto.jdo.UserRoleJdo;
import su.svn.showcase.services.CrudService;
import su.svn.showcase.services.UserRoleCrudService;
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

import static su.svn.showcase.services.impl.support.EntityManagerFactoryProducer.configure;

@DisplayName("A UserRoleFullCrudServiceImplTest unit test cases")
@AddPackages(value = {UserRoleDao.class, CrudService.class})
@ExtendWith({JtaEnvironment.class, WeldJunit5Extension.class})
class UserRoleCrudServiceImplTest {

    static final Class<?> tClass = UserRoleCrudServiceImplTest.class;
    static final String resourceNamePrefix = "/META-INF/sql/" + tClass.getSimpleName();
    static final UUID UUID10 = UUID.fromString("00000000-0000-0000-0000-000000000010");

    static final UserRoleDao userRoleDaoEjb = new UserRoleDaoEjb();
    static final RoleConverter roleBaseConverter = new RoleBaseConverter();
    static final UserOnlyLoginConverter userLoginBaseConverter = new UserOnlyLoginBaseConverter();
    static final UserRoleConverter userRolePartConverter = new UserRolePartConverter();
    static final UserRoleCrudService userRoleCrudServiceImpl = new UserRoleCrudServiceImpl();

    private final Map<String, Object> ejbMap = new HashMap<String, Object>() {{
        put("UserRoleDaoEjb", userRoleDaoEjb);
        put("RoleBaseConverter", roleBaseConverter);
        put("UserOnlyLoginBaseConverter", userLoginBaseConverter);
        put("UserRolePartConverter", userRolePartConverter);
        put("UserRoleCrudService", userRoleCrudServiceImpl);
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
            UserRoleDaoEjb.class,
            UserRoleCrudServiceImpl.class,
            EntityManagerFactoryProducer.class,
            EntityManagerProducer.class)
            .activate(RequestScoped.class)
            .setEjbFactory(ejbFactory())
            .setPersistenceContextFactory(injectionPoint -> emf.createEntityManager())
            .setPersistenceUnitFactory(injectionPoint -> emf)
            .inject(userRoleDaoEjb)
            .inject(userRolePartConverter)
            .inject(userRoleCrudServiceImpl)
            .inject(this)
            .build();

    @Inject
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    @EJB(beanName = "UserRoleCrudService")
    UserRoleCrudService service;


    @BeforeEach
    void setUp() throws Exception {
        InputStream is = tClass.getResourceAsStream(resourceNamePrefix + "_setUp.sql");
        userTransaction.begin();
        InputStreamUtil.readAndExecuteLine(is, sql ->
                entityManager.createNativeQuery(sql).executeUpdate());
        userTransaction.commit();
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

    @DisplayName("Can inject entity manager and user transaction")
    @Test
    void canInject_entityManager() {
        Assertions.assertNotNull(entityManager);
        Assertions.assertNotNull(userTransaction);
    }

    @Disabled
    @Test
    void create() throws Exception {
        String roleName = "roleTest" + UUID1.toString().substring(0,24);
        RoleJdo role = RoleJdo.builder()
                .id(UUID1)
                .roleName(roleName)
                .build();
        UserLoginJdo userLogin = UserLoginJdo.builder()
                .id(UUID1)
                .dateTime(NOW1)
                .login("loginTest" + UUID1)
                .password("passwordTest" + UUID1)
                .build();
        UserRoleJdo userRole = UserRoleJdo.builder()
                .id(UUID1)
                .dateTime(NOW1)
                .role(role)
                .userLogin(userLogin)
                .roleName(roleName)
                .build();
        userTransaction.begin();
        service.create(userRole);
        userTransaction.rollback();
    }

    @Test
    void readById() throws Exception {
        userTransaction.begin();
        UserRoleJdo test = service.readById(UUID10);
        userTransaction.rollback();
        System.out.println("test = " + test);

    }

    @Test
    void readRange() throws Exception {
        userTransaction.begin();
        List<UserRoleJdo> test= service.readRange(0, Integer.MAX_VALUE);
        userTransaction.rollback();
        System.out.println("test = " + test);
    }

    @Disabled
    @Test
    void update() throws Exception {
        RoleJdo role = RoleJdo.builder()
                .id(UUID10)
                .roleName("testRole10")
                .build();
        UserLoginJdo userLogin = UserLoginJdo.builder()
                .id(UUID10)
                .dateTime(NOW1)
                .login("loginTest10")
                .password("passwordTest10")
                .build();
        UserRoleJdo newsEntryDto = UserRoleJdo.builder()
                .id(UUID10)
                .dateTime(NOW1)
                .role(role)
                .roleName("testRole10")
                .userLogin(userLogin)
                .build();
        userTransaction.begin();
        service.update(newsEntryDto);
        userTransaction.rollback();
    }

    @Test
    void delete(UserRoleCrudService service) {
    }
}