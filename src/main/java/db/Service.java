package db;

import model.Group;
import model.Professor;
import model.Student;
import model.Subject;
import exception.DBConnectionException;
import exception.NoSuchEntityException;
import exception.WrongDataException;
import org.apache.log4j.*;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Vlad on 06.10.2016.
 */
public class Service {
    private StudentDAO daoStudent;
    private GroupDAO daoGroup;
    private SubjectDAO daoSubject;
    private ProfessorDAO daoProfessor;
    private static Logger log = Logger.getLogger(Service.class.getName());

    public Service(EntityManagerFactory factory) throws IOException {
        daoStudent = new StudentDAO(factory);
        daoGroup = new GroupDAO(factory);
        daoSubject = new SubjectDAO(factory);
        daoProfessor = new ProfessorDAO(factory);
    }

    public List<Student> getAllStudents(){
        log.info("execute getAllStudents method");
        return daoStudent.getAll();
    }

    public List<Group> getAllGroups(){
        log.info("execute getAllGroups method");
        return daoGroup.getAll();
    }

    public List<Subject> getAllSubjects(){
        log.info("execute getAllSubjects method");
        return daoSubject.getAll();
    }

    public List<Professor> getAllProfessors(){
        log.info("execute getAllProfessors method");
        return daoProfessor.getAll();
    }

    public Student addStudent(String name, int groupId, boolean isActive) throws NoSuchEntityException {
        log.info("execute addStudent method with name=" + name +
                                                " groupId=" + groupId +
                                                " active=" + isActive);
        Group group = daoGroup.getById(groupId);
        if (group != null){
            Student student = new Student(name, group, isActive);
            daoStudent.add(student);
            log.info("Student has been successfully added");
            return student;
        } else {
            log.error("There is no group in db under id=" + groupId);
            throw new NoSuchEntityException("There is no group in db under id=" + groupId);
        }
    }

    public Student updateStudentInfo(int studentIdToUpdate, String nameNew, int group_idNew, boolean isActiveNew) throws NoSuchEntityException {
        log.info("execute updateStudentInfo method");
        Student student = daoStudent.getById(studentIdToUpdate);
        if (student != null){
            Group group = daoGroup.getById(group_idNew);
            if (group != null){
                student.setGroup(group);
                student.setName(nameNew);
                student.setActive(isActiveNew);
                daoStudent.update(student);
                return student;
            }else{
                log.error("There is no group in db under Id=" + group_idNew);
                throw new NoSuchEntityException("There is no group in db under Id=" + group_idNew);
            }
        }else{
            log.error("There is no student in db under Id=" + studentIdToUpdate);
            throw new NoSuchEntityException("There is no student in db under Id=" + studentIdToUpdate);
        }
    }

    public Student getStudentById(int studentId) throws NoSuchEntityException {
        log.info("execute getStudentById method with student id= " + studentId);
        Student student = daoStudent.getById(studentId);
        if (student != null){
            return student;
        }else{
            log.error("There is no student in db under Id=" + studentId);
            throw new NoSuchEntityException("There is no student in db under Id=" + studentId);
        }
    }

    public List<Student> getAllStudentsByGroupId(int groupId) throws NoSuchEntityException {
        log.info("execute getAllStudentsByGroupId method with group id= " + groupId);
        List<Student> students = daoStudent.getAllByGroupId(groupId);
        if (students != null){
            return students;
        } else {
            log.error("There are no students in db that belong to group under Id=" + groupId);
            throw new NoSuchEntityException("There are no students in db that belong to group under Id=" + groupId);
        }
    }

    public Group getGroupById(int groupId) throws NoSuchEntityException {
        log.info("execute getGroupById method with group id= " + groupId);
        Group group = daoGroup.getById(groupId);
        if (group != null){
            return group;
        } else {
            log.error("There is no group in db under Id=" + groupId);
            throw new NoSuchEntityException("There is no group in db under Id=" + groupId);
        }
    }

    public Group addGroup(String name, boolean isActive) throws WrongDataException {
        log.info("execute addGroup method with name= " + name +
                "isActive= " + isActive);
        Group group = new Group(name, isActive);
        return daoGroup.add(group);
    }

