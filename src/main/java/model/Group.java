package model;

/**
 * Created by Vlad on 04.10.2016.
 */
public class Group extends Entity {
    public Group(int id, String name, boolean active) {
        setId(id);
        setName(name);
        setActive(active);
    }

    @Override
    public String toString() {
        return getId() + "\t" +
                getName() + "\t" +
                "active: " + isActive();
    }
}
