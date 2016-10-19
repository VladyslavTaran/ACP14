package model;

import javax.persistence.*;

/**
 * Created by Vlad on 04.10.2016.
 */

@Entity
@Table(name = "professors")
public class Professor extends Inherit {
    @OneToOne(cascade = CascadeType.PERSIST,orphanRemoval = true)
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;

    @Column(name = "experience")
    private int experience;

    public Professor(String name, int experience, Subject subject, boolean active) {
        setName(name);
        setActive(active);
        this.experience = experience;
        this.subject = subject;
    }

    public Professor() {
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public int hashCode() {
        return getId()*
                getName().hashCode() +
                (isActive() ? 1 : 0) +
                experience +
                subject.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Professor))return false;
        Professor other = (Professor) obj;
        boolean res = true;
        if (getId() != other.getId()) res &= false;
        if (getName() != null && other.getName() != null){
            if (!getName().equals(other.getName())) res &= false;
        } else {
            res &= false;
        }
        if (isActive() != other.isActive()) res &= false;
        if (experience != other.getExperience()) res &= false;
        if (subject != null && other.getSubject() != null){
            if (getSubject().getId() != other.getSubject().getId()) res &= false;
        } else {
            res &= false;
        }
        return res;
    }

    @Override
    public String toString() {
        return getId() + "\t" +
                getName() + "\t\t" +
                getExperience() + "\t" +
                getSubject().getName() +
                "active: " + isActive();
    }
}