    public Group updateGroupInfo(int groupIdToUpdate, String nameNew, boolean isActiveNew) throws NoSuchEntityException {
        log.info("execute updateGroupINfo with group id= " + groupIdToUpdate +
                                                " new name= " + nameNew +
                                                " isActive= " + isActiveNew);
        Group group = daoGroup.getById(groupIdToUpdate);
        if (group != null){
            group.setName(nameNew);
            group.setActive(isActiveNew);
            daoGroup.update(group);
            return group;
        }else{
            log.error("There is no group in db under Id=" + groupIdToUpdate);
            throw new NoSuchEntityException("There is no group in db under Id=" + groupIdToUpdate);
        }
    }

    public Subject addSubject(String name, String description, boolean isActive) throws WrongDataException {
        log.info("execute addSubject method with name= " + name +
                                                " description=" + description +
                                                " isActive=" + isActive);
        return daoSubject.add(new Subject(name, description, isActive));
    }

    public Subject getSubjectById (int subjectId) throws NoSuchEntityException {
        log.info("execute getSubjectById method with subject Id=" + subjectId);
        Subject subject = daoSubject.getById(subjectId);
        if (subject != null) {
            return subject;
        } else {
            log.error("There is no subject in db under Id=" + subjectId);
            throw new NoSuchEntityException("There is no subject in db under Id=" + subjectId);
        }
    }

    public Professor addProfessor(String name, int experience, int subject_id, boolean isActive) throws NoSuchEntityException {
        log.info("addProfessor method with name=" + name +
                                            " experience=" + experience +
                                            " subject id= " + subject_id +
                                            " isActive=" + isActive);
        Subject subject = daoSubject.getById(subject_id);
        if (subject != null){
            return daoProfessor.add(new Professor(name, experience, subject, isActive));
        } else {
            log.error("There is no subject in db under Id=" + subject_id);
            throw new NoSuchEntityException("There is no subject in db under Id=" + subject_id);
        }
    }

    public Professor getProfessorById(int professorId) throws NoSuchEntityException {
        log.info("execute getProfessorById method with professor id= " + professorId);
        Professor professor = daoProfessor.getById(professorId);
        if (professor != null){
            return professor;
        }
        log.error("There is no professor in db under Id=" + professorId);
        throw new NoSuchEntityException("There is no professor in db under Id=" + professorId);
    }

    public boolean updateProfessorInfo(int professorIdToUpdate, String nameNew, int experienceNew, int subject_idNew, boolean isActiveNew) throws WrongDataException, NoSuchEntityException {
        log.info("execute updateProfessorInfo method with professor id=" + professorIdToUpdate +
                                                        " new name=" + nameNew +
                                                        " new experience=" + experienceNew +
                                                        " new subject id=" + subject_idNew +
                                                        " isActive=" + isActiveNew);
        Professor professor = daoProfessor.getById(professorIdToUpdate);
        if (professor != null) {
            Subject subject = daoSubject.getById(subject_idNew);
            if (subject != null) {
                professor.setName(nameNew);
                professor.setExperience(experienceNew);
                professor.setSubject(subject);
                professor.setActive(isActiveNew);
                daoProfessor.update(professor);
                return true;
            } else {
                log.error("There is no subject in db under Id=" + subject_idNew);
                throw new NoSuchEntityException("There is no subject in db under Id=" + subject_idNew);
            }
        } else {
            log.error("There is no professor in db under Id=" + professorIdToUpdate);
            throw new NoSuchEntityException("There is no professor in db under Id=" + professorIdToUpdate);
        }
    }

    public boolean updateSubjectInfo(int subjectIdToUpdate, String nameNew, String descriptionNew, boolean isActiveNew) throws WrongDataException, NoSuchEntityException {
        log.error("execute updateSubjectInfo method with subject id=" + subjectIdToUpdate +
                                                " new name=" + nameNew +
                                                " new description=" + descriptionNew +
                                                " isActive=" + isActiveNew);
        Subject subject = daoSubject.getById(subjectIdToUpdate);
        if (subject != null) {
            subject.setName(nameNew);
            subject.setDescription(descriptionNew);
            subject.setActive(isActiveNew);
            daoSubject.update(subject);
            return true;
        } else {
            log.error("There is no subject in db under Id=" + subjectIdToUpdate);
            throw new NoSuchEntityException("There is no subject in db under Id=" + subjectIdToUpdate);
        }
    }
}
