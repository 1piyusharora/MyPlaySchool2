package com.auribisesmyplayschool.myplayschool.adminApp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by White Wolf on 8/13/2018.
 */

public class RoutesBean implements Parcelable{

    String routeName;
    int routeId;
    String routeDesc;
    int activate;
    int branchId;


    public RoutesBean() {
    }

    public RoutesBean(String route_name, int route_id, String route_description, int activate, int branchId) {
        this.routeName = route_name;
        this.routeId = route_id;
        this.routeDesc = route_description;
        this.activate = activate;
        this.branchId = branchId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getRouteDesc() {
        return routeDesc;
    }

    public void setRouteDesc(String routeDesc) {
        this.routeDesc = routeDesc;
    }

    public int getActivate() {
        return activate;
    }

    public void setActivate(int activate) {
        this.activate = activate;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(routeId);
        parcel.writeString(routeName);
        parcel.writeString(routeDesc);
        parcel.writeInt(branchId);
        parcel.writeInt(activate);


    }

    protected RoutesBean(Parcel in) {
        routeId = in.readInt();
        routeName = in.readString();
        routeDesc = in.readString();
        branchId = in.readInt();
        activate = in.readInt();

    }

    public static final Parcelable.Creator<RoutesBean> CREATOR = new Parcelable.Creator<RoutesBean>() {
        @Override
        public RoutesBean createFromParcel(Parcel in) {
            return new RoutesBean(in);
        }

        @Override
        public RoutesBean[] newArray(int size) {
            return new RoutesBean[size];
        }
    };


    @Override
    public String toString() {
        return "RoutesBean{" +
                "routeName='" + routeName + '\'' +
                ", routeId=" + routeId +
                ", routeDesc='" + routeDesc + '\'' +
                ", activate=" + activate +
                ", branchId=" + branchId +
                '}';
    }

}
