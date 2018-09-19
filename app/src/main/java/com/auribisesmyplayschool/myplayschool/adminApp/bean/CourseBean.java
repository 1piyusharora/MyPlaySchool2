package com.auribisesmyplayschool.myplayschool.adminApp.bean;

import java.io.Serializable;

public class CourseBean implements Serializable {

    int courseId, adminId;
    String courseName;

    public CourseBean() {
    }

    public CourseBean(int courseId, int adminId, String courseName) {
        this.courseId = courseId;
        this.adminId = adminId;
        this.courseName = courseName;
    }
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return "CourseBean{" +
                "courseId=" + courseId +
                ", adminId=" + adminId +
                ", courseName='" + courseName + '\'' +
                '}';
    }

}
