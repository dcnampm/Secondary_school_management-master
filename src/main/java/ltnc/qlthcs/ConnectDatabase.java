package ltnc.qlthcs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDatabase {
    public static String url = "jdbc:mysql://localhost:3306/qlhsc2";
    public static String username = "root";
    public static String password = "nam.pd193034@";

    public static final String QUERY_STUDENT_DATA_HOMEPAGE =
            "SELECT u.userID, u.userName, u.gender, u.DoB, u.email, u.phoneNumber, u.address, u.role, c.session\n" +
                    "FROM user u\n" +
                    "JOIN student s ON u.userID = s.userID\n" +
                    "JOIN study ON s.studentID = study.studentID\n" +
                    "JOIN class c ON study.classID = c.classID\n" +
                    "WHERE u.userID = ?";

    public static final String QUERY_TEACHER_DATA_HOMEPAGE =
            "SELECT u.userID, u.userName, u.gender, u.DoB, u.email, u.phoneNumber, u.address, u.role, t.cccd, t.startDate\n" +
                    "FROM user u\n" +
                    "JOIN teacher t ON u.userID = t.userID\n" +
                    "WHERE u.userID = ?";

    public static final String QUERY_ADMIN_DATA_HOMEPAGE =
            "SELECT u.userID, u.userName, u.gender, u.DoB, u.email, u.phoneNumber, u.address, u.role\n" +
                    "FROM user u\n" +
                    "WHERE u.userID = ?";
    private static Connection conn;
    public static Connection getConnection() {
        try {
            //Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //Connect to db
            conn = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
}

