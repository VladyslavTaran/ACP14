package db;

import model.Group;
import model.Professor;
import model.Student;
import model.Subject;
import model.exception.DBConnectionException;
import model.exception.NoSuchEntityException;
import model.exception.WrongDataException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Vlad on 04.10.2016.
 */
public class DAO implements IDBController{

    private Connection connection = null;
    private ResultSet resultset = null;
    private PreparedStatement preparedStatement = null;
    private Properties properties;

    public DAO() {
        loadProperties();
    }

    private boolean connectToDB() throws DBConnectionException {
        try {
            Class.forName(properties.getProperty(Constants.PROP_JDBC));
            connection =
                    DriverManager.
                            getConnection
                                    (properties.getProperty(Constants.PROP_URL),
                                     properties.getProperty(Constants.PROP_USER),
                                     properties.getProperty(Constants.PROP_PWD));
            return true;
        } catch (ClassNotFoundException e) {
            throw new DBConnectionException(Constants.CONNECTION_DETAILS_ARE_WRONG + e.getMessage());
        } catch (SQLException e) {
            throw new DBConnectionException(Constants.CONNECTION_DETAILS_ARE_WRONG + e.getMessage());
        }
    }

    private void closeConnection() throws DBConnectionException {
        try {
            if (resultset != null) {
                resultset.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DBConnectionException(Constants.CONNECTION_DETAILS_ARE_WRONG);
        }
    }


    private ResultSet executeSQLQuery(String sqlQuery) throws WrongDataException {
        try {
            resultset = connection.createStatement().executeQuery(sqlQuery);
            return resultset;
        } catch (SQLException e) {
            throw new WrongDataException(Constants.WRONG_DATA_MESSAGE);
        }
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();

        try {
            connectToDB();
            resultset = executeSQLQuery(Constants.GET_ALL_STUDENTS);
            if (resultset != null) {
                while (resultset.next()) {
                    students.add(new Student(
                                    resultset.getInt(Constants.FIELD_ID),
                                    resultset.getString(Constants.FIELD_NAME),
                                    resultset.getInt(Constants.FIELD_GROUP_ID),
                                    resultset.getBoolean(Constants.FIELD_ACTIVE)
                            )
                    );
                }
            }
        } catch (SQLException e) {
            return null;
        } catch (WrongDataException e) {
            return null;
        } catch (DBConnectionException e) {
            return null;
        } finally {
            try {
                closeConnection();
            } catch (DBConnectionException e) {
                return null;
            }
        }
        return students;
    }

    @Override
    public List<Group> getAllGroups() {
        List<Group> groups = new ArrayList<>();

        try {
            connectToDB();
            resultset = executeSQLQuery(Constants.GET_ALL_GROUPS);
            if (resultset != null) {
                while (resultset.next()) {
                    groups.add(new Group(
                                        resultset.getInt(Constants.FIELD_ID),
                                        resultset.getString(Constants.FIELD_NAME),
                                        resultset.getBoolean(Constants.FIELD_ACTIVE)
                                        )
                              );
                }
            }
        } catch (SQLException e) {
            return null;
        } catch (WrongDataException e) {
            return null;
        } catch (DBConnectionException e) {
            return null;
        } finally {
            try {
                closeConnection();
            } catch (DBConnectionException e) {
                return null;
            }
        }
        return groups;
    }

    @Override
    public List<Subject> getAllSubjects(){
        List<Subject> subjects = new ArrayList<>();

        try {
            connectToDB();
            resultset = executeSQLQuery(Constants.GET_ALL_SUBJECTS);
            if (resultset != null) {
                while (resultset.next()) {
                    subjects.add(new Subject(
                                    resultset.getInt(Constants.FIELD_ID),
                                    resultset.getString(Constants.FIELD_NAME),
                                    resultset.getString(Constants.FIELD_DESCRIPTION),
                                    resultset.getBoolean(Constants.FIELD_ACTIVE)
                            )
                    );
                }
            }
        } catch (SQLException e) {
            return null;
        } catch (WrongDataException e) {
            return null;
        } catch (DBConnectionException e) {
            return null;
        } finally {
            try {
                closeConnection();
            } catch (DBConnectionException e) {
                return null;
            }
        }
        return subjects;
    }

    @Override
    public List<Professor> getAllProfessors(){
        List<Professor> professors = new ArrayList<>();

        try {
            connectToDB();
            resultset = executeSQLQuery(Constants.GET_ALL_PROFESSORS);
            if (resultset != null) {
                while (resultset.next()) {
                    professors.add(new Professor(
                                    resultset.getInt(Constants.FIELD_ID),
                                    resultset.getString(Constants.FIELD_NAME),
                                    resultset.getInt(Constants.FIELD_EXPERIENCE),
                                    resultset.getString(Constants.FIELD_SUBJECT),
                                    resultset.getBoolean(Constants.FIELD_ACTIVE)
                            )
                    );
                }
            }
        } catch (SQLException e) {
            return null;
        } catch (WrongDataException e) {
            return null;
        } catch (DBConnectionException e) {
            return null;
        } finally {
            try {
                closeConnection();
            } catch (DBConnectionException e) {
                return null;
            }
        }
        return professors;
    }

    @Override
    public boolean addStudent(String name, int group_id, boolean isActive) throws SQLException {
        try {
            connectToDB();
            preparedStatement = connection.prepareStatement(Constants.INSERT_STUDENT);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, group_id);
            preparedStatement.setInt(3, (isActive ? 1 : 0));

            preparedStatement.execute();
        } catch (DBConnectionException e) {
            return false;
        } finally {
            try {
                closeConnection();
            } catch (DBConnectionException e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean updateStudent(int id, String name, int group_id, boolean isActive) throws SQLException {
        try {
            connectToDB();
            preparedStatement = connection.prepareStatement(Constants.UPDATE_STUDENT);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, group_id);
            preparedStatement.setInt(3, (isActive ? 1 : 0));
            preparedStatement.setInt(4, id);

            preparedStatement.execute();
        } catch (DBConnectionException e) {
            return false;
        } finally {
            try {
                closeConnection();
            } catch (DBConnectionException e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Student getStudentById(int studentId) throws NoSuchEntityException {
        Student student = null;

        try {
            connectToDB();
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.GET_STUDENT_BY_ID);
            preparedStatement.setInt(1, studentId);

            resultset = preparedStatement.executeQuery();

            if (resultset != null) {
                resultset.next();
                student = new Student(
                                        resultset.getInt(Constants.FIELD_ID),
                                        resultset.getString(Constants.FIELD_NAME),
                                        resultset.getInt(Constants.FIELD_GROUP_ID),
                                        resultset.getBoolean(Constants.FIELD_ACTIVE)
                                     );

            }
        } catch (DBConnectionException e) {
            return null;
        } catch (SQLException e) {
            throw new NoSuchEntityException(Constants.NO_ID_IN_DB + e.getMessage());
        } finally {
            try {
                closeConnection();
            } catch (DBConnectionException e) {
                return null;
            }
        }
        return student;
    }

    @Override
    public List<Student> getAllStudentByGroupId(int groupId) {
        List<Student> students = new ArrayList<>();

        try {
            connectToDB();
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.GET_ALL_STUDENTS_BY_GROUP_ID);
            preparedStatement.setInt(1, groupId);

            resultset = preparedStatement.executeQuery();

            if (resultset != null) {
                while(resultset.next()) {
                    students.add(new Student(
                                            resultset.getInt(Constants.FIELD_ID),
                                            resultset.getString(Constants.FIELD_NAME),
                                            resultset.getInt(Constants.FIELD_GROUP_ID),
                                            resultset.getBoolean(Constants.FIELD_ACTIVE)
                                            )
                                );
                };
            }
        } catch (DBConnectionException e) {
            return null;
        } catch (SQLException e) {
            throw new NoClassDefFoundError(Constants.NO_ID_IN_DB + e.getMessage());
        } finally {
            try {
                closeConnection();
            } catch (DBConnectionException e) {
                return null;
            }
        }
        return students;
    }

    @Override
    public Group getGroupById(int groupId) throws NoSuchEntityException {
        Group group = null;

        try {
            connectToDB();
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.GET_GROUP_BY_ID);
            preparedStatement.setInt(1, groupId);

            resultset = preparedStatement.executeQuery();

            if (resultset != null) {
                resultset.next();
                group = new Group(
                        resultset.getInt(Constants.FIELD_ID),
                        resultset.getString(Constants.FIELD_NAME),
                        resultset.getBoolean(Constants.FIELD_ACTIVE)
                );
            }
        } catch (DBConnectionException e) {
            return null;
        } catch (SQLException e) {
            throw new NoSuchEntityException(Constants.NO_ID_IN_DB + e.getMessage());
        } finally {
            try {
                closeConnection();
            } catch (DBConnectionException e) {
                return null;
            }
        }
        return group;
    }

    @Override
    public boolean addGroup(String name, boolean isActive) throws SQLException {
        try {
            connectToDB();
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.INSERT_GROUP);

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, (isActive ? 1 : 0));

            preparedStatement.execute();
        } catch (DBConnectionException e) {
            return false;
        }finally {
            try {
                closeConnection();
            } catch (DBConnectionException e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean updateGroup(int id, String name, boolean isActive) throws SQLException {
        try {
            connectToDB();
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.UPDATE_GROUP);

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, (isActive ? 1 : 0));
            preparedStatement.setInt(3, id);

            preparedStatement.execute();
        } catch (DBConnectionException e) {
            return false;
        }finally {
            try {
                closeConnection();
            } catch (DBConnectionException e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addSubject(String name, String description, boolean isActive) throws SQLException {
        try {
            connectToDB();
            PreparedStatement preparedStatement =  connection.prepareStatement(Constants.INSERT_SUBJECT);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, (isActive ? 1 : 0));

            preparedStatement.execute();
        } catch (DBConnectionException e) {
            return false;
        } finally {
            try {
                closeConnection();
            } catch (DBConnectionException e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Subject getSubjectById(int subjectId) throws SQLException {
        Subject subject = null;

        try {
            connectToDB();
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.GET_SUBJECT_BY_ID);
            preparedStatement.setInt(1, subjectId);

            resultset = preparedStatement.executeQuery();

            if (resultset != null) {
                resultset.next();
                subject = new Subject(
                        resultset.getInt(Constants.FIELD_ID),
                        resultset.getString(Constants.FIELD_NAME),
                        resultset.getString(Constants.FIELD_DESCRIPTION),
                        resultset.getBoolean(Constants.FIELD_ACTIVE)
                );
            }
        } catch (DBConnectionException e) {
            return null;
        } finally {
            try {
                closeConnection();
            } catch (DBConnectionException e) {
                return null;
            }
        }
        return subject;
    }

    @Override
    public boolean updateSubject(int id, String name, String description, boolean isActive) throws SQLException {
        try {
            connectToDB();
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.UPDATE_SUBJECT);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, (isActive ? 1 : 0));
            preparedStatement.setInt(4, id);

            preparedStatement.execute();
        } catch (DBConnectionException e) {
            return false;
        }finally {
            try {
                closeConnection();
            } catch (DBConnectionException e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addProfessor(String name, int experience, int subject_id, boolean isActive) throws SQLException {
        try {
            connectToDB();
            PreparedStatement preparedStatement =  connection.prepareStatement(Constants.INSERT_PROFESSOR);

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, experience);
            preparedStatement.setInt(3, subject_id);
            preparedStatement.setInt(4, (isActive ? 1 : 0));

            preparedStatement.execute();
        } catch (DBConnectionException e) {
            return false;
        } finally {
            try {
                closeConnection();
            } catch (DBConnectionException e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean updateProfessor(int id, String name, int experience, int subject_id, boolean isActive) throws SQLException {
        try {
            connectToDB();
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.UPDATE_PROFESSOR);

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, experience);
            preparedStatement.setInt(3, subject_id);
            preparedStatement.setInt(4, (isActive ? 1 : 0));
            preparedStatement.setInt(5, id);

            preparedStatement.execute();
        } catch (DBConnectionException e) {
            return false;
        } finally {
            try {
                closeConnection();
            } catch (DBConnectionException e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Professor getProfessorById(int professorId) throws SQLException {
        Professor professor = null;

        try {
            connectToDB();
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.GET_PROFESSOR_BY_ID);
            preparedStatement.setInt(1, professorId);

            resultset = preparedStatement.executeQuery();

            if (resultset != null) {
                resultset.next();
                professor = new Professor(
                        resultset.getInt(Constants.FIELD_ID),
                        resultset.getString(Constants.FIELD_NAME),
                        resultset.getInt(Constants.FIELD_EXPERIENCE),
                        resultset.getString(Constants.FIELD_SUBJECT),
                        resultset.getBoolean(Constants.FIELD_ACTIVE)
                );
            }
        } catch (DBConnectionException e) {
            return null;
        } finally {
            try {
                closeConnection();
            } catch (DBConnectionException e) {
                return null;
            }
        }
        return professor;
    }

    private void loadProperties(){
        properties = new Properties();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(Constants.DB_PROPERTIES);

        if (inputStream != null) {
            try {
                properties.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
