package db;

import model.Group;
import model.Professor;
import model.Student;
import model.Subject;
import model.exception.NoSuchEntityException;
import model.exception.WrongDataException;
import org.apache.log4j.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Vlad on 06.10.2016.
 */
public class Service {
    private DAO dao;
    private static Logger log = Logger.getLogger(Service.class.getName());

    public Service(DAO dao) throws IOException {
        this.dao = dao;
    }

    public List<Student> getAllStudents(){
        log.info("execute getAllStudents method");
        return dao.getAllStudents();
    }

    public List<Group> getAllGroups(){
        log.info("execute getAllGroups method");
        return dao.getAllGroups();
    }

    public List<Subject> getAllSubjects(){
        log.info("execute getAllSubjects method");
        return dao.getAllSubjects();
    }

    public List<Professor> getAllProfessors(){
        log.info("execute getAllProfessors method");
        return dao.getAllProfessors();
    }

    public boolean addStudent(String name, int groupId, boolean isActive) throws WrongDataException {
        try {
            log.info("execute addStudent method with parameters: name= " + name + "groupId= " + groupId + "isActive= " + isActive);
            return dao.addStudent(name, groupId, isActive);
        }catch (SQLException e) {
            log.error("addStudent method failed", e);
            throw new WrongDataException(Constants.WRONG_DATA_MESSAGE + e.getMessage());
        }
    }

    public boolean updateStudentInfo(int studentIdToUpdate, String nameNew, int group_idNew, boolean isActiveNew) throws WrongDataException, NoSuchEntityException {
        try {
            log.info("execute updateSrudentInfo method with parameters: " +
                    "studentId= " + studentIdToUpdate +
                    "new name= " + nameNew +
                    "new group id= " + group_idNew +
                    "isActive= " + isActiveNew);
            if (getStudentById(studentIdToUpdate) != null){
                return dao.updateStudent(studentIdToUpdate,nameNew,group_idNew,isActiveNew);
            }
        } catch (SQLException e) {
            log.error("updateStudentInfo method failed", e);
            throw new WrongDataException(Constants.WRONG_DATA_MESSAGE + e.getMessage());
        }
        return false;
    }

    public Student getStudentById(int studentId) throws NoSuchEntityException {
        log.info("execute getStudentById method with student id= " + studentId);
        return dao.getStudentById(studentId);
    }

    public List<Student> getAllStudentsByGroupId(int groupId) throws NoSuchEntityException {
        log.info("execute getAllStudentsByGroupId method with group id= " + groupId);
        if (getGroupById(groupId) != null) {
            return dao.getAllStudentByGroupId(groupId);
        } else {
            return null;
        }
    }

    public Group getGroupById(int groupId) throws NoSuchEntityException {
        try {
            log.info("execute getGroupById method with group id= " + groupId);
            return dao.getGroupById(groupId);
        } catch (NoSuchEntityException e) {
            log.error("getGroupById failed", e);
            throw e;
        }
    }

    public boolean addGroup(String name, boolean isActive) throws WrongDataException {
        try {
            log.info("execute addGroup method with name= " + name +
                    "isActive= " + isActive);
            return dao.addGroup(name,isActive);
        } catch (SQLException e) {
            log.error("addGroup method failed", e);
            throw new WrongDataException(Constants.WRONG_DATA_MESSAGE + e.getMessage());
        }
    }

    public boolean updateGroupInfo(int groupIdToUpdate, String nameNew, boolean isActiveNew) throws WrongDataException, NoSuchEntityException {
        try {
            log.info("execute updateGroupINfo with group id= " + groupIdToUpdate +
                                                    " new name= " + nameNew +
                                                    " isActive= " + isActiveNew);
            if (getGroupById(groupIdToUpdate) != null){
                try {
                    return dao.updateGroup(groupIdToUpdate, nameNew, isActiveNew);
                } catch (SQLException e) {
                    log.error("addGroup method failed", e);
                    throw new WrongDataException(Constants.WRONG_DATA_MESSAGE + e.getMessage());
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
            return dao.addSubject(name,description,isActive);
        } catch (SQLException e) {
            log.error("addSubject method failed", e);
            throw new WrongDataException(Constants.WRONG_DATA_MESSAGE + e.getMessage());
        }
    }

    public Subject getSubjectById (int subjectId) throws NoSuchEntityException {
        try {
            log.info("execute getSubjectById method with subject Id=" + subjectId);
            return dao.getSubjectById(subjectId);
        } catch (SQLException e) {
            log.error("getSubjectById method failed", e);
            throw new NoSuchEntityException(Constants.NO_ID_IN_DB + e.getMessage());
        }
    }

    public boolean addProfessor(String name, int experience, int subject_id, boolean isActive) throws WrongDataException {
        try {
            log.info("addProfessor method with name=" + name +
                                                " experience=" + experience +
                                                " subject id= " + subject_id +
                                                " isActive=" + isActive);
            return dao.addProfessor(name,experience,subject_id,isActive);
        } catch (SQLException e) {
            log.error("addProfessor method failed", e);
            throw new WrongDataException(Constants.WRONG_DATA_MESSAGE + e.getMessage());
        }
    }

    public Professor getProfessorById(int professorId) throws NoSuchEntityException {
        try {
            log.info("execute getProfessorById method with professor id= " + professorId);
            return dao.getProfessorById(professorId);
        } catch (SQLException e) {
            log.error("getProfessorById method failed", e);
            throw new NoSuchEntityException(Constants.NO_ID_IN_DB + e.getMessage());
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
                return dao.updateProfessor(professorIdToUpdate,nameNew,experienceNew,subject_idNew,isActiveNew);
            }
        } catch (SQLException e) {
            log.error("updateProfessorInfo method failed", e);
            throw new WrongDataException(Constants.WRONG_DATA_MESSAGE + e.getMessage());
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
                return dao.updateSubject(subjectIdToUpdate, nameNew, descriptionNew, isActiveNew);
            }catch (SQLException e){
                log.error("updateSubjectInfo method failed", e);
                throw new WrongDataException(Constants.WRONG_DATA_MESSAGE + e.getMessage());
            }
        }
        return false;
    }
}
