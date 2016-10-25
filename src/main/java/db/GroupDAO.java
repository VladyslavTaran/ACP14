package db;

import model.Group;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Vlad on 10/21/2016.
 */
public class GroupDAO implements IDAO<Group> {
    private EntityManager manager;

    public GroupDAO(EntityManagerFactory factory) {
        manager = factory.createEntityManager();
    }

    @Override
    public Group add(Group obj) {
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            manager.persist(obj);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        return obj;
    }

    @Override
    public boolean deactivate(int Id) {
        EntityTransaction transaction = manager.getTransaction();
        Group group = getById(Id);
        if (group != null) {
            try {
                transaction.begin();
                group.setActive(false);
                transaction.commit();
                return true;
            } catch (Exception e) {
                transaction.rollback();
            }
        }
        return false;
    }

    @Override
    public boolean update(Group obj) {
        EntityTransaction transaction = manager.getTransaction();

        Group group = getById(obj.getId());

        if (group != null) {
            try {
                transaction.begin();
                group.setStudents(obj.getStudents());
                group.setActive(obj.isActive());
                group.setName(obj.getName());
                group.setCourses(obj.getCourses());
                transaction.commit();
                return true;
            } catch (Exception e) {
                transaction.rollback();
            }
        }
        return false;
    }

    @Override
    public Group getById(int Id) {
        return manager.find(Group.class, Id);
    }

    @Override
    public List<Group> getAll() {
        TypedQuery<Group> query = manager.createQuery(Constants.GET_ALL_GROUPS,Group.class);
        return query.getResultList();
    }

    @Override
    public void clearTable() {
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        Query query = manager.createQuery("DELETE FROM Group");
        query.executeUpdate();
        transaction.commit();
    }
}
