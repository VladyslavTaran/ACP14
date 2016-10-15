package model;

/**
 * Created by Vlad on 04.10.2016.
 */
public class Professor extends Inherit {
    private int experience;
    private String subject;

    public Professor(int id, String name, int experience, String subject, boolean active) {
        setId(id);
        setName(name);
        setActive(active);
        this.experience = experience;
        this.subject = subject;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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
            if (!getSubject().equals(other.getSubject())) res &= false;
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
                getSubject() +
                "active: " + isActive();
    }
}
