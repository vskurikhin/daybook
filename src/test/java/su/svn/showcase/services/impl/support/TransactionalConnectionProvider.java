/*
 * This file was last modified at 2020.02.09 14:22 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TransactionalConnectionProvider.java
 * $Id$
 */

package su.svn.showcase.services.impl.support;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.h2.jdbcx.JdbcDataSource;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;

import com.arjuna.ats.jdbc.TransactionalDriver;

/**
 * @author <a href="mailto:gytis@redhat.com">Gytis Trikleris</a>
 */
public class TransactionalConnectionProvider implements ConnectionProvider {

    public static final String DATASOURCE_JNDI = "java:testDS";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";
    public static final String URL = "jdbc:h2:mem:test:db;DB_CLOSE_ON_EXIT=FALSE" +
            ";INIT=runscript from 'classpath:META-INF/sql/create.sql'";

    private final TransactionalDriver transactionalDriver;

    public TransactionalConnectionProvider() {
        transactionalDriver = new TransactionalDriver();
    }

    public static void bindDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(URL);
        dataSource.setUser(USERNAME);
        dataSource.setPassword(PASSWORD);

        try {
            InitialContext initialContext = new InitialContext();
            initialContext.bind(DATASOURCE_JNDI, dataSource);
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty(TransactionalDriver.userName, USERNAME);
        properties.setProperty(TransactionalDriver.password, PASSWORD);
        properties.setProperty(TransactionalDriver.createDb, "true");
        return transactionalDriver.connect("jdbc:arjuna:" + DATASOURCE_JNDI, properties);
    }

    @Override
    public void closeConnection(Connection connection) throws SQLException {
        if (!connection.isClosed()) {
            connection.close();
        }
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class aClass) {
        return getClass().isAssignableFrom(aClass);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T unwrap(Class<T> aClass) {
        if (isUnwrappableAs(aClass)) {
            return (T) this;
        }

        throw new UnknownUnwrapTypeException(aClass);
    }
}
//EOF
