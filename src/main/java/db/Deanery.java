package db;

import model.Student;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Vlad on 06.10.2016.
 */
public class Deanery {
    private DBController dbController;

    public Deanery(DBController dbController) {
        this.dbController = dbController;
    }

    public void updateStudentInfo(int studentIdToUpdate, String nameNew, int group_idNew, boolean isActiveNew) throws SQLException {
        if (dbController.getStudentById(studentIdToUpdate) != null){
            dbController.updateStudent(studentIdToUpdate,nameNew,group_idNew,isActiveNew);
        }
    }

    public void updateGroupInfo(int groupIdToUpdate, String nameNew, boolean isActiveNew) throws SQLException {
        if (dbController.getGroupById(groupIdToUpdate) != null){
            dbController.updateGroup(groupIdToUpdate,nameNew,isActiveNew);
        }
    }

    public void updateProfessorInfo(int professorIdToUpdate, String nameNew, int experienceNew, int subject_idNew, boolean isActiveNew) throws SQLException {
        if (dbController.getProfessorById(professorIdToUpdate) != null){
            dbController.updateProfessor(professorIdToUpdate,nameNew,experienceNew,subject_idNew,isActiveNew);
        }
    }

    public void updateSubjectInfo(int subjectIdToUpdate, String nameNew, String descriptionNew, boolean isActiveNew) throws SQLException {
        if (dbController.getSubjectById(subjectIdToUpdate) != null){
            dbController.updateSubject(subjectIdToUpdate,nameNew,descriptionNew,isActiveNew);
        }
    }

    public List<Student> getStudentsByGroupId(int groupId){
        return dbController.getAllStudentByGroupId(groupId);
    }
}
