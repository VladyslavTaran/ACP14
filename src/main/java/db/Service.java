package db;

import model.Group;
import model.Professor;
import model.Student;
import model.Subject;
import exception.DBConnectionException;
import exception.NoSuchEntityException;
import exception.WrongDataException;
import org.apache.log4j.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Vlad on 06.10.2016.
 */
public class Service {
    private DAOStudent daoStudent;
    private DAOGroup daoGroup;
    private DAOSubject daoSubject;
    private DAOProfessor daoProfessor;
    private static Logger log = Logger.getLogger(Service.class.getName());

    public Service(DAOStudent daoStudent,
                   DAOGroup daoGroup,
                   DAOSubject daoSubject,
                   DAOProfessor daoProfessor) throws IOException {
        this.daoStudent = daoStudent;
        this.daoGroup = daoGroup;
        this.daoSubject = daoSubject;
        this.daoProfessor = daoProfessor;
    }

    public List<Student> getAllStudents(){
        log.info("execute getAllStudents method");
        try {
            return daoStudent.getAllStudents();
        } catch (SQLException e) {
            log.error("getAllStudents method failed", e);
            return null;
        } catch (WrongDataException e) {
            log.error("getAllStudents method failed", e);
            return null;
        } catch (DBConnectionException e) {
            log.error("getAllStudents method failed", e);
            return null;
        }
    }

    public List<Group> getAllGroups(){
        log.info("execute getAllGroups method");
        try {
            return daoGroup.getAllGroups();
        } catch (SQLException e) {
            log.error("getAllGroups method failed", e);
            return null;
        } catch (DBConnectionException e) {
            log.error("getAllGroups method failed", e);
            return null;
        } catch (WrongDataException e) {
            log.error("getAllGroups method failed", e);
            return null;
        }
    }

    public List<Subject> getAllSubjects(){
        log.info("execute getAllSubjects method");
        try {
            return daoSubject.getAllSubjects();
        } catch (DBConnectionException e) {
            log.error("getAllSubjects method failed", e);
            return null;
        } catch (SQLException e) {
            log.error("getAllSubjects method failed", e);
            return null;
        } catch (WrongDataException e) {
            log.error("getAllSubjects method failed", e);
            return null;
        }
    }

    public List<Professor> getAllProfessors(){
        log.info("execute getAllProfessors method");
        try {
            return daoProfessor.getAllProfessors();
        } catch (DBConnectionException e) {
            log.error("getProfessors method failed", e);
            return null;
        } catch (SQLException e) {
            log.error("getProfessors method failed", e);
            return null;
        } catch (WrongDataException e) {
            log.error("getProfessors method failed", e);
            return null;
        }
    }

    public boolean addStudent(String name, int groupId, boolean isActive) throws WrongDataException {
        try {
            log.info("execute addStudent method with parameters: name= " + name + "groupId= " + groupId + "isActive= " + isActive);
            return daoStudent.addStudent(name, groupId, isActive);
        }catch (SQLException e) {
            log.error("addStudent method failed", e);
            throw new WrongDataException(Constants.WRONG_DATA_MESSAGE + e.getMessage());
        } catch (DBConnectionException e) {
            log.error("addStudent method failed", e);
            return false;
        }
    }

    public boolean updateStudentInfo(int studentIdToUpdate, String nameNew, int group_idNew, boolean isActiveNew) throws WrongDataException, NoSuchEntityException {
        try {
            log.info("execute updateStudentInfo method with parameters: " +
                    "studentId= " + studentIdToUpdate +
                    "new name= " + nameNew +
                    "new group id= " + group_idNew +
                    "isActive= " + isActiveNew);
            if (getStudentById(studentIdToUpdate) != null){
                return daoStudent.updateStudent(studentIdToUpdate,nameNew,group_idNew,isActiveNew);
            }
        } catch (SQLException e) {
            log.error("updateStudentInfo method failed", e);
            throw new WrongDataException(Constants.WRONG_DATA_MESSAGE + e.getMessage());
        } catch (DBConnectionException e) {
            log.error("updateStudentInfo method failed", e);
        }
        return false;
    }

    public Student getStudentById(int studentId) throws NoSuchEntityException {
        log.info("execute getStudentById method with student id= " + studentId);
        try {
            return daoStudent.getStudentById(studentId);
        } catch (DBConnectionException e) {
            log.error("getStudentById method failed", e);
            return null;
        } catch (SQLException e) {
            log.error("getStudentById method failed", e);
            return null;
        }
    }

    public List<Student> getAllStudentsByGroupId(int groupId) throws NoSuchEntityException {
        log.info("execute getAllStudentsByGroupId method with group id= " + groupId);
        if (getGroupById(groupId) != null) {
            try {
                return daoStudent.getAllStudentByGroupId(groupId);
            } catch (DBConnectionException e) {
                log.error("getAllStudentsByGroupId method failed", e);
                return null;
            }
        } else {
            return null;
        }
    }

    public Group getGroupById(int groupId) throws NoSuchEntityException {
        try {
            log.info("execute getGroupById method with group id= " + groupId);
            return daoGroup.getGroupById(groupId);
        } catch (NoSuchEntityException e) {
            log.error("getGroupById failed", e);
            throw e;
        } catch (DBConnectionException e) {
            log.error("getGroupById failed", e);
            return null;
        } catch (SQLException e) {
            log.error("getGroupById failed", e);
            return null;
        }
    }

