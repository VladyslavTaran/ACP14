package db.interfaces;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Vlad on 10/21/2016.
 */
public interface IDAO<T> {
    T add(T obj);
    boolean deactivate(int Id);
    boolean update(T obj);
    T getById(int Id);
    List<T> getAll();
    void clearTable();
}
