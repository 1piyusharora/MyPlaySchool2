package com.auribisesmyplayschool.myplayschool.bean;

import java.io.Serializable;

public class SignInBean implements Serializable {
    int branchId,userId,userStatus;
    String branchName,branchAddress,branchContact,userName,userEmail,userContact,userPassword,userType;

    public SignInBean() {
    }

    public SignInBean(int branchId, int userId, int userStatus, String branchName, String branchAddress, String branchContact, String userName, String userEmail, String userContact, String userPassword, String userType) {
        this.branchId = branchId;
        this.userId = userId;
        this.userStatus = userStatus;
        this.branchName = branchName;
        this.branchAddress = branchAddress;
        this.branchContact = branchContact;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userContact = userContact;
        this.userPassword = userPassword;
        this.userType = userType;
    }

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

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
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

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "SignInBean{" +
                "branchId=" + branchId +
                ", userId=" + userId +
                ", userStatus=" + userStatus +
                ", branchName='" + branchName + '\'' +
                ", branchAddress='" + branchAddress + '\'' +
                ", branchContact='" + branchContact + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userContact='" + userContact + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
    /*int tId, tPin, tBatchSt, tNoBatch, tFees, tStatus, empId,tPlan,tHome;
    String tName, tDob, tGender, tEmail,tPassword, tState, tCity, tAddress, tPhn1, tPhn2, tQual, tExperience, tAwards,tFeesPer, tGeneralInfo,tCurrentDate, tExpiryDate;
    Float  tLati, tLongi;

    public SignInBean() {
        tId = 0;
        tPin = 0;
        tBatchSt = 0;
        tNoBatch = 0;
        tFees = 0;
        tStatus = -1;
        empId = 0;
        tName = "";
        tDob = "";
        tGender = "";
        tEmail = "";
        tPlan = 0;
        tPassword = "";
        tState = "";
        tCity = "";
        tAddress = "";
        tPhn1 = "";
        tPhn2 = "";
        tQual = "";
        tExperience = "";
        tAwards = "";
        tHome = -1;
        tFeesPer = "";
        tGeneralInfo = "";
        tLati = 0.0f;
        tLongi = 0.0f;
        tCurrentDate = "";
        tExpiryDate = "";
    }*/
}
