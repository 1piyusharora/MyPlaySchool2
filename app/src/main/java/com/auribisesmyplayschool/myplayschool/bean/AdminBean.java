package com.auribisesmyplayschool.myplayschool.bean;

import java.io.Serializable;

//, packageType, homeTuitions, planId, categoryId, cityId, employeeId
//, generalInfo, awards

public class AdminBean implements Serializable {

    int adminId, adminStatus;
    //double latitude,longitude;
    String adminName, adminEmail, adminContact, adminPassword, adminInstituteName, adminInstituteCourse, dateFrom, dateTo, institute_code, address;

    public AdminBean() {
    }



    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public int getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(int adminStatus) {
        this.adminStatus = adminStatus;
    }

   /* public int getPackageType() {
        return packageType;
    }

    public void setPackageType(int packageType) {
        this.packageType = packageType;
    }

    public int getHomeTuitions() {
        return homeTuitions;
    }

    public void setHomeTuitions(int homeTuitions) {
        this.homeTuitions = homeTuitions;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }*/

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminContact() {
        return adminContact;
    }

    public void setAdminContact(String adminContact) {
        this.adminContact = adminContact;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminInstituteName() {
        return adminInstituteName;
    }

    public void setAdminInstituteName(String adminInstituteName) {
        this.adminInstituteName = adminInstituteName;
    }

    public String getAdminInstituteCourse() {
        return adminInstituteCourse;
    }

    public void setAdminInstituteCourse(String adminInstituteCourse) {
        this.adminInstituteCourse = adminInstituteCourse;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getInstitute_code() {
        return institute_code;
    }

    public void setInstitute_code(String institute_code) {
        this.institute_code = institute_code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /*public String getGeneralInfo() {
        return generalInfo;
    }

    public void setGeneralInfo(String generalInfo) {
        this.generalInfo = generalInfo;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }*/

    /*@Override
    public String toString() {
        return "AdminBean{" +
                "adminId=" + adminId +
                ", adminStatus=" + adminStatus +
                ", packageType=" + packageType +
                ", homeTuitions=" + homeTuitions +
                ", planId=" + planId +
                ", categoryId=" + categoryId +
                ", cityId=" + cityId +
                ", employeeId=" + employeeId +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", adminName='" + adminName + '\'' +
                ", adminEmail='" + adminEmail + '\'' +
                ", adminContact='" + adminContact + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                ", adminInstituteName='" + adminInstituteName + '\'' +
                ", adminInstituteCourse='" + adminInstituteCourse + '\'' +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", institute_code='" + institute_code + '\'' +
                ", address='" + address + '\'' +
                ", generalInfo='" + generalInfo + '\'' +
                ", awards='" + awards + '\'' +
                '}';
    }*/
}
