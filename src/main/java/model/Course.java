package model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Vlad on 04.10.2016.
 */

@Entity
@Table(name = "courses")
public class Course {
    @Transient
    private List<Group> groups;
    @Transient
    private List<Subject> subjects;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int id;

    public Course(int id, List<Group> groups, List<Subject> subjects) {
        this.id = id;
        this.groups = groups;
        this.subjects = subjects;
    }

    public Course() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }


    @Override
    public int hashCode() {
        return getGroups().hashCode() + getSubjects().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Course))return false;
        Course other = (Course) obj;
        boolean res = true;
        if (groups != null && other.getGroups() !=null) {
            if (!getGroups().equals(other.getGroups())){
                res &= false;
            }
        }
        if (subjects != null && other.getSubjects() !=null) {
            if (!getSubjects().equals(other.getSubjects())){
                res &= false;
            }
        }
        return res;
    }

    @Override
    public String toString() {
        String res = "";
        for (Group group : groups) {
            res += group.getName();
        }
        for (Subject subject : subjects) {
            res += subject.getName();
        }
        return res;
    }
}
