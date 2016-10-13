package db;

/**
 * Created by Vlad on 05.10.2016.
 */
public class Constants {
    public static final String DB_PROPERTIES = "db.properties";
    public static final String PROP_JDBC = "jdbc";
    public static final String PROP_URL = "url";
    public static final String PROP_USER = "user";
    public static final String PROP_PWD = "pwd";

    public static final String WRONG_DATA_MESSAGE = "Something was wrong with input data. Please verify.\n";
    public static final String NO_ID_IN_DB = "No entity under pointed Id in DB.";
    public static final String CONNECTION_DETAILS_ARE_WRONG = "Something is wrong with connection details.\n";

    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_GROUP_ID = "group_id";
    public static final String FIELD_ACTIVE = "active";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_EXPERIENCE = "experience";
    public static final String FIELD_SUBJECT = "subject";

    public static final String GET_ALL_STUDENTS = "select id, name, group_id, active from students";
    public static final String GET_ALL_STUDENTS_BY_GROUP_ID = "select id, name, group_id, active from students where group_id=?";
    public static final String GET_ALL_SUBJECTS = "select id, name, description, active from subjects";
    public static final String GET_ALL_GROUPS = "select id, name, active from groups";
    public static final String GET_ALL_PROFESSORS = "select p.id, p.name, p.experience, p.active, s.name subject " +
                                                    "from professors p " +
                                                    "left join subjects s on p.id = s.id";
    public static final String GET_STUDENT_BY_ID = "select id, name, group_id, active from students " +
                                                    "where id=?";
    public static final String GET_GROUP_BY_ID = "select id, name, active from groups " +
                                                    "where id=?";
    public static final String GET_PROFESSOR_BY_ID = "select p.id, p.name, p.experience, s.name subject, p.active from professors p " +
                                                    "left join subjects s on p.subject_id = s.id " +
                                                    "where p.id=?";
    public static final String GET_SUBJECT_BY_ID = "select id, name, description, active from subjects " +
                                                    "where id=?";

    public static final String INSERT_STUDENT = "insert into students(name,group_id,active) values (?,?,?)";
    public static final String INSERT_GROUP = "insert into groups(name, active) values (?,?)";
    public static final String INSERT_SUBJECT = "insert into subjects(name,description, active) values (?,?,?)";
    public static final String INSERT_PROFESSOR = "insert into professors(name,experience,subject_id,active) values (?,?,?,?)";

    public static final String UPDATE_STUDENT = "update students set name=?, group_id=?, active=? where id=?";
    public static final String UPDATE_GROUP = "update groups set name=?, active=? where id=?";
    public static final String UPDATE_SUBJECT = "update subjects set name=?,description=?,active=? where id=?";
    public static final String UPDATE_PROFESSOR = "update professors set name=?,experience=?,subject_id=?,active=? where id=?";
}
