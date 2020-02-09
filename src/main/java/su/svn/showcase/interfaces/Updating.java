package su.svn.showcase.interfaces;

import su.svn.showcase.domain.DBEntity;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

public interface Updating<E extends DBEntity> {
    E update(@NotNull E entity);

    E update(@NotNull E entity, Map<String, Object> values);

    default <T> Optional<T> convertIfContainsKey(Class<T> c, Map<String, Object> map, String key) {

        if (map.containsKey(key)) {
            Object o = map.get(key);
            if (c.isInstance(o)) {
                return Optional.of(c.cast(o));
            }
        }
        return Optional.empty();
    }

    default <T> Optional<List<T>> convertListIfContainsKey(final Class<T> tClass, Map<String, Object> map, String key) {

        if (map.containsKey(key)) {
            Object l = map.get(key);
            if (l instanceof List<?>) {
                List<?> list = (List<?>) l;
                List<T> result = list.stream()
                        .map(o -> tClass.isInstance(o) ? tClass.cast(o) : null)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                return Optional.of(result);
            }
        }
        return Optional.empty();
    }

    default <T> Optional<Set<T>> convertSetIfContainsKey(final Class<T> tClass, Map<String, Object> map, String key) {

        if (map.containsKey(key)) {
            Object s = map.get(key);
            if (s instanceof Set<?>) {
                Set<?> set = (Set<?>) s;
                Set<T> result = set.stream()
                        .map(o -> tClass.isInstance(o) ? tClass.cast(o) : null)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());
                return Optional.of(result);
            }
        }
        return Optional.empty();
    }

    default <K, T extends DBEntity<K>> Map<K, T> toEntitiesMap(Collection<T> entities) {
        return entities.stream().filter(Objects::nonNull).collect(Collectors.toMap(DBEntity::getId, e -> e));
    }

    default <K, D extends DBEntity<K>> Map<K, D> toDataTransferObjectsMap(Collection<D> entities) {
        return entities.stream().filter(Objects::nonNull).collect(Collectors.toMap(DBEntity::getId, e -> e));
    }
}
