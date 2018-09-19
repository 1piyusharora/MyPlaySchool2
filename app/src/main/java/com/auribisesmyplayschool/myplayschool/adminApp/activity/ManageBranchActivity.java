package com.auribisesmyplayschool.myplayschool.adminApp.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.auribisesmyplayschool.myplayschool.Helper.Constants;
import com.auribisesmyplayschool.myplayschool.R;
import com.auribisesmyplayschool.myplayschool.adminApp.adapter.BranchListAdapter;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.BranchBean;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.UserBean;
import com.auribisesmyplayschool.myplayschool.adminApp.classes.AdminUtil;
import com.auribisesmyplayschool.myplayschool.bean.AdminBean;
import com.auribisesmyplayschool.myplayschool.classes.AttUtil;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class ManageBranchActivity extends AppCompatActivity implements View.OnClickListener{
    ListView listView;
    ArrayList<BranchBean> branchBeanArrayList;
    BranchListAdapter branchListAdapter;
    int branchId, branchStatus;
    String branchName, branchContact;
    BranchBean branchBean;
    int pos,posSpnBatch=0;
    String bStatus;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    ArrayList<String> spinList;
    Dialog dialog;
    FirebaseFirestore db;
    AdminBean adminBean;
    int branch_id;


    //ArrayList<BatchBean> batchBeanArrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_manage_branch);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        prefs = getSharedPreferences(AttUtil.shpREG, Context.MODE_PRIVATE);
        db = FirebaseFirestore.getInstance();
        Intent rcv = getIntent();
        adminBean = (AdminBean) rcv.getSerializableExtra(AdminUtil.TAG_ADMIN_BEAN);
        initViews();
       /* Intent i = getIntent();
        if(i.hasExtra(AdminUtil.TAG_BRANCHARRAYLIST)) {
            branchBeanArrayList = (ArrayList<BranchBean>) i.getSerializableExtra(AdminUtil.TAG_BRANCHARRAYLIST);
            if (branchBeanArrayList.isEmpty()) {
                getSupportActionBar().setTitle("Branch(0)");
                Toast.makeText(ManageBranchActivity.this, "No Branch(es) found", Toast.LENGTH_LONG).show();
            } else {
                getSupportActionBar().setTitle("Branch("+branchBeanArrayList.size()+")");
                branchListAdapter = new BranchListAdapter(ManageBranchActivity.this,
                        R.layout.admin_adapter_branch_listitem, branchBeanArrayList);
                listView.setAdapter(branchListAdapter);
            }
        }*/
       retrieveBranch();

    }



    public void retrieveBranch() {
        db.collection(Constants.branchCollection).whereEqualTo("adminId",adminBean.getAdminId()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            //size = queryDocumentSnapshots.size();
                            branchBean = doc.getDocument().toObject(BranchBean.class);
                            branch_id = branchBean.getBranchId();
                            branchBeanArrayList.add(branchBean);
                            branchListAdapter = new BranchListAdapter(ManageBranchActivity.this,
                                    R.layout.admin_adapter_branch_listitem, branchBeanArrayList);
                            listView.setAdapter(branchListAdapter);
                            branchListAdapter.notifyDataSetChanged();
                            getSupportActionBar().setTitle("Branch("+branchBeanArrayList.size()+")");
                        }else {
                            getSupportActionBar().setTitle("Branch(0)");
                            Toast.makeText(ManageBranchActivity.this, "No Branch(es) found", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });


    }

    public void initViews() {
       // batchBeanArrayList = new ArrayList<>();
        branchBeanArrayList = new ArrayList<>();
        branchBean = new BranchBean();
        //batchBeanArrayList = new ArrayList<>();
        spinList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        editor = prefs.edit();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                branchId = branchBeanArrayList.get(position).getBranchId();
                branchName = branchBeanArrayList.get(position).getBranchName();
                branchContact = branchBeanArrayList.get(position).getBranchContact();
                branchStatus = branchBeanArrayList.get(position).getBranchStatus();
                branchBean = branchBeanArrayList.get(position);
                if (branchBean.getBranchStatus() == 1) {
                    bStatus = "Deactivate";
                } else {
                    bStatus = "Activate";
                }
                show_options();
            }
        });
    }

    public void show_options() {
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab1:
                Intent intent = new Intent(ManageBranchActivity.this,AddBranchActivity.class);
                intent.putExtra(AdminUtil.TAG_ADMIN_BEAN,adminBean);
                intent.putExtra("branch_id",branch_id);
                startActivityForResult(intent, AdminUtil.REQ_CODE);
                break;
            case R.id.fab2:
                startActivity(new Intent(ManageBranchActivity.this,BranchCourseActivity.class).
                        putExtra(AdminUtil.TAG_BRANCHARRAYLIST,branchBeanArrayList));
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        floatingbutton();
    }
    void floatingbutton(){
        FloatingActionMenu menu =(FloatingActionMenu)findViewById(R.id.fab);
        if(menu.isOpened()){
            menu.close(true);
        }else {
            Intent i = new Intent();
            i.putExtra(AdminUtil.TAG_BRANCHARRAYLIST,branchBeanArrayList);
            setResult(AdminUtil.RES_CODE,i);
            finish();
        }

    }

}
