package model;

/**
 * Created by Vlad on 04.10.2016.
 */
public class Subject extends Entity {
    private String description;

    public Subject(int id, String name, String description, boolean active) {
        this.description = description;
        setId(id);
        setName(name);
        setActive(active);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return getId() + "\t" +
                getName() + "\t\t\t" +
                getDescription() + "\t" +
                "active: " + isActive();
    }
}
