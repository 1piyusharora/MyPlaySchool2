package com.auribisesmyplayschool.myplayschool.adminApp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.auribisesmyplayschool.myplayschool.R;
import com.auribisesmyplayschool.myplayschool.adminApp.adapter.DigitalMsgAdapter;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.BranchBean;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.MessageBean;
import com.auribisesmyplayschool.myplayschool.adminApp.classes.AdminUtil;
import com.auribisesmyplayschool.myplayschool.bean.AdminBean;
import com.auribisesmyplayschool.myplayschool.bean.BatchBean;
import com.auribisesmyplayschool.myplayschool.classes.AttUtil;

import org.json.JSONObject;

import java.util.ArrayList;

public class DigitalMessageActivity extends AppCompatActivity  {

    ListView listViewMsgs;
    MessageBean messageBeanUpdated;
    MessageBean messageBean;
    DigitalMsgAdapter adapter;
    ArrayList<MessageBean> messageList,tempList,tempList2;
    int updateDelete,posi,update,responseSignal=0,posBranch = 0,reqCode = 0;
    String message;
    ArrayList<BranchBean> branchBeanArrayList;
    ArrayList<BatchBean> batchBeanArrayList,batchBeanArrayListTemp;
    ArrayList<String> branchNameArrayList;
    SharedPreferences preferences;
    AlertDialog selectBranchAlertDialog;
    AdminBean adminBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_digital_message);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Digital Messages");
        preferences = getSharedPreferences(AttUtil.shpREG, Context.MODE_PRIVATE);

        Intent rcv = getIntent();
        adminBean = (AdminBean) rcv.getSerializableExtra(AdminUtil.TAG_ADMIN_BEAN);
        branchBeanArrayList=(ArrayList<BranchBean>) rcv.getSerializableExtra(AdminUtil.TAG_BRANCHARRAYLIST);

        initViews();
        messageList = new ArrayList<>();
        branchNameArrayList = new ArrayList<>();
        branchNameArrayList.add("--No Branch Found--");
        batchBeanArrayList = new ArrayList<>();
        batchBeanArrayListTemp = new ArrayList<>();

    }
    void initViews(){
        AdminUtil.progressDialog(this);
        tempList=new ArrayList<>();
        tempList2 = new ArrayList<>();
        listViewMsgs=(ListView)findViewById(R.id.listViewMsg);
        listViewMsgs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }



    void branchNetConnect(){
        reqCode = 1;
        AdminUtil utility = AdminUtil.getMyUtil();
        utility.showMsg(DigitalMessageActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,1,"Choose").setIcon(R.drawable.filter).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }else if(item.getItemId()==1){
            alertFilter();
        }
        return super.onOptionsItemSelected(item);
    }

    void alertFilter(){
        batchBeanArrayListTemp.clear();
        for(int i = 0;i<batchBeanArrayList.size();i++){
            if(branchBeanArrayList.get(posBranch-1).getBranchId()==batchBeanArrayList.get(i).getBranchId()){
                batchBeanArrayListTemp.add(batchBeanArrayList.get(i));
            }
        }
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        CharSequence options[]=new CharSequence[batchBeanArrayListTemp.size()+2];
        for (int i=0;i<batchBeanArrayListTemp.size();i++){
            options[i]=batchBeanArrayListTemp.get(i).getBatch_title();
        }
        options[batchBeanArrayListTemp.size()]="All";
        options[batchBeanArrayListTemp.size()+1]="Student";
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                tempList2.clear();
                tempList2.addAll(messageList);
                messageList.clear();
                if(position==batchBeanArrayListTemp.size()){
                    messageList.addAll(tempList);
                    if(messageList.size()>0||messageList!=null){
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(DigitalMessageActivity.this, "No message(s) for this batch.", Toast.LENGTH_SHORT).show();
                        messageList.addAll(tempList2);
                        adapter.notifyDataSetChanged();
                    }
                    dialogInterface.dismiss();
                }else if(position==batchBeanArrayListTemp.size()+1){
                    for (int i=0;i<tempList.size();i++){
                        if(tempList.get(i).getType()==1){
                            messageList.add(tempList.get(i));
                        }
                        if(messageList.size()>0||messageList!=null){
                            adapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(DigitalMessageActivity.this, "No message(s) for this batch.", Toast.LENGTH_SHORT).show();
                            messageList.addAll(tempList2);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    dialogInterface.dismiss();
                }else{
                    for (int i=0;i<tempList.size();i++){
                        if(tempList.get(i).getType()==0 &&
                                tempList.get(i).getAudience()==batchBeanArrayListTemp.get(position).getBatchId()){
                            messageList.add(tempList.get(i));
                        }
                    }
                    if(messageList.size()>0||messageList!=null){
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(DigitalMessageActivity.this, "No message(s) for this batch.", Toast.LENGTH_SHORT).show();
                        messageList.addAll(tempList2);
                        adapter.notifyDataSetChanged();
                    }
                    dialogInterface.dismiss();
                }
            }
        });
        builder.create().show();
    }

    void selectBranchDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DigitalMessageActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.admin_custom_branch_batch_spn, null);
        dialogBuilder.setTitle("Select a  Branch");
        dialogBuilder.setView(dialogView);
        Spinner spinner = (Spinner)dialogView.findViewById(R.id.spnBranchMsgAdmin);
        spinner.setAdapter(new ArrayAdapter(DigitalMessageActivity.this,
                android.R.layout.simple_spinner_dropdown_item,branchNameArrayList));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                posBranch = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        dialogBuilder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(posBranch>0){
                   // selectMessages();
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        selectBranchAlertDialog = dialogBuilder.create();
        selectBranchAlertDialog.setCancelable(false);
        selectBranchAlertDialog.show();
    }



}
