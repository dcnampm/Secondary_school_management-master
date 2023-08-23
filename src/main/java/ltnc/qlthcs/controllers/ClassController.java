package ltnc.qlthcs.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ltnc.qlthcs.ConnectDatabase;
import ltnc.qlthcs.models.Class;
import ltnc.qlthcs.models.classData;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClassController extends BaseController{

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnApply;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;
    @FXML
    private Button btnEditApply;
    @FXML
    private Button btnEditCancel;

    @FXML
    private Button btnEditClear;

    @FXML
    private Button btnSearch;

    @FXML
    private ComboBox<String> classBox;

    @FXML
    private ComboBox<String> gradeBox;

    @FXML
    private TextField classGVCNField;

    @FXML
    private TextField classIDField;

    @FXML
    private TextField classNameField;

    @FXML
    private TextField classSizeField;

    @FXML
    private TextField classYearField;

    @FXML
    private TextField editClassGVCNField;

    @FXML
    private TextField editClassIDField;

    @FXML
    private TextField editClassNameField;

    @FXML
    private TextField editClassSizeField;

    @FXML
    private TextField editClassYearField;

    @FXML
    private DialogPane formAdd;

    @FXML
    private DialogPane formEdit;

    @FXML
    private TableView<classData> tblClass;

    @FXML
    private TableColumn<classData, Integer> tblClassID;

    @FXML
    private TableColumn<classData, String> tblClassName;

    @FXML
    private TableColumn<classData, Integer> tblClassSize;

    @FXML
    private TableColumn<classData, String> tblClassYear;


    @FXML
    private TableColumn<classData, String> tblClassGVCN;



    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    // =================================================================================
    public void addClass(ActionEvent event) {
        formAdd.setVisible(false);
        if (event.getSource() == btnAdd) {
            formAdd.setVisible(true);
        }
    }

    public void cancelBtn(ActionEvent event) {
        formAdd.setVisible(false);
        formEdit.setVisible(false);
        addClassReset();
    }

    public void editClass(ActionEvent event) {
        formEdit.setVisible(false);
        if (event.getSource() == btnEdit) {
            formEdit.setVisible(true);
        }
    }

    public void addClassReset() {
        editClassGVCNField.setText("");
        editClassIDField.setText("");
        editClassNameField.setText("");
        editClassSizeField.setText("");
        editClassYearField.setText("");

        classYearField.setText("");
        classGVCNField.setText("");
        classIDField.setText("");
        classNameField.setText("");
        classSizeField.setText("");
    }
    // =================================================================================
    @FXML
    private void addClassFunc(ActionEvent event) {

        connect = ConnectDatabase.getConnection();

        try {
            Alert alert;
            if (classIDField.getText().isEmpty() || classNameField.getText().isEmpty() || classSizeField.getText().isEmpty() || classYearField.getText().isEmpty() || classGVCNField.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                String check = "SELECT id FROM class WHERE id = '" + classIDField.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(check);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("classID: " + classIDField.getText() + " was already exist!");
                    alert.showAndWait();
                } else {
                    String teacherID = classGVCNField.getText();
                    int classID = Integer.parseInt(classIDField.getText());
                    String className = classNameField.getText();
                    String session = classYearField.getText();
                    int size = Integer.parseInt(classSizeField.getText());

                    classData newSubject = new classData(classID, className, size, session, teacherID);

                    tblClass.getItems().add(newSubject);

                    tblClassID.setCellValueFactory(new PropertyValueFactory<>("classID"));
                    tblClassName.setCellValueFactory(new PropertyValueFactory<>("className"));
                    tblClassSize.setCellValueFactory(new PropertyValueFactory<>("size"));
                    tblClassYear.setCellValueFactory(new PropertyValueFactory<>("session"));
                    tblClassGVCN.setCellValueFactory(new PropertyValueFactory<>("teacherID"));

                    String insertInfo = "INSERT INTO class" + "(classID,className,size,session,teacherID)" + "VALUES(?,?,?,?,?)";

                    prepare = connect.prepareStatement(insertInfo);
                    prepare.setString(1, classIDField.getText());
                    prepare.setString(2, classNameField.getText());
                    prepare.setString(3, classSizeField.getText());
                    prepare.setString(4, classYearField.getText());
                    prepare.setString(5, classGVCNField.getText());
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                    addClassReset();
                    formAdd.setVisible(false);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // =================================================================================
    @FXML
    private void editClassFunc(ActionEvent event) {

        connect = ConnectDatabase.getConnection();

        String sql = "UPDATE class SET className = ?, size = ?, session = ?, teacherID = ? WHERE classID = ?";

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, editClassNameField.getText());
            prepare.setString(2, editClassSizeField.getText());
            prepare.setString(3, editClassYearField.getText());
            prepare.setString(4, editClassGVCNField.getText());
            prepare.setInt(5, Integer.parseInt(editClassIDField.getText()));

            prepare.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Updated!");
            alert.showAndWait();

            showData();
            formEdit.setVisible(false);
        } catch (SQLException e) {
        }

    }
    // =================================================================================
    public void delete() {

        classData data = tblClass.getSelectionModel().getSelectedItem();

        if (data == null) {
            return;
        }

        connect = ConnectDatabase.getConnection();
        String sql = "DELETE FROM class WHERE classID = ?";

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() != ButtonType.OK)
                return;
            else {
                prepare = connect.prepareStatement(sql);
                prepare.setInt(1, data.getClassID());
                prepare.executeUpdate();

                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Information");
                alert2.setHeaderText(null);
                alert2.setContentText("Successfully Deleted!");
                alert2.showAndWait();

                showData();
            }
        } catch (SQLException e) {
        }

    }
    // =================================================================================
    public void selectData() {

        classData data = tblClass.getSelectionModel().getSelectedItem();

        if (data == null) {
            return;
        }

        connect = ConnectDatabase.getConnection();
        String sql = "SELECT classID, className, size, session, teacherID FROM class WHERE classID = ?";

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, data.getClassID());
            result = prepare.executeQuery();

            if (result.next()) {
                editClassIDField.setText(String.valueOf(result.getInt("classID")));
                editClassNameField.setText(result.getString("className"));
                editClassSizeField.setText(String.valueOf(result.getInt("size")));
                editClassYearField.setText(result.getString("session"));
                editClassGVCNField.setText(result.getString("teacherID"));
            }
        } catch (SQLException e) {
        }
    }
    // =================================================================================
    public ObservableList<classData> listData() {
        ObservableList<classData> dataList = FXCollections.observableArrayList();
        Connection connect = ConnectDatabase.getConnection();

        String sql = "SELECT * FROM class";

        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            while (result.next()) {
                classData data;
                data = new classData(result.getInt("classID"), result.getString("className"), result.getInt("size"), result.getString("session"), result.getString("teacherID"));
                dataList.add(data);
                tblClassID.setCellValueFactory(new PropertyValueFactory<>("classID"));
                tblClassName.setCellValueFactory(new PropertyValueFactory<>("className"));
                tblClassSize.setCellValueFactory(new PropertyValueFactory<>("size"));
                tblClassYear.setCellValueFactory(new PropertyValueFactory<>("session"));
                tblClassGVCN.setCellValueFactory(new PropertyValueFactory<>("teacherID"));

                tblClass.getItems().add(data);
            }
        } catch (Exception e) {
        }
        return dataList;
    }

    public void showData() {
        ObservableList<classData> show = listData();
        tblClass.setItems(show);
    }
    // =================================================================================
    private String[] gradeList = {"null","6", "7", "8", "9"};

    public void selectGradeList() {
        List<String> listG = new ArrayList<>();
        for (String data : gradeList) {
            listG.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listG);
        gradeBox.setItems(listData);
        gradeBox.setOnAction(event -> {
            String selectedGrade = gradeBox.getValue();
            if (selectedGrade == null || selectedGrade.equals("null")) {
                selectClassList();
            } else {
                updateClassList(selectedGrade);
            }
        });
    }


    private void updateClassList(String selectedGrade) {
        connect = ConnectDatabase.getConnection();
        ObservableList<String> classList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT className FROM class";
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            while (result.next()) {
                classList.add(result.getString("className"));
            }
        } catch (SQLException e) {}

        classBox.setItems(classList);
        List<String> filteredClassList = new ArrayList<>();
        for (String data : classList) {
            if (data.startsWith(selectedGrade)) {
                filteredClassList.add(data);
            }
        }
        ObservableList<String> listData = FXCollections.observableArrayList(filteredClassList);
        classBox.setItems(listData);
    }

    public void selectClassList() {
        connect = ConnectDatabase.getConnection();
        ObservableList<String> classList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT className FROM class";
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            while (result.next()) {
                classList.add(result.getString("className"));
            }
        } catch (SQLException e) {}
        List<String> listC = new ArrayList<>();
        for (String data : classList) {
            listC.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listC);
        classBox.setItems(listData);
    }
    @FXML
    private void Search(ActionEvent event) {
        String selectedClass = classBox.getValue();
        String selectedGrade = gradeBox.getValue();

        if (selectedGrade == null || selectedGrade.equals("null")) {
            showData();
            return;
        }

        connect = ConnectDatabase.getConnection();
        String sql = "SELECT * FROM class WHERE className = ?";

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, selectedClass);
            result = prepare.executeQuery();

            ObservableList<classData> searchDataList = FXCollections.observableArrayList();
            while (result.next()) {
                classData data = new classData(result.getInt("classID"), result.getString("className"), result.getInt("size"), result.getString("session"), result.getString("teacherID"));
                searchDataList.add(data);
            }

            tblClass.setItems(searchDataList);
        } catch (SQLException e) {}
    }

// =================================================================================

    public void initialize(URL location, ResourceBundle resources) {
        selectGradeList();
        selectClassList();

        showData();
    }
}
