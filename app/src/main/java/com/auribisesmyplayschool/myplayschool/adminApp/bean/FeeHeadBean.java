package com.auribisesmyplayschool.myplayschool.adminApp.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by White Wolf on 8/29/2018.
 */



public class FeeHeadBean implements Serializable {

    int headId,feeType,branchId,adminId;
    String headName;
    ArrayList<FeeCostBean> feeCostBeanArrayList;

    public FeeHeadBean(){

    }

    public FeeHeadBean(int headId, int feeType, int branchId, int adminId, String headName, ArrayList<FeeCostBean> feeCostBeanArrayList) {
        this.headId = headId;
        this.feeType = feeType;
        this.branchId = branchId;
        this.adminId = adminId;
        this.headName = headName;
        this.feeCostBeanArrayList = feeCostBeanArrayList;
    }

    public int getHeadId() {
        return headId;
    }

    public void setHeadId(int headId) {
        this.headId = headId;
    }

    public int getFeeType() {
        return feeType;
    }

    public void setFeeType(int feeType) {
        this.feeType = feeType;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public ArrayList<FeeCostBean> getFeeCostBeanArrayList() {
        return feeCostBeanArrayList;
    }

    public void setFeeCostBeanArrayList(ArrayList<FeeCostBean> feeCostBeanArrayList) {
        this.feeCostBeanArrayList = feeCostBeanArrayList;
    }

    @Override
    public String toString() {
        return "FeeHeadBean{" +
                "headId=" + headId +
                ", feeType=" + feeType +
                ", branchId=" + branchId +
                ", adminId=" + adminId +
                ", headName='" + headName + '\'' +
                ", feeCostBeanArrayList=" + feeCostBeanArrayList +
                '}';
    }
}
