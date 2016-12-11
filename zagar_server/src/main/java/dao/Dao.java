package dao;

import java.util.List;
import java.util.Optional;

/**
 * Created by Artem on 12/11/16.
 */
public interface Dao<T> {

    List<T> getAll();

    List<T> getAllWhere(String... hqlConditions);

    void insert(T t);

    default Optional<T> findById(int id) {
        return Optional.ofNullable(
                getAllWhere("id=" + id).get(0)
        );
    }

}
