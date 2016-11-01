package db;

import db.interfaces.IStudentDAO;
import model.Group;
import model.Student;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

public class StudentDAO implements IStudentDAO<Student> {
    @PersistenceContext
    private EntityManager manager;

    public StudentDAO() {
    }

    @Override
    @Transactional
    public Student add(Student obj) {
        Group group = manager.find(Group.class, obj.getGroup().getId());
        obj.setGroup(group);
        manager.persist(obj);
        return obj;
    }

    @Override
    @Transactional
    public boolean deactivate(int Id) {
        Student student = getById(Id);
        if (student != null) {
                student.setActive(false);
        }
        return false;
    }

    @Override
    @Transactional
    public boolean update(Student obj) {
        Student student = getById(obj.getId());

        if (student != null) {
            student.setActive(obj.isActive());
            student.setName(obj.getName());
            Group group = manager.find(Group.class, obj.getGroup().getId());
            student.setGroup(group);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Student getById(int Id) {
        return manager.find(Student.class, Id);
    }

    @Override
    @Transactional
    public List<Student> getAll() {
        TypedQuery<Student> query = manager.createQuery(Constants.GET_ALL_STUDENTS, Student.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public List<Student> getAllByGroupId(int groupId){
        TypedQuery<Student> query = manager.createQuery("SELECT s FROM Student s WHERE s.group.id =:groupId", Student.class);
        query.setParameter("groupId", groupId);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void clearTable() {
        Query query = manager.createQuery("DELETE FROM Student");
        query.executeUpdate();
    }
}
