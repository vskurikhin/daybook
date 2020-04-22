/*
 * This file was last modified at 2020.04.20 22:41 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NamedQueryUtil.java
 * $Id$
 */

package su.svn.showcase.utils;

import org.hibernate.Session;
import org.hibernate.hql.internal.ast.ASTQueryTranslatorFactory;
import org.hibernate.hql.internal.ast.QueryTranslatorImpl;
import org.hibernate.hql.spi.QueryTranslatorFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public class OrderingQueryHibernateUtil {

    public static String ORDER_BY_CLAUSE_START = " ORDER BY ";

    private static final Pattern badQueryPattern = Pattern.compile("[^\\p{ASCII}]*");

    private static org.hibernate.query.Query<UUID> queryUUID;

    public static String getNamedQueryString(EntityManager em, String queryName) {

        Query tmpQuery = em.createNamedQuery(queryName);
        @SuppressWarnings("rawtypes")
        org.hibernate.Query sqlQuery = tmpQuery.unwrap(org.hibernate.Query.class);

        String queryString = sqlQuery.getQueryString();
        if (badQueryPattern.matcher(queryString).matches()) {
            throw new RuntimeException("Bad query string."); // TODO custom Exception
        }

        return queryString;

//        Session session = em.unwrap(JpaEntityManager.class).getActiveSession();
//        DatabaseQuery databaseQuery = ((EJBQueryImpl)query).getDatabaseQuery();
//        databaseQuery.prepareCall(session, new DatabaseRecord());
//        String sqlString = databaseQuery.getSQLString();
    }

    public static Query getNamedQueryOrderedBy(EntityManager em, String queryName, Map<String, Boolean> columnNames) {

        StringBuilder sb = new StringBuilder();
        sb.append(getNamedQueryString(em, queryName));
        sb.append(ORDER_BY_CLAUSE_START);

        int limit = columnNames.size();
        int i = 0;
        for (String columnName: columnNames.keySet()) {
            sb.append(columnName);

            if (columnNames.get(columnName))
                sb.append(" ASC");
            else
                sb.append(" DESC");

            if (i++ != (limit - 1)) {
                sb.append(", \n");
            }
        }

        return em.createQuery(sb.toString());
    }
}
