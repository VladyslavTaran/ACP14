package db;

import model.Professor;
import model.Subject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Vlad on 22.10.2016.
 */
public class ProfessorDAO implements IDAO<Professor> {
    private EntityManager manager;

    public ProfessorDAO(EntityManagerFactory factory) {
        manager = factory.createEntityManager();
    }

    @Override
    public Professor add(Professor obj) {
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            Subject subject = manager.find(Subject.class, obj.getSubject().getId());
            obj.setSubject(subject);
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
        Professor professor = getById(Id);
        if (professor != null) {
            try {
                transaction.begin();
                professor.setActive(false);
                transaction.commit();
                return true;
            } catch (Exception e) {
                transaction.rollback();
            }
        }
        return false;
    }

    @Override
    public boolean update(Professor obj) {
        EntityTransaction transaction = manager.getTransaction();

        Professor professor = getById(obj.getId());

        if (professor != null) {
            try {
                transaction.begin();
                professor.setActive(obj.isActive());
                professor.setName(obj.getName());
                professor.setExperience(obj.getExperience());
                professor.setSubject(obj.getSubject());
                transaction.commit();
                return true;
            } catch (Exception e) {
                transaction.rollback();
            }
        }
        return false;
    }

    @Override
    public Professor getById(int Id) {
        return manager.find(Professor.class, Id);
    }

    @Override
    public List<Professor> getAll() {
        TypedQuery<Professor> query = manager.createQuery(Constants.GET_ALL_PROFESSORS, Professor.class);
        return query.getResultList();
    }
}
