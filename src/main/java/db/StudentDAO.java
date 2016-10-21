package db;

import model.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Created by Vlad on 10/21/2016.
 */
public class StudentDAO implements IDAO<Student> {

    private EntityManagerFactory studentFacory;

    public StudentDAO(EntityManagerFactory entityManagetFactory) {
        this.studentFacory = entityManagetFactory;
    }

    @Override
    public Student add(Student obj) {
        return null;
    }

    @Override
    public boolean deactivate(Student obj) {
        return false;
    }

    @Override
    public boolean update(Student obj) {
        return false;
    }

    @Override
    public Student getById(int Id) {
        return null;
    }
}
