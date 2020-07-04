package com.example.ui_02;

public class DoseInfo {
    private String d_code;
    private String userID;
    private String d_date;
    private String n_code;
    private String n_name;

    public String getN_name() {return n_name; }

    public void setN_name(String n_name) { this.n_name = n_name; }

    public String getD_code() {
        return d_code;
    }

    public void setD_code(String d_code) {
        this.d_code = d_code;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getD_date() {
        return d_date;
    }

    public void setD_date(String d_date) {
        this.d_date = d_date;
    }

    public String getN_code() {
        return n_code;
    }

    public void setN_code(String n_code) {
        this.n_code = n_code;
    }
}
