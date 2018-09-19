package com.auribisesmyplayschool.myplayschool.adminApp.bean;

import java.io.Serializable;

/**
 * Created by kshitij on 30/05/17.
 */

public class CategoryBean implements Serializable {

    int feeCategoryId,branchId,cost;
    String categoryName;

    public CategoryBean(){}

    public CategoryBean(int feeCategoryId, int branchId, String categoryName, int cost) {
        this.feeCategoryId = feeCategoryId;
        this.branchId = branchId;
        this.categoryName = categoryName;
        this.cost = cost;
    }

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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "CategoryBean{" +
                "feeCategoryId=" + feeCategoryId +
                ", branchId=" + branchId +
                ", cost=" + cost +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