    public boolean addGroup(String name, boolean isActive) throws WrongDataException {
        try {
            log.info("execute addGroup method with name= " + name +
                    "isActive= " + isActive);
            return daoGroup.addGroup(name,isActive);
        } catch (SQLException e) {
            log.error("addGroup method failed", e);
            throw new WrongDataException(Constants.WRONG_DATA_MESSAGE + e.getMessage());
        } catch (DBConnectionException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateGroupInfo(int groupIdToUpdate, String nameNew, boolean isActiveNew) throws WrongDataException, NoSuchEntityException {
        try {
            log.info("execute updateGroupINfo with group id= " + groupIdToUpdate +
                                                    " new name= " + nameNew +
                                                    " isActive= " + isActiveNew);
            if (getGroupById(groupIdToUpdate) != null){
                try {
                    return daoGroup.updateGroup(groupIdToUpdate, nameNew, isActiveNew);
                } catch (SQLException e) {
                    log.error("addGroup method failed", e);
                    throw new WrongDataException(Constants.WRONG_DATA_MESSAGE + e.getMessage());
                } catch (DBConnectionException e) {
                    log.error("addGroup method failed", e);
                    return false;
                }
            }
        } catch (NoSuchEntityException e) {
            log.error("addGroup method failed", e);
            throw e;
        }
        return false;
    }

    public boolean addSubject(String name, String description, boolean isActive) throws WrongDataException {
        try {
            log.info("execute addSubject method with name= " + name +
                                                    " description=" + description +
                                                    " isActive=" + isActive);
            return daoSubject.addSubject(name,description,isActive);
        } catch (SQLException e) {
            log.error("addSubject method failed", e);
            throw new WrongDataException(Constants.WRONG_DATA_MESSAGE + e.getMessage());
        } catch (DBConnectionException e) {
            log.error("addSubject method failed", e);
            return false;
        }
    }

    public Subject getSubjectById (int subjectId) throws NoSuchEntityException {
        try {
            log.info("execute getSubjectById method with subject Id=" + subjectId);
            return daoSubject.getSubjectById(subjectId);
        } catch (SQLException e) {
            log.error("getSubjectById method failed", e);
            throw new NoSuchEntityException(Constants.NO_ID_IN_DB + e.getMessage());
        } catch (DBConnectionException e) {
            log.error("getSubjectById method failed", e);
            return null;
        }
    }

    public boolean addProfessor(String name, int experience, int subject_id, boolean isActive) throws WrongDataException {
        try {
            log.info("addProfessor method with name=" + name +
                                                " experience=" + experience +
                                                " subject id= " + subject_id +
                                                " isActive=" + isActive);
            return daoProfessor.addProfessor(name,experience,subject_id,isActive);
        } catch (SQLException e) {
            log.error("addProfessor method failed", e);
            throw new WrongDataException(Constants.WRONG_DATA_MESSAGE + e.getMessage());
        } catch (DBConnectionException e) {
            log.error("addProfessor method failed", e);
            return false;
        }
    }

    public Professor getProfessorById(int professorId) throws NoSuchEntityException {
        try {
            log.info("execute getProfessorById method with professor id= " + professorId);
            return daoProfessor.getProfessorById(professorId);
        } catch (SQLException e) {
            log.error("getProfessorById method failed", e);
            throw new NoSuchEntityException(Constants.NO_ID_IN_DB + e.getMessage());
        } catch (DBConnectionException e) {
            log.error("getProfessorById method failed", e);
            return null;
        }
    }

    public boolean updateProfessorInfo(int professorIdToUpdate, String nameNew, int experienceNew, int subject_idNew, boolean isActiveNew) throws WrongDataException, NoSuchEntityException {
        try {
            log.info("execute updateProfessorInfo method with professor id=" + professorIdToUpdate +
                                                            " new name=" + nameNew +
                                                            " new experience=" + experienceNew +
                                                            " new subject id=" + subject_idNew +
                                                            " isActive=" + isActiveNew);
            if (getProfessorById(professorIdToUpdate) != null){
                return daoProfessor.updateProfessor(professorIdToUpdate,nameNew,experienceNew,subject_idNew,isActiveNew);
            }
        } catch (SQLException e) {
            log.error("updateProfessorInfo method failed", e);
            throw new WrongDataException(Constants.WRONG_DATA_MESSAGE + e.getMessage());
        } catch (DBConnectionException e) {
            log.error("updateProfessorInfo method failed", e);
            return false;
        }
        return false;
    }

    public boolean updateSubjectInfo(int subjectIdToUpdate, String nameNew, String descriptionNew, boolean isActiveNew) throws WrongDataException, NoSuchEntityException {
        log.error("execute updateSubjectInfo method with subject id=" + subjectIdToUpdate +
                                                " new name=" + nameNew +
                                                " new description=" + descriptionNew +
                                                " isActive=" + isActiveNew);
        if (getSubjectById(subjectIdToUpdate) != null){
            try {
                return daoSubject.updateSubject(subjectIdToUpdate, nameNew, descriptionNew, isActiveNew);
            }catch (SQLException e){
                log.error("updateSubjectInfo method failed", e);
                throw new WrongDataException(Constants.WRONG_DATA_MESSAGE + e.getMessage());
            } catch (DBConnectionException e) {
                log.error("updateSubjectInfo method failed", e);
                return false;
            }
        }
        return false;
    }
}
