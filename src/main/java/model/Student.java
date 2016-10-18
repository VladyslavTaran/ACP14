package model;

import javax.persistence.*;

/**
 * Created by Vlad on 04.10.2016.
 */

@Entity
@Table(name = "students")
public class Student extends Inherit {
    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group group;

    public Student(int id, String name, Group group, boolean active) {
        setName(name);
        setId(id);
        setActive(active);
        this.group = group;
    }

    public Student() {
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public int hashCode() {
        return getId()*
                getName().hashCode() +
                (isActive() ? 1 : 0) +
                getGroup().hashCode();
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
        if (!getGroup().equals(other.getGroup())) res &= false;
        return res;
    }

    @Override
    public String toString() {
        return getId() + " " +
                getName() + "\t" +
                group.getName() + " " +
                "active:" + isActive();
    }
}
