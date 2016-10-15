package model;

/**
 * Created by Vlad on 04.10.2016.
 */
public class Group extends Inherit {
    public Group(int id, String name, boolean active) {
        setId(id);
        setName(name);
        setActive(active);
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
