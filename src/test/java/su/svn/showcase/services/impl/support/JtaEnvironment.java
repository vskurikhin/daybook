/*
 * This file was last modified at 2020.02.09 14:22 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * JtaEnvironment.java
 * $Id$
 */

package su.svn.showcase.services.impl.support;

import org.jnp.server.NamingBeanImpl;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.arjuna.ats.jta.utils.JNDIManager;

public class JtaEnvironment implements AfterEachCallback, BeforeEachCallback {

    private NamingBeanImpl NAMING_BEAN;

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        NAMING_BEAN.stop();
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        NAMING_BEAN = new NamingBeanImpl();
        NAMING_BEAN.start();

        JNDIManager.bindJTAImplementation();
        TransactionalConnectionProvider.bindDataSource();
    }
}
//EOF
