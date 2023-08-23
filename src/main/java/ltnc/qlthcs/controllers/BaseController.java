package ltnc.qlthcs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ltnc.qlthcs.ConnectDatabase;
import ltnc.qlthcs.MainApplication;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseController {
    @FXML
    private Button closeButton;
    @FXML
    private Button homepageButton;
    @FXML
    private MenuButton logOutMenuButton;
    @FXML
    private MenuItem logOutMenuItem;
    @FXML
    private Button classButton;
    @FXML
    private Button studentButton;
    @FXML
    private Button teacherButton;
    @FXML
    private Button scoreButton;
    @FXML
    private Button subjectButton;
    @FXML
    private Button statisticsButton;
    private String loggedInUserRole;

    public void setLoggedInUserRole(String loggedInUserRole) {
        this.loggedInUserRole = loggedInUserRole;
    }

//    public BaseController() {}

    // Đóng giao diện
    public void closeButtonOnAction (ActionEvent e) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    //xử lí đăng xuất
    @FXML
    public void logOutMenuItemOnAction(ActionEvent event) throws IOException {
        //Xác nhận đăng xuất
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận đăng xuất");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc mình muốn đăng xuất?");

        // Thiết lập các nút trong hộp thoại
        ButtonType buttonTypeYes = new ButtonType("Đồng ý");
        ButtonType buttonTypeCancel = new ButtonType("Hủy");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);

        // Hiển thị hộp thoại và chờ người dùng phản hồi
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == buttonTypeYes) {
                // Thực hiện đăng xuất ở đây
                // Điều hướng về giao diện login
                try {
                    // Tạo một FXMLLoader mới cho trang login.fxml
                    FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login.fxml"));
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root, 520, 400);
                    Stage stage = (Stage) logOutMenuButton.getScene().getWindow();
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //xử lí giao diện theo role
    protected void setButtonVisibility(String loggedInUserRole) {
        if ("admin".equals(loggedInUserRole)) {
            classButton.setDisable(false);
            studentButton.setDisable(false);
            teacherButton.setDisable(false);
            scoreButton.setDisable(false);
            subjectButton.setDisable(false);
            statisticsButton.setDisable(false);
        } else if ("học sinh".equals(loggedInUserRole) || "giáo viên".equals(loggedInUserRole)) {
            classButton.setDisable(false);
            studentButton.setDisable(true);
            teacherButton.setDisable(true);
            scoreButton.setDisable(false);
            subjectButton.setDisable(true);
            statisticsButton.setDisable(true);
        }
    }

    //chuyển giao diện trang chủ
    public void homepageButtonOnAction(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("homepage.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1050, 800);

        Stage stage = (Stage) homepageButton.getScene().getWindow();
        stage.setScene(scene);
    }

    //chuyển giao diện quản lý lớp
    public void classButtonOnAction(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("class.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1050, 800);

        Stage stage = (Stage) classButton.getScene().getWindow();
        stage.setScene(scene);
    }

    //chuyển giao diện quản lý học sinh
    public void studentButtonOnAction(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("student.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1050, 800);

        Stage stage = (Stage) studentButton.getScene().getWindow();
        stage.show();
        stage.setScene(scene);
    }

    //chuyển giao diện quản lý giáo viên
    public void teacherButtonOnAction(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("teacher.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1050, 800);

        Stage stage = (Stage) teacherButton.getScene().getWindow();
        stage.setScene(scene);
    }

    //chuyển giao diện quản lý điểm
    public void scoreButtonOnAction(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("score.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1050, 800);

        Stage stage = (Stage) scoreButton.getScene().getWindow();
        stage.setScene(scene);
    }

//    chuyển giao diện quản lý môn học
    public void subjectButtonOnAction(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("subject.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1050, 800);

        Stage stage = (Stage) subjectButton.getScene().getWindow();
        stage.setScene(scene);
    }

    //chuyển giao diện thống kê
    public void statisticsButtonOnAction(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("statistics.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1050, 700);

        Stage stage = (Stage) statisticsButton.getScene().getWindow();
        stage.setScene(scene);
    }

}
