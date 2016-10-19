package model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by Vlad on 04.10.2016.
 */

@Entity
@Table(name = "groups")
public class Group extends Inherit {
    @OneToMany(mappedBy = "group")
    private List<Student> students;

    @OneToMany(mappedBy = "group")
    private List<Course> courses;

    public Group(String name, boolean active) {
        setName(name);
        setActive(active);
    }

    public Group() {
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public int hashCode() {
        return getId()*getName().hashCode() + (isActive() ? 1 : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Group))return false;
        Group other = (Group) obj;
        boolean res = true;
        if (getId() != other.getId()) res &= false;
        if (getName() != null && other.getName() != null){
            if (!getName().equals(other.getName())) res &= false;
        } else {
            res &= false;
        }
        if (other.isActive() != isActive()) res &= false;
        return res;
    }

    @Override
    public String toString() {
        return getId() + "\t" +
                getName() + "\t" +
                "active: " + isActive();
    }
}
