package ltnc.qlthcs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ltnc.qlthcs.ConnectDatabase;
import ltnc.qlthcs.MainApplication;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.sql.*;

public class LoginController extends BaseController{
    @FXML
    private Button closeButton;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private RadioButton studentRadioButton;
    @FXML
    private RadioButton teacherRadioButton;
    @FXML
    private RadioButton adminRadioButton;
    @FXML
    private ToggleGroup roleToggleGroup;
    private String loggedInUserID;
    public static String role;

    //đóng giao diện
    public void closeButtonOnAction (ActionEvent e) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    //action cho Đăng nhập
    public void loginButtonOnAction (ActionEvent e) {
        if (usernameTextField.getText().isBlank() || passwordPasswordField.getText().isBlank()) {
            loginMessageLabel.setText("Vui lòng nhập tài khoản và mật khẩu");
        } else {
            validateLogin();
        }
    }

    //Xác thực tk và mk từ db
    public void validateLogin() {
        //tạo kết nối db
        ConnectDatabase connectNow = new ConnectDatabase();
        Connection conn = connectNow.getConnection();

        // Kiểm tra lựa chọn của RadioButton để xác định vai trò
        String selectedRole = "";
        if (studentRadioButton.isSelected()) {
            selectedRole = "học sinh";
        } else if (teacherRadioButton.isSelected()) {
            selectedRole = "giáo viên";
        } else if (adminRadioButton.isSelected()) {
            selectedRole = "admin";
        }

        String verifyLogin = "SELECT COUNT(*), role FROM user WHERE account = ? AND password = ? GROUP BY role";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(verifyLogin);
            //tham số 1 truyển vào text gõ trong TextField tài khoản
            //tham số 2 truyền vào text gõ trong TextField password
            preparedStmt.setString(1, usernameTextField.getText());
            preparedStmt.setString(2, passwordPasswordField.getText());

            ResultSet resultSet = preparedStmt.executeQuery();
            boolean matchedRole = false;
            while (resultSet.next()) {
                matchedRole = true;
                int count = resultSet.getInt(1);
                role = resultSet.getString(2);

                if (count == 1) {
                    loggedInUserID = usernameTextField.getText();
                    if (selectedRole.equals(role)) {
                        setLoggedInUserRole(role);
                        changeToHomepage();
                    } else {
                        loginMessageLabel.setText("Vai trò đăng nhập không đúng!");
                    }
                }
            }
            if (!matchedRole) {
                loginMessageLabel.setText("Tài khoản hoặc mật khẩu không chính xác!");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    //Chuyển giao diện tới trang chủ
    public void changeToHomepage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("homepage.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1050, 800);

        HomepageController homepageController = fxmlLoader.getController();
        homepageController.setLoggedInUserID(loggedInUserID); // Truyền thông tin người dùng vào HomepageController
        homepageController.setLoggedInUserRole(role);
        //gọi initialize() để lấy tt người dùng vừa đăng nhập
        homepageController.initialize();

        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(scene);
    }

}