package ltnc.qlthcs.controllers;

import ltnc.qlthcs.ConnectDatabase;
import ltnc.qlthcs.MainApplication;
import ltnc.qlthcs.models.Student;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class StudentController extends BaseController{
    @FXML
    private ChoiceBox<String> choiceBoxKhoi;
    @FXML
    private ChoiceBox<String> choiceBoxLop;
    @FXML
    private TextField txtSearch;
    private String[] khoi = {"6", "7", "8", "9"};
    @FXML
    private TableView<Student> table;
    @FXML
    private TableColumn<Student, Integer> indexCol;
    @FXML
    private TableColumn<Student, String> studentIDCol;
    @FXML
    private TableColumn<Student, String> studentNameCol;
    @FXML
    private TableColumn<Student, Date> DoBCol;
    @FXML
    private TableColumn<Student, String> genderCol;
    @FXML
    private TableColumn<Student, String> addressCol;
    @FXML
    private TableColumn<Student, String> sdtCol;
    @FXML
    private TableColumn<Student, String> classNameCol;
    @FXML
    private Button btnAdd;
    @FXML
    private Button deleteIcon;
    @FXML
    private Button editIcon;


    String query = null;
    Connection connection;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Student student = null ;
    ObservableList<Student> StudentList = FXCollections.observableArrayList();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDate();
        // Lắng nghe sự kiện click trên dòng của TableView
        table.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 1) {
                student = table.getSelectionModel().getSelectedItem();
                if (student != null) {
                    deleteIcon.setDisable(false); // Kích hoạt nút xóa
                    editIcon.setDisable(false);
                } else {
                    deleteIcon.setDisable(true); // Vô hiệu hóa nút xóa
                    editIcon.setDisable(false);
                }
            }
        });
    }
    @FXML
    private void setupIndexColumn() {
        indexCol.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(table.getItems().indexOf(column.getValue()) + 1));
        indexCol.setSortable(false);
        indexCol.setPrefWidth(50);
    }
    //Hàm setup choiboox lớp
    private void updateLopOptions(String selectedKhoi) {
//        String[] lopOptions;
        choiceBoxLop.getItems().clear();
        if ("6".equals(selectedKhoi)) {
            choiceBoxLop.getItems().addAll("6A", "6B");
        } else if ("7".equals(selectedKhoi)) {
                choiceBoxLop.getItems().addAll("7A", "7A");
        } else if ("8".equals(selectedKhoi)) {
                choiceBoxLop.getItems().addAll("8A", "8A");
        } else if ("9".equals(selectedKhoi)) {
                choiceBoxLop.getItems().addAll("9A", "9A");
        }
    }

    @FXML
    private void handleChoiBoxLop(){
        choiceBoxLop.setOnAction(event -> {
            String selectedLop = choiceBoxLop.getValue();
            if (selectedLop != null) {
                try {
                    getStudentInClass(selectedLop);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    private void searchFilter() {
        FilteredList<Student> filteredList = new FilteredList<>(StudentList, e->true);
        txtSearch.setOnKeyReleased(e->{
            txtSearch.textProperty().addListener((observableValue, oldValue, newValue)->{
                filteredList.setPredicate((Predicate<? super Student>) cust ->{
                    if(newValue == null||newValue.isEmpty()){
                        return true;
                    }
                    String toLowerCaseFilter = newValue.toLowerCase();
                    if (cust.getStudentID().contains(toLowerCaseFilter)) {
                        return true;
                    } else if (cust.getUserName().toLowerCase().contains(toLowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Student> students = new SortedList<>(filteredList);
            students.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(students);
        });
    }

    private void getStudentInClass(String selectedLop) throws SQLException{
        StudentList.clear();
        Connection conn = ConnectDatabase.getConnection();
        String query = "SELECT * " +
                "FROM student " +
                "JOIN user ON user.userID = student.userID "+
                "JOIN study ON study.studentID = student.studentID "+
                "JOIN class ON class.classID = study.classID "+
                "WHERE class.className = '"+ selectedLop +"'";
        PreparedStatement pst = conn.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {

            String studentID = rs.getString("studentID");
            String userID = rs.getString("userID");
            String userName = rs.getString("userName");
            Date DoB = rs.getDate("DoB");
            String gender = rs.getString("gender");
            String email = rs.getString("email");
            String phoneNumber = rs.getString("phoneNumber");
            String address = rs.getString("address");
            String role = rs.getString("role");
            String account = rs.getString("account");
            String password = rs.getString("password");
            String parentName = rs.getString("parentName");
            String parentPhone = rs.getString("parentPhone");
            String className = rs.getString("className");
            StudentList.add(new Student(studentID, userID, userName, gender, DoB,
                    email, phoneNumber, address, role, account, password, parentName, parentPhone, className));
        }
        table.setItems(StudentList);
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
    @FXML
    private void refreshTable() {
        try {
            StudentList.clear();

//            query = "SELECT * FROM `student`";
            query = "SELECT * " +
                    "FROM student " +
                    "JOIN user ON user.userID = student.userID "+
                    "JOIN study ON study.studentID = student.studentID "+
                    "JOIN class ON class.classID = study.classID";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String studentID = resultSet.getString("studentID");
                String userID = resultSet.getString("userID");
                String userName = resultSet.getString("userName");
                Date DoB = resultSet.getDate("DoB");
                String gender = resultSet.getString("gender");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phoneNumber");
                String address = resultSet.getString("address");
                String role = resultSet.getString("role");
                String account = resultSet.getString("account");
                String password = resultSet.getString("password");
                String parentName = resultSet.getString("parentName");
                String parentPhone = resultSet.getString("parentPhone");
//                Integer classID = resultSet.getInt("classID");
                String className = resultSet.getString("className");
                StudentList.add(new Student(studentID, userID, userName, gender, DoB,
                        email, phoneNumber, address, role, account, password, parentName, parentPhone,className));
                table.setItems(StudentList);
                deleteIcon.setDisable(true);
                editIcon.setDisable(false);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void loadDate() {
        connection = ConnectDatabase.getConnection();
        choiceBoxKhoi.getItems().addAll(khoi);
        choiceBoxKhoi.setOnAction(event -> {
            String selectedKhoi = choiceBoxKhoi.getValue();
            updateLopOptions(selectedKhoi);
        });
        setupIndexColumn();
        refreshTable();
        handleChoiBoxLop();
        searchFilter();
        studentIDCol.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        DoBCol.setCellValueFactory(new PropertyValueFactory<>("DoB"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        sdtCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        classNameCol.setCellValueFactory(new PropertyValueFactory<>("className"));
        //add cell of button edit
        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
            try {
                String deleteScoreQuery = "DELETE FROM score WHERE studentID = ?";
                preparedStatement = connection.prepareStatement(deleteScoreQuery);
                preparedStatement.setString(1, student.getStudentID());
                preparedStatement.executeUpdate();

                // Xóa học sinh trong bảng study
                String deleteStudyQuery = "DELETE FROM study WHERE studentID = ?";
                preparedStatement = connection.prepareStatement(deleteStudyQuery);
                preparedStatement.setString(1, student.getStudentID());
                preparedStatement.executeUpdate();

                // Xóa học sinh trong bảng student
                String deleteStudentQuery = "DELETE FROM student WHERE studentID = ?";
                preparedStatement = connection.prepareStatement(deleteStudentQuery);
                preparedStatement.setString(1, student.getStudentID());
                preparedStatement.executeUpdate();

                // Xóa học sinh trong bảng user
                String deleteUserQuery = "DELETE FROM user WHERE userID = ?";
                preparedStatement = connection.prepareStatement(deleteUserQuery);
                preparedStatement.setString(1, student.getUserID());
                preparedStatement.executeUpdate();
                refreshTable();

            } catch (SQLException e) {
            }
        });

        editIcon.setOnMouseClicked((MouseEvent event) -> {
            student = table.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("addStudent.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
            }
            addStudentController addStudentController = loader.getController();
            addStudentController.setUpdate(true);
            addStudentController.setTextField(student.getUserID(), student.getStudentID(), student.getUserName(),student.getGender(),
                    student.getDoB(), student.getEmail(), student.getAddress(), student.getPhoneNumber(),
                    student.getParentName(), student.getParentPhone(), student.getAccount(), student.getPassword(), student.getClassName());
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        });
        table.setItems(StudentList);


    }
}
