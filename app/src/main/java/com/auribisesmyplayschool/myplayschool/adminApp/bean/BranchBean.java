package com.auribisesmyplayschool.myplayschool.adminApp.bean;

import java.io.Serializable;
//,submit_fees,noAdmission,branCourId  int
//img_url,userName,courseName,userEmail  String
public class BranchBean implements Serializable {
    int branchId,userId,branchStatus,adminId;
    String branchName;
    String branchAddress;
    String branchContact;
    String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    String userEmail;

    public BranchBean() {
    }

    /*public BranchBean(int branchId, int userId, int branchStatus, int adminId, String branchName,
                      String branchAddress, String branchContact, String img_url, String userName,int submit_fees
            ,String courseName,int noAdmission,int branCourId,String userEmail) {
        this.branchId = branchId;
        this.userId = userId;
        this.branchStatus = branchStatus;
        this.adminId = adminId;
        this.branchName = branchName;
        this.branchAddress = branchAddress;
        this.branchContact = branchContact;
    }*/

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBranchStatus() {
        return branchStatus;
    }

    public void setBranchStatus(int branchStatus) {
        this.branchStatus = branchStatus;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    public String getBranchContact() {
        return branchContact;
    }

    public void setBranchContact(String branchContact) {
        this.branchContact = branchContact;
    }



    /*@Override
    public String toString() {
        return "BranchBean{" +
                "branchId=" + branchId +
                ", userId=" + userId +
                ", branchStatus=" + branchStatus +
                ", adminId=" + adminId +
                ", branchName='" + branchName + '\'' +
                ", branchAddress='" + branchAddress + '\'' +
                ", branchContact='" + branchContact + '\'' +
                ", img_url='" + img_url + '\'' +
                ", branCourId='" + branCourId + '\'' +
                '}';
    }*/
}
