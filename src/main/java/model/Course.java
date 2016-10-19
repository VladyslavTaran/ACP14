package model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Vlad on 04.10.2016.
 */

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group group;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;

    public Course(Group group, Subject subject) {
        this.group = group;
        this.subject = subject;
    }

    public Course() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Course))return false;
        Course other = (Course) obj;
        boolean res = true;

        return res;
    }

    @Override
    public String toString() {
        String res = "";

        return res;
    }
}
