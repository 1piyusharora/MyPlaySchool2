package com.auribisesmyplayschool.myplayschool.bean;

import java.io.Serializable;

/**
 * Created by White Wolf on 8/13/2018.
 */

public class BatchBean implements Serializable {
    int batchId,branCourId,branchId,batchStatus,countSeat,batchTeacherId,userId,activeStudents,inActiveStudents,laterJoinings;
    String batch_title,batch_seat,batch_time,batch_year,batch_course,batch_start_date,coursetitle,userGroup,batcTeachGroup;

    public BatchBean() {
        batchId = 0;
        branCourId = 0;
        branchId = 0;
        countSeat = 0;
    }

    public BatchBean(int batchId, int branCourId, int branchId, int batchStatus, int countSeat, int batchTeacherId, int userId, int activeStudents, int inActiveStudents, int laterJoinings, String batch_title, String batch_seat, String batch_time, String batch_year, String batch_course, String batch_start_date, String coursetitle, String userGroup, String batcTeachGroup) {
        this.batchId = batchId;
        this.branCourId = branCourId;
        this.branchId = branchId;
        this.batchStatus = batchStatus;
        this.countSeat = countSeat;
        this.batchTeacherId = batchTeacherId;
        this.userId = userId;
        this.activeStudents = activeStudents;
        this.inActiveStudents = inActiveStudents;
        this.laterJoinings = laterJoinings;
        this.batch_title = batch_title;
        this.batch_seat = batch_seat;
        this.batch_time = batch_time;
        this.batch_year = batch_year;
        this.batch_course = batch_course;
        this.batch_start_date = batch_start_date;
        this.coursetitle = coursetitle;
        this.userGroup = userGroup;
        this.batcTeachGroup = batcTeachGroup;
    }

    public String getBatcTeachGroup() {
        return batcTeachGroup;
    }

    public void setBatcTeachGroup(String batcTeachGroup) {
        this.batcTeachGroup = batcTeachGroup;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public int getBatchTeacherId() {
        return batchTeacherId;
    }

    public void setBatchTeacherId(int batchTeacherId) {
        this.batchTeacherId = batchTeacherId;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public int getBranCourId() {
        return branCourId;
    }

    public void setBranCourId(int branCourId) {
        this.branCourId = branCourId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(int batchStatus) {
        this.batchStatus = batchStatus;
    }

    public int getCountSeat() {
        return countSeat;
    }

    public void setCountSeat(int countSeat) {
        this.countSeat = countSeat;
    }

    public String getBatch_title() {
        return batch_title;
    }

    public void setBatch_title(String batch_title) {
        this.batch_title = batch_title;
    }

    public String getBatch_seat() {
        return batch_seat;
    }

    public void setBatch_seat(String batch_seat) {
        this.batch_seat = batch_seat;
    }

    public String getBatch_time() {
        return batch_time;
    }

    public void setBatch_time(String batch_time) {
        this.batch_time = batch_time;
    }

    public String getBatch_year() {
        return batch_year;
    }

    public void setBatch_year(String batch_year) {
        this.batch_year = batch_year;
    }

    public String getBatch_course() {
        return batch_course;
    }

    public void setBatch_course(String batch_course) {
        this.batch_course = batch_course;
    }

    public String getBatch_start_date() {
        return batch_start_date;
    }

    public void setBatch_start_date(String batch_start_date) {
        this.batch_start_date = batch_start_date;
    }

    public String getCoursetitle() {
        return coursetitle;
    }

    public void setCoursetitle(String coursetitle) {
        this.coursetitle = coursetitle;
    }

    public int getActiveStudents() {
        return activeStudents;
    }

    public void setActiveStudents(int activeStudents) {
        this.activeStudents = activeStudents;
    }

    public int getInActiveStudents() {
        return inActiveStudents;
    }

    public void setInActiveStudents(int inActiveStudents) {
        this.inActiveStudents = inActiveStudents;
    }

    public int getLaterJoinings() {
        return laterJoinings;
    }

    public void setLaterJoinings(int laterJoinings) {
        this.laterJoinings = laterJoinings;
    }


}

