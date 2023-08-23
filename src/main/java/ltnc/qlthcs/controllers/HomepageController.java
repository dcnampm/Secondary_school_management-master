package ltnc.qlthcs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import ltnc.qlthcs.ConnectDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomepageController extends BaseController{
//    @FXML
//    private Button closeButton;
    @FXML
    private Label idLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label DoBLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label phoneNumberLabel;
    @FXML
    private Label sessionLabel;
    @FXML
    private Label cccdLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private Label startDateLabel;
    private String loggedInUserID;
    private String loggedInUserRole;
    @FXML
    private MenuButton logOutMenuButton;

    //initialize() được gọi tự động khi FXML được nạp và giao diện được khởi tạo
    public void initialize() {
        // Phương thức initialize sẽ được gọi khi homepage.fxml được nạp và hiển thị

        //
        loggedInUserRole = LoginController.role;
        setButtonVisibility(loggedInUserRole);
        // Gọi phương thức getUserData() từ phương thức initialize()
        getUserData();
    }

    //lấy mã người dùng
    public void setLoggedInUserID(String loggedInUserID) {
        this.loggedInUserID = loggedInUserID;
        idLabel.setText("Mã người dùng: " + loggedInUserID);
    }

    public void setLoggedInUserRole(String loggedInUserRole) {
        this.loggedInUserRole = loggedInUserRole;
    }

    //Kết nối db, lấy thông tin user vừa đăng nhập
    public void getUserData() {
        //Tạo kết nối đến db
        ConnectDatabase connectNow = new ConnectDatabase();
        Connection conn = connectNow.getConnection();
        
//        String getUserData = "SELECT * FROM user WHERE userID = ?";
        try {
            PreparedStatement preparedStmt;

            if ("admin".equals(loggedInUserRole)) {
                preparedStmt = conn.prepareStatement(ConnectDatabase.QUERY_ADMIN_DATA_HOMEPAGE);
            } else if ("giáo viên".equals(loggedInUserRole)) {
                preparedStmt = conn.prepareStatement(ConnectDatabase.QUERY_TEACHER_DATA_HOMEPAGE);
            } else
            {
                preparedStmt = conn.prepareStatement(ConnectDatabase.QUERY_STUDENT_DATA_HOMEPAGE);
            }

            preparedStmt.setString(1, loggedInUserID);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString(2);
                String gender = resultSet.getString(3);
                String dob = resultSet.getString(4);
                String email = resultSet.getString(5);
                String phoneNumber = resultSet.getString(6);
                String address = resultSet.getString(7);
                String role = resultSet.getString(8);

                // Gán giá trị vào các label
                usernameLabel.setText("Họ và tên: " + username);
                genderLabel.setText("Giới tính: " + gender);
                DoBLabel.setText("Ngày tháng năm sinh: " + dob);
                emailLabel.setText("Email: " + email);
                phoneNumberLabel.setText("Số điện thoại: " + phoneNumber);
                addressLabel.setText("Địa chỉ: " + address);
                roleLabel.setText("Chức vụ: " + role);

                logOutMenuButton.setText(username);
                // Kiểm tra và gán dữ liệu riêng tùy theo vai trò
                if ("học sinh".equals(loggedInUserRole)) {
                    String session = resultSet.getString(9);
                    sessionLabel.setText("Khóa: " + session);
                } else if ("giáo viên".equals(loggedInUserRole)) {
                    String cccd = resultSet.getString(9);
                    String startDate = resultSet.getString(10);
                    // Gán giá trị vào các label tương ứng
                    cccdLabel.setText("CCCD: " + cccd);
                    startDateLabel.setText("Ngày bắt đầu công tác: " + startDate);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

