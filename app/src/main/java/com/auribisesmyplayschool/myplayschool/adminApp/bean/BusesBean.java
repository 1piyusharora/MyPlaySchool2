package com.auribisesmyplayschool.myplayschool.adminApp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by White Wolf on 8/13/2018.
 */

public class BusesBean implements Parcelable {

    int busId;
    String busNumber;
    String busDesc;
    int branchId;
    int activate;


    // for getting data from driver bean and array bean
    int driverId;
    int maidId;
    int routeId;

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public BusesBean()
    {
    }

    public BusesBean(int bus_id, String bus_number, String bus_description, int branch_id, int activate,int routeId) {
        this.busId = bus_id;
        this.busNumber = bus_number;
        this.busDesc = bus_description;
        this.branchId = branch_id;
        this.activate = activate;
        this.routeId =  routeId;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getBusDesc() {
        return busDesc;
    }

    public void setBusDesc(String busDesc) {
        this.busDesc = busDesc;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getActivate() {
        return activate;
    }

    public void setActivate(int activate) {
        this.activate = activate;
    }

    public int getDriverId() {return driverId;}

    public void setDriverId(int driverId) {this.driverId = driverId;}

    public int getMaidId() {return maidId;}

    public void setMaidId(int maidId) {this.maidId = maidId;}

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags)
    {
        parcel.writeInt(busId);
        parcel.writeString(busNumber);
        parcel.writeString(busDesc);
        parcel.writeInt(branchId);
        parcel.writeInt(activate);
        parcel.writeInt(maidId);
        parcel.writeInt(driverId);
        parcel.writeInt(routeId);
    }

    protected BusesBean(Parcel in)
    {
        busId = in.readInt();
        busNumber = in.readString();
        busDesc = in.readString();
        branchId = in.readInt();
        activate = in.readInt();
        driverId = in.readInt();
        maidId = in.readInt();
        routeId = in.readInt();

    }

    public static final Parcelable.Creator<BusesBean> CREATOR = new Parcelable.Creator<BusesBean>()
    {
        @Override
        public BusesBean createFromParcel(Parcel in) {
            return new BusesBean(in);
        }

        @Override
        public BusesBean[] newArray(int size) {
            return new BusesBean[size];
        }
    };


    @Override
    public String toString() {
        return "BusesBean{" +
                "busId=" + busId +
                ", busNumber='" + busNumber + '\'' +
                ", busDesc='" + busDesc + '\'' +
                ", branchId=" + branchId +
                ", activate=" + activate +
                ", driverId=" + driverId +
                ", maidId=" + maidId +
                ", routeId=" + routeId +
                '}';
    }

}
