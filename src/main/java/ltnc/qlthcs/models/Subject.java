package ltnc.qlthcs.models;

public class Subject {
    private static int subjectID;
    private static String subjectName;
    private static String description;

    public Subject() {}

    public Subject(int subjectID, String subjectName, String description) {
        this.subjectID = subjectID;
        this.subjectName = subjectName;
        this.description = description;
    }

    public static int getSubjectID() {
        return subjectID;
    }

    public static void setSubjectID(int subjectID) {
        Subject.subjectID = subjectID;
    }

    public static String getSubjectName() {
        return subjectName;
    }

    public static void setSubjectName(String subjectName) {
        Subject.subjectName = subjectName;
    }

    public static String getDescription() {
        return description;
    }

    public static void setDescription(String description) {
        Subject.description = description;
    }
}
