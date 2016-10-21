package db;

import model.Group;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 * Created by Vlad on 10/21/2016.
 */
public class GroupDAO implements IDAO<Group> {

    private EntityManagerFactory factory;

    public GroupDAO(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public Group add(Group obj) {
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            manager.persist(obj);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            manager.close();
        }

        return obj;
    }

    @Override
    public boolean deactivate(Group obj) {
        return false;
    }

    @Override
    public boolean update(Group obj) {
        return false;
    }

    @Override
    public Group getById(int Id) {
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            Group group = manager.find(Group.class, Id);
            return group;
        } finally {
            manager.close();
        }
    }
}
