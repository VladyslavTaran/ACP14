package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Vlad on 04.10.2016.
 */

@Entity
@Table(name = "students")
public class Student extends Inherit {
    private int groupId;

    public Student(int id, String name, int groupId, boolean active) {
        setName(name);
        setId(id);
        setActive(active);
        this.groupId = groupId;
    }

    public Student() {
    }

    @Column
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public int hashCode() {
        return getId()*
                getName().hashCode() +
                (isActive() ? 1 : 0) +
                getGroupId();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof String ))return false;
        Student other = (Student) obj;
        boolean res = true;
        if (getId() != other.getId()) res &= false;
        if (getName() != null && other.getName() != null){
            if (!getName().equals(other.getName())) res &= false;
        } else {
            res &= false;
        }
        if (isActive() != other.isActive()) res &= false;
        if (groupId != other.getGroupId()) res &= false;
        return res;
    }

    @Override
    public String toString() {
        return getId() + " " +
                getName() + "\t" +
                groupId + " " +
                "active:" + isActive();
    }
}
