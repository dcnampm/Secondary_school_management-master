package ltnc.qlthcs.models;

import java.sql.Date;

public class Teacher extends User{
    private static String teacherID;
    private static Date startDate;
    private static String cccd;
    private static String subjectName;
    private static String classCN;
    private static String className;

    public Teacher() {
    }

    public Teacher(String userID, String userName, Date Dob, String gender, String address, String phoneNumber, String subjectName, String className, String classCN, Date startDate) {
        super(userID, userName, Dob, gender, address, phoneNumber);
        this.subjectName = subjectName;
        this.className = className;
        this.classCN = classCN;
        this.startDate = startDate;
    }

    public static String getTeacherID() {
        return teacherID;
    }

    public static void setTeacherID(String teacherID) {
        Teacher.teacherID = teacherID;
    }

    public static Date getStartDate() {
        return startDate;
    }

    public static void setStartDate(Date startDate) {
        Teacher.startDate = startDate;
    }

    public static String getCccd() {
        return cccd;
    }

    public static void setCccd(String cccd) {
        Teacher.cccd = cccd;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", teacherID=" + teacherID + '\'' +
                ", startDate='" + startDate + '\'' +
                ", cccd='" + cccd + '\'';
    }
}
