package com.auribisesmyplayschool.myplayschool.adminApp.classes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import com.auribisesmyplayschool.myplayschool.classes.MyResponseConnectivity;

public class AdminUtil {
    static AdminUtil util = new AdminUtil();
    public static String TAG_COURSE = "course";
    public static String TAG_ADMIN_BEAN = "admin_details";
    public static String TAG_BRANCH_BEAN = "branch_details";
    public static String TAG_BRANCHARRAYLIST = "branchArray";
    public static String TAG_USER = "user";
    public static String userId="userId";
    public static int REQ_CODE = 1;
    public static int RES_CODE = 2;

    public static ProgressDialog pd;
    MyResponseConnectivity myResponseConnectivity;

    public static final String Error_message= "Error while loading data. Please try again later.";

    //sharedpreferances
    public static String adminId="adminId";
    public static String adminName="adminName";
    public static String adminEmail="adminEmail";
    public static String adminContact="adminContact";
    public static String adminPassword="adminPassword";
    public static String adminStatus="adminStatus";
    public static String adminInstituteName="adminInstituteName";
    public static String adminInstituteCourse="adminInstituteCourse";
    public static String dateFrom="dateFrom";
    public static String dateTo="dateTo";
    public static String packageType = "packageType";
    public static String shpAdminBean = "shpAdminBean";


    public static boolean isNetworkConnected(Activity activity)
    {
        ConnectivityManager cmgr;
        cmgr= (ConnectivityManager)activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=cmgr.getActiveNetworkInfo();
        if(info!=null){
            if(info.isConnected()){
                return true;
            }else{
                return false;
            }
        }else {
            return false;
        }
    }

    public void showMsg(final Activity activity){
        AlertDialog.Builder dialog =new AlertDialog.Builder(activity);
        dialog.setMessage("No Internet Connectivity. Please enable and Retry");
        dialog.setCancelable(false);
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });
        dialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (isNetworkConnected(activity)) {
                    myResponseConnectivity.onMyResponseConnectivity(1);
                } else {
                    myResponseConnectivity.onMyResponseConnectivity(0);
                }
            }
        });
        dialog.create().show();
    }

    public static void progressDialog( final Activity activity){

        pd = new ProgressDialog(activity);
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
    }

    public static void pd(int flag){
        if(flag == 1){
            pd.show();
        }else if(flag == 0){
            pd.dismiss();
        }
    }

    public static AdminUtil getMyUtil(){
        return util;
    }
}
