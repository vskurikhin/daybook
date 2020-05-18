/*
 * This file was last modified at 2020.04.24 21:39 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * SortFields.java
 * $Id$
 */

package su.svn.showcase.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.domain.Sort;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class SortFields {
    private static final Logger LOGGER = LoggerFactory.getLogger(SortFields.class);

    @Data
    @ToString
    @EqualsAndHashCode
    public static class Cluster {
        Map<String, Boolean> cluster = new LinkedHashMap<>();
    }

    private final String entityName;
    private final Set<Cluster> clusters;

    public SortFields(Class<?> aClass) {
        this.entityName = aClass.getSimpleName();
        this.clusters = Collections.unmodifiableSet(sortFields(aClass));
    }

    public boolean contains(Map<String, Boolean> sorting) {
        Cluster cluster = new Cluster();
        cluster.setCluster(sorting);

        return clusters.contains(cluster);
    }

    @Override
    public String toString() {
        return "SortFields{" +
                "entityName='" + entityName + '\'' +
                ", clusters=" + clusters +
                '}';
    }

    private Set<Cluster> sortFields(Class<?> aClass) {
        Set<Cluster> clusters = new LinkedHashSet<>();
        Map<String, Field> mapFields = Arrays.stream(aClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Sort.class))
                .collect(Collectors.toMap(Field::getName, f -> f));
        for (String fieldName : mapFields.keySet()) {
            Cluster cluster = new Cluster();
            Field field = mapFields.get(fieldName);
            cluster.getCluster().put(fieldName, ! getAnnotationSort(field).decrease());
            for (String other : getAnnotationSort(field).cluster()) {
                Field f = mapFields.get(other);
                boolean ascending = f == null || ! getAnnotationSort(f).decrease();
                cluster.getCluster().put(other, ascending);
            }
            clusters.add(cluster);
        }
        return clusters;
    }

    private Sort getAnnotationSort(Field field) {
        return field.getAnnotation(Sort.class);
    }
}
