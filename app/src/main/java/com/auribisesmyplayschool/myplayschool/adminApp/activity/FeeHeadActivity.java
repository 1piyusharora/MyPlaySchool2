package com.auribisesmyplayschool.myplayschool.adminApp.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.auribisesmyplayschool.myplayschool.Helper.Constants;
import com.auribisesmyplayschool.myplayschool.LoginActivity;
import com.auribisesmyplayschool.myplayschool.R;
import com.auribisesmyplayschool.myplayschool.adminApp.adapter.FeeHeadAdapter;
import com.auribisesmyplayschool.myplayschool.adminApp.adapter.RoutesAdapter;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.FeeCategoryBean;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.FeeCostBean;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.FeeHeadBean;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.RoutesBean;
import com.auribisesmyplayschool.myplayschool.adminApp.classes.AdminUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

import dmax.dialog.SpotsDialog;

import static com.auribisesmyplayschool.myplayschool.adminApp.activity.RouteBusesActivity.branch_id;

public class FeeHeadActivity extends AppCompatActivity implements View.OnClickListener {

    private int branchId;
    private ListView listViewHeads;
    private Button buttonUpdate;

    FeeHeadBean feeHeadBean;
    FeeCostBean feeCostBean;
    FeeCategoryBean feeCategoryBean;
    private ArrayList<FeeHeadBean> feeHeadBeanArrayList;
    private ArrayList<FeeCostBean> feeCostBeanArrayList;
    private ArrayList<FeeCategoryBean> feeCategoryBeanArrayList;
    private FeeHeadAdapter feeHeadAdapter;
    SpotsDialog progressDialog;


