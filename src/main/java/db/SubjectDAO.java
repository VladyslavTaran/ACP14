package db;

import db.interfaces.IDAO;
import model.Subject;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

public class SubjectDAO implements IDAO<Subject> {
    @PersistenceContext
    private EntityManager manager;

    public SubjectDAO() {
    }

    @Override
    @Transactional
    public Subject add(Subject obj) {
        manager.persist(obj);
        return obj;
    }

    @Override
    @Transactional
    public boolean deactivate(int Id) {
        Subject subject = getById(Id);
        if (subject != null) {
            subject.setActive(false);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean update(Subject obj) {
        Subject subject = getById(obj.getId());
        if (subject != null) {
            subject.setActive(obj.isActive());
            subject.setName(obj.getName());
            subject.setCourses(obj.getCourses());
            subject.setDescription(obj.getDescription());
            subject.setProfessor(obj.getProfessor());
            return true;
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
    @Transactional
    public void clearTable() {
        Query query = manager.createQuery("DELETE FROM Subject");
        query.executeUpdate();
    }
}
