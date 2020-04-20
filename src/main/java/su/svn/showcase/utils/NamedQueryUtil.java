/*
 * This file was last modified at 2020.04.18 11:33 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NamedQueryUtil.java
 * $Id$
 */

package su.svn.showcase.utils;

import org.hibernate.SQLQuery;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.Map;
import java.util.regex.Pattern;

public class NamedQueryUtil {

    public static String ORDER_BY_CLAUSE_START = " ORDER BY";

    private static final Pattern badQueryPattern = Pattern.compile("[^\\p{ASCII}]*");

    public static String getNamedQueryString(EntityManager em, String queryName) throws SQLException {
        Query tmpQuery = em.createNamedQuery(queryName);
        SQLQuery sqlQuery = tmpQuery.unwrap(SQLQuery.class);
        String queryString = sqlQuery.getQueryString();
        if (badQueryPattern.matcher(queryString).matches()) {
            throw new SQLException("Bad query string.");
        }

        return queryString;
    }

    public static Query getNamedQueryOrderedBy(EntityManager em, String queryName, Map<String, Boolean> columnNames)
           throws SQLException {

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

            if (i != (limit - 1)) {
                sb.append(", \n");
            }
        }

        return em.createNativeQuery(sb.toString());
    }
}
