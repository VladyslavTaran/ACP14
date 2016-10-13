package model;

/**
 * Created by Vlad on 04.10.2016.
 */
public class Course {
    private int groupId;
    private int subjectId;

    public Course(int groupId, int subjectId) {
        this.groupId = groupId;
        this.subjectId = subjectId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public int hashCode() {
        return getGroupId()*getSubjectId() + getGroupId();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Course))return false;
        Course other = (Course) obj;
        boolean res = true;
        if (groupId != other.getGroupId()) res &= false;
        if (subjectId != other.getSubjectId()) res &= false;
        return res;
    }

    @Override
    public String toString() {
        return getGroupId() + "\t" +
                getSubjectId();
    }
}
