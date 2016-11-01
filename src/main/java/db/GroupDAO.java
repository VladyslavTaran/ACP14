package db;

import db.interfaces.IDAO;
import model.Group;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

public class GroupDAO implements IDAO<Group> {
    @PersistenceContext
    private EntityManager manager;

    public GroupDAO() {
    }

    @Override
    @Transactional
    public Group add(Group obj) {
        manager.persist(obj);
        return obj;
    }

    @Override
    @Transactional
    public boolean deactivate(int Id) {
        Group group = getById(Id);
        if (group != null) {
            group.setActive(false);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean update(Group obj) {
        Group group = getById(obj.getId());
        if (group != null) {
            group.setStudents(obj.getStudents());
            group.setActive(obj.isActive());
            group.setName(obj.getName());
            group.setCourses(obj.getCourses());
            return true;
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
    @Transactional
    public void clearTable() {
        Query query = manager.createQuery("DELETE FROM Group");
        query.executeUpdate();
    }
}
