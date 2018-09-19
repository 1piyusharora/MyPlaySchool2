package com.auribisesmyplayschool.myplayschool.adminApp.bean;

/**
 * Created by White Wolf on 8/13/2018.
 */

public class MessageBean {

    int id,audience,type,branchId;
    String message,date,time;

    public MessageBean(int id, int audience, String message, String date, String time, int type, int branchId) {
        this.id = id;
        this.audience = audience;
        this.message = message;
        this.date = date;
        this.time = time;
        this.type = type;
        this.branchId = branchId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAudience() {
        return audience;
    }

    public void setAudience(int audience) {
        this.audience = audience;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
