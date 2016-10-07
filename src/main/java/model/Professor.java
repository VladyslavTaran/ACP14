package model;

/**
 * Created by Vlad on 04.10.2016.
 */
public class Professor extends Entity {
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
    public String toString() {
        return getId() + "\t" +
                getName() + "\t\t" +
                getExperience() + "\t" +
                getSubject() +
                "active: " + isActive();
    }
}
