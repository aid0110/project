package com.example.ui_02;

public class UserInfo {
    private static String userID,userPassword;

    public UserInfo() {
    }

    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        UserInfo.userID = userID;
    }

    public static String getUserPassword() {
        return userPassword;
    }

    public static void setUserPassword(String userPassword) {
        UserInfo.userPassword = userPassword;
    }
}
