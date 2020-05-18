package su.svn.showcase.utils;

import org.hibernate.transform.BasicTransformerAdapter;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class DistinctPostResultTransformer extends BasicTransformerAdapter {
    private final EntityManager entityManager;

    public DistinctPostResultTransformer(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List transformList(List list) {

        /* Map<Serializable, Identifiable> identifiableMap = new LinkedHashMap<>(list.size());

        for (Object entityArray : list) {
            if (Object[].class.isAssignableFrom(entityArray.getClass())) {
                Post post = null;
                PostComment comment = null;

                Object[] tuples = (Object[]) entityArray;

                for (Object tuple : tuples) {
                    if(tuple instanceof Identifiable) {
                        entityManager.detach(tuple);

                        if (tuple instanceof Post) {
                            post = (Post) tuple;
                        }
                        else if (tuple instanceof PostComment) {
                            comment = (PostComment) tuple;
                        }
                        else {
                            throw new UnsupportedOperationException(
                                    "Tuple " + tuple.getClass() + " is not supported!"
                            );
                        }
                    }
                }

                if (post != null) {
                    if (!identifiableMap.containsKey(post.getId())) {
                        identifiableMap.put(post.getId(), post);
                        post.setComments(new ArrayList<>());
                    }
                    if (comment != null) {
                        post.addComment(comment);
                    }
                }
            }
        }
        return new ArrayList<>(identifiableMap.values()); */
        return null;
    }
}
