package com.auribisesmyplayschool.myplayschool.adminApp.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.auribisesmyplayschool.myplayschool.Helper.Constants;
import com.auribisesmyplayschool.myplayschool.R;
import com.auribisesmyplayschool.myplayschool.adminApp.activity.AddUserActivity;
import com.auribisesmyplayschool.myplayschool.adminApp.activity.ManageBranchManagerActivity;
import com.auribisesmyplayschool.myplayschool.adminApp.activity.ViewAssignedBranchesActivity;
import com.auribisesmyplayschool.myplayschool.adminApp.adapter.UserListAdapter;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.UserBean;
import com.auribisesmyplayschool.myplayschool.adminApp.classes.AdminUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class ManagerFragment extends Fragment {
    private UserBean userBean;
    private ListView listView;
    public UserListAdapter userListAdapter;
    int pos,userStatus;
    private  String uStatus;
    FirebaseFirestore db;
    ListenerRegistration registration;

    public ManagerFragment() {
    }

    public void initlist(){
         userListAdapter = new UserListAdapter(getActivity()
                    ,R.layout.admin_adapter_user_listitem, ManageBranchManagerActivity.managerBeanArrayList);
            listView.setAdapter(userListAdapter);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        View view = inflater.inflate(R.layout.admin_fragment_manager, container, false);
        listView = (ListView)view.findViewById(R.id.lvFragmentManager);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                //userId = branchManagerList.get(position).getUserId();
                userBean = ManageBranchManagerActivity.managerBeanArrayList.get(position);
                registration = db.collection(Constants.usersCollection).document(String.valueOf(position+1)).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                          userStatus = documentSnapshot.getLong("userStatus").intValue();
                          if(userStatus==1){
                              uStatus = "Deactivate";
                              Toast.makeText(getContext(),""+uStatus,Toast.LENGTH_SHORT).show();
                          }else{
                              uStatus = "Activate";
                          }
                    }
                });

                if(userBean.getUserStatus() == 1){
                    uStatus = "Deactivate";
                } else {
                    uStatus = "Activate";
                }
                show_options();
            }
        });
        return view;
    }

    public void show_options(){
        AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
        String[] options = {"View Assigned Branches","Update Details",uStatus,"Resend Credentials"};

        build.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case 0:
                        startActivity(new Intent(getActivity(),ViewAssignedBranchesActivity.class)
                                .putExtra(AdminUtil.userId,userBean.getUserId()));
                        break;
                    case 1:
                        Intent passBeanData = new Intent(getActivity(),AddUserActivity.class);
                        passBeanData.putExtra(AdminUtil.TAG_USER,userBean);
                        startActivityForResult(passBeanData,AdminUtil.REQ_CODE);
                        break;
                    case 2:
                        if (AdminUtil.isNetworkConnected(getActivity()))
                            start_deactivate();
                        else
                            //activateNetConnect();
                        break;
                   /* case 3:
                        if (AdminUtil.isNetworkConnected(getActivity()))
                            resendCredentials();
                        else
                            resendNetConnect();
                        break;*/
                }
            }
        });

        build.create().show();
    }


    private void start_deactivate() {

    }

    public void updateListFunction(){
        if(ManageBranchManagerActivity.managerBeanArrayList.size()>1&&ManageBranchManagerActivity.managerBeanArrayList!=null){
            userListAdapter.notifyDataSetChanged();
        }else{
            userListAdapter = new UserListAdapter(getActivity()
                    , R.layout.admin_adapter_user_listitem,
                    ManageBranchManagerActivity.managerBeanArrayList);
            listView.setAdapter(userListAdapter);
        }
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Managers"
                +" ("+ManageBranchManagerActivity.managerBeanArrayList.size()+")");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode == AdminUtil.REQ_CODE)&&(resultCode == AdminUtil.RES_CODE)){
            userBean  = (UserBean) data.getSerializableExtra(AdminUtil.TAG_USER);
            ManageBranchManagerActivity.managerBeanArrayList.set(pos,userBean);
            ((AppCompatActivity)getActivity()).getSupportActionBar()
                    .setTitle("Managers" +" ("+ManageBranchManagerActivity.managerBeanArrayList.size()+")");
            this.userListAdapter.notifyDataSetChanged();
        }

    }
}
