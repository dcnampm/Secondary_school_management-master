package ltnc.qlthcs.controllers;

import ltnc.qlthcs.ConnectDatabase;
import ltnc.qlthcs.models.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class addStudentController implements Initializable {
    @FXML
    private TextField tfUserID;
    @FXML
    private TextField tfStudentID;
    @FXML
    private TextField tfUserName;

    @FXML
    private DatePicker tfDoB;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfPhoneNumber;
    @FXML
    private TextField tfParentName;
    @FXML
    private TextField tfParentPhone;
    @FXML
    private TextField tfAccount;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfRole;
    @FXML
    private TextField tfGender;
    @FXML
    private TextField tfClassName;
    @FXML
    private ChoiceBox cbRole;
    @FXML
    private ChoiceBox<String> cbGender;
    private String[] gender = {"Nam"," Nữ"};
//    private String[] role = {"Học sinh", "Giáo viên", "Admin"};
    @FXML
    private ChoiceBox<String> cbClass;

    private String[] classcb = {"6A","6B","7A","7B","8A","8B","9A","9B"};

    @FXML
    private Button btnSave;
    String query = null;
    String query1 = null;
    String query2 = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Student student = null;
    private boolean update;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        cbRole.getItems().addAll(role);
//        cbRole.setOnAction(event ->{
//            String selectedRole = (String) cbRole.getValue();
//        });
//        cbGender.getItems().addAll(gender);
//        cbGender.setOnAction(event ->{
//            String selectedRole = (String) cbGender.getValue();
//        });
//        cbClass.getItems().addAll(classcb);
//        cbClass.setOnAction(event ->{
//            String selectedClass = (String) cbClass.getValue();
//        });


    }
    @FXML
    private void save(MouseEvent event){
        String userID = tfUserID.getText();
        String studentID = tfStudentID.getText();
        String userName = tfUserName.getText();
//        String gender = cbGender.getValue().toString() ;
        String gender = tfGender.getText();
        LocalDate dob = tfDoB.getValue();

        Date DoB = java.sql.Date.valueOf(dob);
        String email = tfEmail.getText();
        String phoneNumber = tfPhoneNumber.getText();
        String address = tfAddress.getText();
//        String role = cbRole.getValue().toString() ;
        String account = tfAccount.getText();
        String password = tfPassword.getText();
        String parentName = tfParentName.getText();
        String parentPhone = tfParentPhone.getText();
        String className = tfClassName.getText();
        if(userID.isEmpty()||studentID.isEmpty()||userName.isEmpty()||gender.isEmpty()||
                address.isEmpty()||address.isEmpty()||account.isEmpty()||DoB ==null||
                password.isEmpty()||parentName.isEmpty()||parentPhone.isEmpty()||className.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill all data");
            alert.showAndWait();
        }else{
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("Xác nhận");
                alert2.setHeaderText(null);
                alert2.setContentText("Bạn có chắc chắn muốn thực hiện không?");
                ButtonType result = alert2.showAndWait().orElse(ButtonType.CANCEL);
                if (result == ButtonType.OK) {
                    try{
                        getQuery();
                        insert();
                        // Hiển thị alert thông báo thành công
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Thành công");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Insert Data đã được thực hiện thành công.");
                        successAlert.showAndWait();
                    }catch (Exception e){
                        Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                        errorAlert.setTitle("Chưa thành công");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Mã họ sinh đã bị trùng vui lòng nhập lại");
                        errorAlert.showAndWait();
                        e.printStackTrace();
                    }
                } else {

                }
        }
    }
    private void getQuery() {

        if (update == false) {

            query = "INSERT INTO user(userID, userName, gender, DoB, email, phoneNumber, address, role, account, password) VALUES(?,?,?,?,?,?,?,?,?,?)";
            query1   ="INSERT INTO student(studentID, userID, parentName, parentPhone) VALUES(?,?,?,?)";
            query2 ="INSERT INTO study(classID, studentID) VALUES(?,?)";

        }else{
            query = "UPDATE user SET userID=?, userName=?, gender=?, DoB=?, email=?, phoneNumber=?, address=?, role=?, account=?, password=? WHERE userID=?";
            query1 = "UPDATE student SET studentID=?, userID=?, parentName=?, parentPhone=? WHERE studentID=?";
            query2 = "UPDATE study SET classID=? WHERE studentID=?";

        }

    }

    private void insert() {

        try {

            Connection conn = ConnectDatabase.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(query);
            pst2.setString(1,tfUserID.getText());
            pst2.setString(2,tfUserName.getText());
            pst2.setString(3,tfGender.getText());
            pst2.setDate(4,java.sql.Date.valueOf(tfDoB.getValue()));
//            pst2.setDate(4,java.sql.Date.valueOf(tfDoB.getValue()));
            pst2.setString(5,tfEmail.getText());
            pst2.setString(6,tfPhoneNumber.getText());
            pst2.setString(7,tfAddress.getText());
            pst2.setString(8,"Học sinh");
            pst2.setString(9,tfAccount.getText());
            pst2.setString(10,tfPassword.getText());
            pst2.execute();
//            String query1   ="INSERT INTO student(studentID, userID, parentName, parentPhone) VALUES(?,?,?,?)";
            PreparedStatement pts1 = conn.prepareStatement(query1);
            pts1.setString(1,tfStudentID.getText());
            pts1.setString(2,tfUserID.getText());
            pts1.setString(3,tfParentName.getText());
            pts1.setString(4,tfParentPhone.getText());
            pts1.execute();

            int classID = getClassID(tfClassName.getText());
            if (classID != -1) {
                // Thực hiện lệnh INSERT INTO study
                PreparedStatement pts3 = conn.prepareStatement(query2);
                pts3.setInt(1,classID);
                pts3.setString(2,tfStudentID.getText());
                pts3.execute();
            }
        } catch (SQLException ex) {
           throw new RuntimeException();
        }

    }
    private int getClassID(String className) throws SQLException {
        Connection conn = ConnectDatabase.getConnection();
        String query = "SELECT classID FROM class WHERE className = ?";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, className);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return rs.getInt("classID");
        }

        return -1; // Trả về giá trị âm nếu không tìm thấy classID
    }
public void setTextField(String userID, String studentID, String userName, String gender,
                         Date DoB, String email, String phoneNumber, String address, String account, String password,
                         String parentName, String parentPhone, String className) {
    tfUserID.setText(userID);
    tfStudentID.setText(studentID);
    tfUserName.setText(userName);
    tfGender.setText(gender);

//    LocalDate localDate = DoB.toLocalDate(); // Convert java.sql.Date to LocalDate
//    LocalDate localDate = DoB.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate localDate = ((java.sql.Date) DoB).toLocalDate();

    tfDoB.setValue(localDate);

    tfEmail.setText(email);
    tfPhoneNumber.setText(phoneNumber);
    tfAddress.setText(address);
    tfAccount.setText(account);
    tfPassword.setText(password);
    tfParentName.setText(parentName);
    tfParentPhone.setText(parentPhone);
    tfClassName.setText(className);
}

    void setUpdate(boolean b) {
        this.update = b;

    }
}
