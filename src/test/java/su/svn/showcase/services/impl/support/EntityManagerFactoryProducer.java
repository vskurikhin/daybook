/*
 * This file was last modified at 2020.02.09 14:22 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * EntityManagerFactoryProducer.java
 * $Id$
 */

package su.svn.showcase.services.impl.support;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.cfg.Environment;

import static su.svn.shared.Constants.Db.PERSISTENCE_UNIT_NAME;

@ApplicationScoped
public class EntityManagerFactoryProducer {

    public static Map<String, Object> configure(BeanManager bm) {
        return new HashMap<String, Object>() {{
            put("javax.persistence.bean.manager", bm);
            put("javax.persistence.jdbc.driver", "org.h2.Driver");
            put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
            put("hibernate.show_sql", "true");
            put("hibernate.format_sql", "true");
            put("javax.persistence.jdbc.url",
                "jdbc:h2:mem:test:db;DB_CLOSE_ON_EXIT=FALSE;INIT=runscript from 'classpath:META-INF/sql/create.sql'");
            put(Environment.CONNECTION_PROVIDER, TransactionalConnectionProvider.class);
        }};
    }

    @Inject
    private BeanManager beanManager;

    @Produces
    @ApplicationScoped
    public EntityManagerFactory produceEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("PgPU", configure(beanManager));
    }

    public void close(@Disposes EntityManagerFactory entityManagerFactory) {
        entityManagerFactory.close();
    }
}
//EOF
