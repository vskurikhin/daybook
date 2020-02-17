/*
 * This file was last modified at 2020.02.16 11:20 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * BaseIntegrationTest.java
 * $Id$
 */

package su.svn.it;

import su.svn.showcase.domain.DBEntity;
import su.svn.showcase.domain.Tag;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

public class BaseIntegrationTest extends IntegrationTestData {
    @PersistenceContext(unitName = "PgPU")
    public EntityManager em;

    @Inject
    private UserTransaction utx;

    @SafeVarargs
    private final void deleteEntity(Class<? extends DBEntity>... entityClasses) {
        try {

            utx.begin();
            for (Class<? extends DBEntity> entityClass : entityClasses) {
                Query query = em.createQuery(String.format("DELETE FROM %s", entityClass.getSimpleName()));
                query.executeUpdate();
            }
            utx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearDB() {
        deleteEntity(Tag.class);
    }
}
//EOF
