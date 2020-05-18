/*
 * This file was last modified at 2020.05.18 21:27 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * OrderingQueryHibernate.java
 * $Id$
 */

package su.svn.showcase.utils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderingQueryHibernate {

    private static String ORDER_BY_CLAUSE_START = " ORDER BY ";

    private static final Pattern badQueryPattern = Pattern.compile("[^\\p{ASCII}]*");

    private static final Pattern QUERY_GET_IDS
            = Pattern.compile("^(select\\s+(distinct\\s+)?e.id)(\\s+)(.+)$", Pattern.CASE_INSENSITIVE);

    public static String getNamedQueryString(EntityManager em, String queryName) {

        Query tmpQuery = em.createNamedQuery(queryName);
        @SuppressWarnings("rawtypes")
        org.hibernate.Query sqlQuery = tmpQuery.unwrap(org.hibernate.Query.class);

        String queryString = sqlQuery.getQueryString();
        if (badQueryPattern.matcher(queryString).matches()) {
            throw new RuntimeException("Bad query string."); // TODO custom Exception
        }

        return queryString;
    }

    public static String getNamedQueryIdsOrderedBy(EntityManager em, String queryName, Map<String, Boolean> columnNames) {

        boolean notFirst = false;
        StringBuilder fields = new StringBuilder();
        StringBuilder orderBy = new StringBuilder();
        orderBy.append(ORDER_BY_CLAUSE_START);

        for (String columnName: columnNames.keySet()) {
            fields.append(", ");
            if (notFirst) {
                orderBy.append(", ");
            }
            notFirst = true;
            orderBy.append("e.").append(columnName);
            fields.append("e.").append(columnName);

            if (columnNames.get(columnName))
                orderBy.append(" ASC");
            else
                orderBy.append(" DESC");
        }
        Matcher matcher = QUERY_GET_IDS.matcher(getNamedQueryString(em, queryName));
        if ( ! matcher.find()) {
            throw new RuntimeException("Bad query string."); // TODO custom Exception
        }

        return matcher.group(1) + fields.toString() + matcher.group(4) + orderBy.toString();
    }

    public static String getNamedQueryIdInOrderedBy(EntityManager em, String queryName, Map<String, Boolean> columnNames) {

        boolean notFirst = false;
        StringBuilder orderBy = new StringBuilder();
        orderBy.append(ORDER_BY_CLAUSE_START);

        for (String columnName: columnNames.keySet()) {
            if (notFirst) {
                orderBy.append(", ");
            }
            notFirst = true;
            orderBy.append("e.").append(columnName);

            if (columnNames.get(columnName))
                orderBy.append(" ASC");
            else
                orderBy.append(" DESC");
        }

        return getNamedQueryString(em, queryName) + orderBy.toString();
    }
}
