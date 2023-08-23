package ltnc.qlthcs.models;

import java.util.Date;

public class User {
    private static String userID;
    private static String userName;
    private static String gender;
    private static Date DoB;
    private static String email;
    private static String phoneNumber;
    private static String address;
    private static String role;
    private static String account;
    private static String password;

    public User() {}
    public User(String userID, String userName, String gender, Date Dob, String email, String phoneNumber, String address, String role, String account, String password) {
        this.userID = userID;
        this.userName = userName;
        this.gender = gender;
        this.DoB = Dob;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
        this.account = account;
        this.password = password;
    }

    public User(String userID, String userName, String gender, Date DoB) {
        this.userID = userID;
        this.userName = userName;
        this.gender = gender;
        this.DoB = DoB;
    }
    public User(String userID, String userName, Date dob, String gender, String address, String phoneNumber) {
    }

    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        User.userID = userID;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        User.userName = userName;
    }

    public static String getGender() {
        return gender;
    }

    public static void setGender(String gender) {
        User.gender = gender;
    }

    public static Date getDoB() {
        return DoB;
    }

    public static void setDoB(Date doB) {
        DoB = doB;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getPhoneNumber() {
        return phoneNumber;
    }

    public static void setPhoneNumber(String phoneNumber) {
        User.phoneNumber = phoneNumber;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        User.address = address;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        User.role = role;
    }

    public static String getAccount() {
        return account;
    }

    public static void setAccount(String account) {
        User.account = account;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        User.password = password;
    }

    @Override
    public String toString() {
        return "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", gender='" + gender + '\'' +
                ", Dob=" + DoB + '\'' +
                ", email=" + email + '\'' +
                ", phoneNumber=" + phoneNumber + '\'' +
                ", address=" + address + '\'' +
                ", role=" + role + '\'' +
                ", account=" + account + '\'' +
                ", password=" + password + '\'' +
                '}';
    }


}
