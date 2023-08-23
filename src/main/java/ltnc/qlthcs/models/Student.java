package ltnc.qlthcs.models;

import java.util.Date;

public class Student extends User{
    private static String studentID;
    private static String parentName;
    private static String parentPhone;
    private String className;

    public Student(String studentID, String userID, String userName, String gender, java.util.Date DoB, String email,
                   String phoneNumber, String address, String role, String account, String password,
                   String parentName, String parentPhone, String className) {
        super(userID, userName, gender, DoB, email, phoneNumber, address, role, account, password );
        this.studentID = studentID;
        this.parentName = parentName;
        this.parentPhone = parentPhone;
        this.className = className;
    }

    public Student(){};
    public Student(String studentID, String userID, String userName, String gender, Date DoB) {
        super(userID,userName,gender, DoB);
        this.studentID = studentID;
    }
    public static String getStudentID() {
        return studentID;
    }

    public static void setStudentID(String studentID) {
        Student.studentID = studentID;
    }

    public static String getParentName() {
        return parentName;
    }

    public static void setParentName(String parentName) {
        Student.parentName = parentName;
    }

    public static String getParentPhone() {
        return parentPhone;
    }

    public static void setParentPhone(String parentPhone) {
        Student.parentPhone = parentPhone;
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", studentID=" + studentID + '\'' +
                ", parentName='" + parentName + '\'' +
                ", parentPhone='" + parentPhone + '\'';
    }
}
