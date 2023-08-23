package ltnc.qlthcs.models;

public class Class {
    private Integer classID;
    private String className;
    private Integer size;
    private String session;
    private String teacherID;


    public Class(Integer classID, String className, Integer size, String session, String teacherID) {
        this.classID = classID;
        this.className = className;
        this.size = size;
        this.session = session;
        this.teacherID = teacherID;

    }

    public Integer getClassID() {
        return classID;
    }

    public String getClassName() {
        return className;
    }

    public Integer getSize() {
        return size;
    }

    public String getSession() {
        return session;
    }

    public String getTeacherID() {
        return teacherID;
    }


}
