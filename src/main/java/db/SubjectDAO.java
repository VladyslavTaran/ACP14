package db;

import model.Subject;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Vlad on 22.10.2016.
 */
public class SubjectDAO implements IDAO<Subject> {
    private EntityManager manager;

    public SubjectDAO(EntityManagerFactory factory) {
        this.manager = factory.createEntityManager();
    }

    @Override
    public Subject add(Subject obj) {
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
        Subject subject = getById(Id);
        if (subject != null) {
            try {
                transaction.begin();
                subject.setActive(false);
                transaction.commit();
                return true;
            } catch (Exception e) {
                transaction.rollback();
            }
        }
        return false;
    }

    @Override
    public boolean update(Subject obj) {
        EntityTransaction transaction = manager.getTransaction();

        Subject subject = getById(obj.getId());

        if (subject != null) {
            try {
                transaction.begin();
                subject.setActive(obj.isActive());
                subject.setName(obj.getName());
                subject.setCourses(obj.getCourses());
                subject.setDescription(obj.getDescription());
                subject.setProfessor(obj.getProfessor());
                transaction.commit();
                return true;
            } catch (Exception e) {
                transaction.rollback();
            }
        }
        return false;
    }

    @Override
    public Subject getById(int Id) {
        return manager.find(Subject.class, Id);
    }

    @Override
    public List<Subject> getAll() {
        TypedQuery<Subject> query = manager.createQuery(Constants.GET_ALL_SUBJECTS, Subject.class);
        return query.getResultList();
    }

    @Override
    public void clearTable() {
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        Query query = manager.createQuery("DELETE FROM Subject");
        query.executeUpdate();
        transaction.commit();
    }
}
