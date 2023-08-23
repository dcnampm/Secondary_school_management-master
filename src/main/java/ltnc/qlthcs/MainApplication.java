package ltnc.qlthcs;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ltnc.qlthcs.controllers.HomepageController;
import ltnc.qlthcs.controllers.LoginController;

import java.io.IOException;

public class MainApplication extends Application {
    private double x = 0;
    private double y = 0;

//    private static Stage stg;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 520, 400);

        //Khi nhấn chuột vào cửa sổ -> lấy tọa độ x, y
        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
            //làm mờ cửa sổ
            stage.setOpacity(0.8);
        });

        root.setOnMouseReleased((MouseEvent event) -> {
            stage.setOpacity(1);
        });

        stage.setScene(scene);
        stage.show();

        System.out.println();
    }

    public static void main(String[] args) { launch();}
}