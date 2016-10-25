package db;

import model.Group;
import model.Student;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Vlad on 10/21/2016.
 */
public class StudentDAO implements IStudentDAO<Student> {
    private EntityManager manager;

    public StudentDAO(EntityManagerFactory factory) {
        this.manager = factory.createEntityManager();
    }

    @Override
    public Student add(Student obj) {
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            Group group = manager.find(Group.class, obj.getGroup().getId());
            obj.setGroup(group);
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
        Student student = getById(Id);
        if (student != null) {
            try {
                transaction.begin();
                student.setActive(false);
                transaction.commit();
                return true;
            } catch (Exception e) {
                transaction.rollback();
            }
        }
        return false;
    }

    @Override
    public boolean update(Student obj) {
        EntityTransaction transaction = manager.getTransaction();

        Student student = getById(obj.getId());

        if (student != null) {
            try {
                transaction.begin();
                student.setActive(obj.isActive());
                student.setName(obj.getName());
                Group group = manager.find(Group.class, obj.getGroup().getId());
                student.setGroup(group);
                transaction.commit();
                return true;
            } catch (Exception e) {
                transaction.rollback();
            }
        }
        return false;
    }

    @Override
    public Student getById(int Id) {
        return manager.find(Student.class, Id);
    }

    @Override
    public List<Student> getAll() {
        TypedQuery<Student> query = manager.createQuery(Constants.GET_ALL_STUDENTS, Student.class);
        return query.getResultList();
    }

    @Override
    public void clearTable() {
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        Query query = manager.createQuery("DELETE FROM Student");
        query.executeUpdate();
        transaction.commit();
    }

    @Override
    public List<Student> getAllByGroupId(int groupId){
        TypedQuery<Student> query = manager.createQuery("SELECT s FROM Student s WHERE s.group_id =:groupId", Student.class);
        query.setParameter("groupId", groupId);
        return query.getResultList();
    }
}
