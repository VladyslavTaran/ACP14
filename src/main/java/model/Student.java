package model;

/**
 * Created by Vlad on 04.10.2016.
 */
public class Student extends Entity{
    private int groupId;

    public Student(int id, String name, int groupId, boolean active) {
        setName(name);
        setId(id);
        setActive(active);
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return getId() + " " +
                getName() + "\t" +
                groupId + " " +
                "active:" + isActive();
    }
}
