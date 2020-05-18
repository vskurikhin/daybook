/*
 * This file was last modified at 2020.05.18 21:31 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * OrderingQueryHibernateTest.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.jboss.weld.junit5.auto.AddPackages;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import su.svn.showcase.dao.jpa.RecordDaoEjb;
import su.svn.showcase.domain.Record;
import su.svn.showcase.services.impl.support.EntityManagerFactoryProducer;
import su.svn.showcase.services.impl.support.EntityManagerProducer;
import su.svn.showcase.services.impl.support.JtaEnvironment;
import su.svn.showcase.utils.OrderingQueryHibernate;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static su.svn.showcase.services.impl.support.EntityManagerFactoryProducer.configure;

@DisplayName("A OrderingQueryHibernate unit test cases")
@AddPackages(value = {OrderingQueryHibernateTest.class})
@ExtendWith({JtaEnvironment.class, WeldJunit5Extension.class})
class OrderingQueryHibernateTest {

    @Inject
    private BeanManager beanManager;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PgPU", configure(beanManager));

    private Map<String, Object> ejbMap = new HashMap<String, Object>();

    private Function<InjectionPoint, Object> ejbFactory() {
        return ip -> {
            String name = ip.getAnnotated().getAnnotation(EJB.class).beanName();
            System.err.println("beanName: " + name);
            return ejbMap.get(name);
        };
    }

    @WeldSetup
    private
    WeldInitiator weld = WeldInitiator.from(
            RecordDaoEjb.class,
            RecordCrudServiceImpl.class,
            EntityManagerFactoryProducer.class,
            EntityManagerProducer.class)
            .activate(RequestScoped.class)
            .setEjbFactory(ejbFactory())
            .setPersistenceContextFactory(injectionPoint -> emf.createEntityManager())
            .setPersistenceUnitFactory(injectionPoint -> emf)
            .inject(this)
            .build();

    @Inject
    private EntityManager entityManager;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getNamedQueryString() {
        String sql = OrderingQueryHibernate.getNamedQueryString(entityManager, Record.FIND_ALL_IDS);
        System.err.println("sql = " + sql);
    }

    @Test
    void getNamedQueryOrderedBy() {
        String sql = OrderingQueryHibernate
                .getNamedQueryIdsOrderedBy(entityManager, Record.FIND_ALL_IDS, Record.getDefaultOrderMap());
        System.err.println("sql = " + sql);
    }

    @Test
    void testGetNamedQueryString() {
        String sql = OrderingQueryHibernate
                .getNamedQueryIdInOrderedBy(entityManager, Record.FIND_ALL_WHERE_ID_IN, Record.getDefaultOrderMap());
        System.err.println("sql = " + sql);
    }
}