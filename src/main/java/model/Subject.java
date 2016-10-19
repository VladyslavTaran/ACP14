package model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Vlad on 04.10.2016.
 */

@Entity
@Table(name = "subjects")
public class Subject extends Inherit {
    @Column(name = "description")
    private String description;

    @OneToOne(mappedBy = "subject")
    private Professor professor;

    @OneToMany(mappedBy = "group")
    private List<Course> courses;

    public Subject(String name, String description, boolean active) {
        this.description = description;
        setName(name);
        setActive(active);
    }

    public Subject() {
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        return getId()*
                getName().hashCode() +
                (isActive() ? 1 : 0) +
                description.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Subject))return false;
        Subject other = (Subject) obj;
        boolean res = true;
        if (getId() != other.getId()) res &= false;
        if (getName() != null && other.getName() != null){
            if (!getName().equals(other.getName())) res &= false;
        } else {
            res &= false;
        }
        if (isActive() != other.isActive()) res &= false;
        if (description != null && other.getDescription() != null){
            if (!description.equals(other.getDescription())) res &= false;
        } else {
            res &= false;
        }
        return res;
    }

    @Override
    public String toString() {
        return getId() + "\t" +
                getName() + "\t\t\t" +
                getDescription() + "\t" +
                "active: " + isActive();
    }
}
