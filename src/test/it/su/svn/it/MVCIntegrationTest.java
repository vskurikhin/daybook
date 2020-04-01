/*
 * This file was last modified at 2020.03.31 20:21 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * MVCIntegrationTest.java
 * $Id$
 */

package su.svn.it;

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

import javax.inject.Inject;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RunWith(Arquillian.class)
public class MVCIntegrationTest extends BaseIntegrationTest {

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
    public void test_() throws Exception {
        System.out.println("MVCIntegrationTest true = " + true);
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
}
//EOF
