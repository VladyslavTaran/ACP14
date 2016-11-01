package db;

import db.interfaces.IDAO;
import model.Professor;
import model.Subject;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

public class ProfessorDAO implements IDAO<Professor> {
    @PersistenceContext
    private EntityManager manager;

    public ProfessorDAO() {
    }

    @Override
    @Transactional
    public Professor add(Professor obj) {
        Subject subject = manager.find(Subject.class, obj.getSubject().getId());
        obj.setSubject(subject);
        manager.persist(obj);
        return obj;
    }

    @Override
    @Transactional
    public boolean deactivate(int Id) {
        Professor professor = getById(Id);
        if (professor != null) {
            professor.setActive(false);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean update(Professor obj) {
        Professor professor = getById(obj.getId());
        if (professor != null) {
            professor.setActive(obj.isActive());
            professor.setName(obj.getName());
            professor.setExperience(obj.getExperience());
            Subject subject = manager.find(Subject.class, obj.getSubject().getId());
            professor.setSubject(subject);
            return true;
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

    @Override
    @Transactional
    public void clearTable() {
        Query query = manager.createQuery("DELETE FROM Professor");
        query.executeUpdate();
    }
}