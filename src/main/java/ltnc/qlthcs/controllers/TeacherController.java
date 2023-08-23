package ltnc.qlthcs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ltnc.qlthcs.MainApplication;
import java.io.IOException;



public class TeacherController extends BaseController {
    @FXML
    private Button addButton;

    @FXML
    private void getAddTeacher(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("teacherForm.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }
    @FXML
    private void getAddStudent(ActionEvent event){
        try{
            Parent parent = FXMLLoader.load(MainApplication.class.getResource("addStudent.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        }catch (IOException e){
            System.out.println("Lỗi chuyển cảnh ");
            e.printStackTrace();
        }
    }
}