    FirebaseFirestore db,db1,db2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_head);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        branchId=getIntent().getIntExtra("branchId",0);
        feeHeadBeanArrayList = (ArrayList<FeeHeadBean>) getIntent().getSerializableExtra("feeHeadArrayList");
        //Toast.makeText(FeeHeadActivity.this,""+branchId,Toast.LENGTH_SHORT).show();
        //db=FirebaseFirestore.getInstance();
        db1=FirebaseFirestore.getInstance();
        //db2=FirebaseFirestore.getInstance();
        initViews();
        //retrieveFeeHeads();
        fetchCostList();
    }

     void initViews(){
        listViewHeads=(ListView)findViewById(R.id.listViewHeads);
        buttonUpdate=(Button)findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(this);

        feeHeadBean=new FeeHeadBean();
        feeCostBean=new FeeCostBean();
        feeCategoryBean=new FeeCategoryBean();
        //feeHeadBeanArrayList=new ArrayList<>();
        feeCostBeanArrayList=new ArrayList<>();
        feeCategoryBeanArrayList=new ArrayList<>();
       // feeHeadAdapter = new FeeHeadAdapter(FeeHeadActivity.this,R.layout.adapter_fee_heads_layout,feeHeadBeanArrayList);
    }

     void retrieveFeeHeads(){
        // firestore

         db1.collection(Constants.feeHeadCollection).whereEqualTo("branchId",branchId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
             @Override
             public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                 for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                     if (doc.getType() == DocumentChange.Type.ADDED) {
                         //size = queryDocumentSnapshots.size();
                         feeHeadBean = doc.getDocument().toObject(FeeHeadBean.class);
                         feeHeadBeanArrayList.add(feeHeadBean);
                     }
                 }
              //   Toast.makeText(FeeHeadActivity.this,"nm "+feeHeadBeanArrayList.size(),Toast.LENGTH_LONG).show();
                 int countOneTime=0,countAnnual=0,countMonthly=0,counTransportation=0;
                 for(int i=0;i<feeHeadBeanArrayList.size();i++){
                     if(feeHeadBeanArrayList.get(i).getFeeType()==1)
                         countOneTime++;
                     else if(feeHeadBeanArrayList.get(i).getFeeType()==2)
                         countAnnual++;
                     else if(feeHeadBeanArrayList.get(i).getFeeType()==3)
                         countMonthly++;
                     else
                         counTransportation++;
                 }

             }
         });
    }

     void fetchCostList() {
         progressDialog = new SpotsDialog(FeeHeadActivity.this, R.style.Custom);
         progressDialog.show();
        db1.collection(Constants.feeCostCollection).whereEqualTo("branchId",branchId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        //size = queryDocumentSnapshots.size();
                        feeCostBean = doc.getDocument().toObject(FeeCostBean.class);
                        feeCostBeanArrayList.add(feeCostBean);
                    }
                }
                Toast.makeText(FeeHeadActivity.this,"qw"+feeCostBeanArrayList.size(),Toast.LENGTH_LONG).show();
                for(int i=0;i<feeHeadBeanArrayList.size();i++){
                    int headId=feeHeadBeanArrayList.get(i).getHeadId();
                    ArrayList<FeeCostBean> respectiveFeeCostList=new ArrayList<>();
                    for(int j=0;j<feeCostBeanArrayList.size();j++){
                        if(feeCostBeanArrayList.get(j).getHeadId()==headId){
                            respectiveFeeCostList.add(feeCostBeanArrayList.get(j));
                        }
                    }
                    feeHeadBeanArrayList.get(i).setFeeCostBeanArrayList(respectiveFeeCostList);
                }
                fetchCategoryList();
                progressDialog.dismiss();


            }
        });
    }

     void fetchCategoryList() {
        db1.collection(Constants.feeCatgoryCollection).whereEqualTo("branchId",branchId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        //size = queryDocumentSnapshots.size();
                        feeCategoryBean = doc.getDocument().toObject(FeeCategoryBean.class);
                        feeCategoryBeanArrayList.add(feeCategoryBean);
                    }
                }
               compareCollections();

                feeHeadAdapter = new FeeHeadAdapter(FeeHeadActivity.this,R.layout.adapter_fee_heads_layout,feeHeadBeanArrayList);
                listViewHeads.setAdapter(feeHeadAdapter);
            }
        });
    }

     void compareCollections() {

         for(int i=0;i<feeCategoryBeanArrayList.size();i++){
             for(int j=0;j<feeCostBeanArrayList.size();j++){
                 if(feeCategoryBeanArrayList.get(i).getFeeCategoryId()==feeCostBeanArrayList.get(j).getCategoryId()){
                     String categoryName=feeCategoryBeanArrayList.get(i).getCategoryName();
                     //feeCostBean.setCategoryName(categoryName);
                     //FeeCostBean feeCostBean1=new FeeCostBean();
                     feeCostBeanArrayList.set(j,feeCostBean).setCategoryName(categoryName);

                 }
             }
         }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void alertNoFeeHead(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("No Fee Heads Found!");
        builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FeeHeadActivity.this.finish();
            }
        });
        builder.create().show();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonUpdate){
            progressDialog = new SpotsDialog(FeeHeadActivity.this, R.style.Custom);
            progressDialog.show();
            updateFeeHeads();
            updateCostHeads();
            progressDialog.dismiss();
        }
    }

    private void updateFeeHeads() {
        for(int i=1;i<=feeHeadBeanArrayList.size();i++){
            db1.collection(Constants.feeHeadCollection).document(String.valueOf(i)).update("headId",feeHeadBeanArrayList.get(i-1).getHeadId(),"headName",feeHeadBeanArrayList.get(i-1).getHeadName(),"feeType",feeHeadBeanArrayList.get(i-1).getFeeType()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                }
            });
        }
    }

    public void updateCostHeads() {
        for(int i=1;i<=feeCostBeanArrayList.size();i++){
            db1.collection(Constants.feeCostCollection).document(String.valueOf(i)).update("feeCostId",feeCostBeanArrayList.get(i-1).getFeeCostId(),"cost",feeCostBeanArrayList.get(i-1).getCost()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }
            });
        }
    }

    private void alertHeadsUpdated(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Fee Heads Updated!");
        builder.setCancelable(false);
        builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FeeHeadActivity.this.finish();
            }
        });
        builder.create().show();
    }
}
