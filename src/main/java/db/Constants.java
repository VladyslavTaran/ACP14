package db;

/**
 * Created by Vlad on 05.10.2016.
 */
public class Constants {
    public static final String GET_ALL_GROUPS = "SELECT g FROM Group g";
    public static final String GET_ALL_STUDENTS = "SELECT s FROM Student s";
    public static final String GET_ALL_SUBJECTS = "SELECT s FROM Subject s";
    public static final String GET_ALL_PROFESSORS = "SELECT p FROM Professor p";

    public static final String JPA_UNIT = "hibernate-unit";
}
