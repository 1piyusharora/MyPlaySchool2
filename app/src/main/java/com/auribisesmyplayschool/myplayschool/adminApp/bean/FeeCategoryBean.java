package com.auribisesmyplayschool.myplayschool.adminApp.bean;

import java.io.Serializable;

/**
 * Created by White Wolf on 9/17/2018.
 */

public class FeeCategoryBean implements Serializable {

    int feeCategoryId,branchId;
    String categoryName;


    public int getFeeCategoryId() {
        return feeCategoryId;
    }

    public void setFeeCategoryId(int feeCategoryId) {
        this.feeCategoryId = feeCategoryId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "FeeCategoryBean{" +
                "feeCategoryId=" + feeCategoryId +
                ", branchId=" + branchId +
                ", categoryBean='" + categoryName + '\'' +
                '}';
    }

}
