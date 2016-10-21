package db;

/**
 * Created by Vlad on 10/21/2016.
 */
public interface IDAO<T> {
    T add(T obj);
    boolean deactivate(T obj);
    boolean update(T obj);
    T getById(int Id);
}
