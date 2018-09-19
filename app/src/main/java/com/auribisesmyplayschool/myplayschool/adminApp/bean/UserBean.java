package com.auribisesmyplayschool.myplayschool.adminApp.bean;

import java.io.Serializable;

public class UserBean implements Serializable {
    int userId,userStatus,adminId,userType,branchId,userSalary,busId;
    String userName,userEmail,userContact,userPassword,branchName;

    public UserBean() {
    }

    public UserBean(int userId, int userStatus, int adminId, String userName, String userEmail,
                    String userContact, String userPassword, int userType,int branchId,String branchName,
                    int userSalary) {
        this.userId = userId;
        this.userStatus = userStatus;
        this.adminId = adminId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userContact = userContact;
        this.userPassword = userPassword;
        this.userType = userType;
        this.branchId = branchId;
        this.branchName = branchName;
        this.userSalary = userSalary;
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

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
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

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public int getUserSalary() {
        return userSalary;
    }

    public void setUserSalary(int userSalary) {
        this.userSalary = userSalary;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userId=" + userId +
                ", userStatus=" + userStatus +
                ", adminId=" + adminId +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userContact='" + userContact + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userType='" + userType + '\'' +
                ", branchId='" + branchId + '\'' +
                ", busId='"+busId+'\''+
                '}';
    }
}
