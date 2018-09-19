package com.auribisesmyplayschool.myplayschool.adminApp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.auribisesmyplayschool.myplayschool.Helper.Constants;
import com.auribisesmyplayschool.myplayschool.LoginActivity;
import com.auribisesmyplayschool.myplayschool.R;
import com.auribisesmyplayschool.myplayschool.adminApp.adapter.CategoryAdapter;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.CategoryBean;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.FeeCostBean;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.FeeHeadBean;
import com.auribisesmyplayschool.myplayschool.classes.AttUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class AddFeeHeadsActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{


    private EditText editTextHeadName;
    private Spinner spinnerFeeType;
    private ListView listViewCategories;
    private int branchId, request, feeType;
    CategoryBean categoryBean;
    private ArrayList<CategoryBean> categoryBeanArrayList;
    private ArrayList<FeeHeadBean> feeHeadBeanArrayList;
    //    private ArrayList<Integer> feeTypeCountArrayList;
    private CategoryAdapter categoryAdapter;
    private ArrayList<String> feeTypeList = new ArrayList<>();

    //private Button submitButton;
    private SharedPreferences preferences;
    //private LinearLayout footerLayout;
    List<String> feeTypeCountArrayList;
    ArrayAdapter adapter;

    FirebaseFirestore db;
    FeeHeadBean feeheadBean;
    FeeCostBean feecostBean;
    SpotsDialog progressDialog;



    private void initViews() {
        feeheadBean = new FeeHeadBean();
        feecostBean = new FeeCostBean();
        feeHeadBeanArrayList = (ArrayList<FeeHeadBean>) getIntent().getSerializableExtra("feeHeadArrayList");
        db=FirebaseFirestore.getInstance();
        categoryBean=new CategoryBean();
        feeTypeList.add("--Select a fee type--");
        feeTypeList.add("One Time");
        feeTypeList.add("Annual");
        feeTypeList.add("Monthly");
        feeTypeList.add("Transportation");
        branchId = getIntent().getIntExtra("branchId", 0);
        LayoutInflater inflater = getLayoutInflater();

        //footerLayout = (LinearLayout) inflater.inflate(R.layout.list_footer_layout, null);
        preferences = getSharedPreferences(AttUtil.shpREG, MODE_PRIVATE);
        //submitButton = footerLayout.findViewById(R.id.buttonSubmit);
        editTextHeadName = findViewById(R.id.edtName);
        spinnerFeeType = findViewById(R.id.spinnerFeeType);
        listViewCategories = findViewById(R.id.listViewCategories);
        categoryBeanArrayList=new ArrayList<>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, feeTypeList);
        spinnerFeeType.setAdapter(adapter);
        spinnerFeeType.setOnItemSelectedListener(this);
        //submitButton.setOnClickListener(this);
        //listViewCategories.addFooterView(footerLayout);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fee_heads);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        feeTypeCountArrayList = (List<String>) getIntent().getSerializableExtra("feeTypeList");
        initViews();
        feeTypeList.clear();
      /*  AdminMainActivity.countOneTime=0;
        AdminMainActivity.countAnnual=0;
        AdminMainActivity.countMonthly=0;
        AdminMainActivity.counTransportation=0;*/
        feeTypeList.add("--Select a fee type--");
        try {
            feeTypeList.add("One Time("+feeTypeCountArrayList.get(0)+")");
        } catch (Exception e) {
            feeTypeList.add("One Time(0)");
        }
        try {
            feeTypeList.add("Annual("+feeTypeCountArrayList.get(1)+")");
        } catch (Exception e) {
            feeTypeList.add("Annual(0)");
        }
        try {
            feeTypeList.add("Monthly("+feeTypeCountArrayList.get(2)+")");
        } catch (Exception e) {
            feeTypeList.add("Monthly(0)");
        }
        try {
            feeTypeList.add("Transportation("+feeTypeCountArrayList.get(3)+")");
        } catch (Exception e) {
            feeTypeList.add("Transportation(0)");
        }
        adapter.notifyDataSetChanged();
        retrieveCategories();
    }


    private void retrieveCategories() {
        //firestore

        db.collection(Constants.feeCatgoryCollection).whereEqualTo("branchId",branchId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        //size = queryDocumentSnapshots.size();
                        categoryBean = doc.getDocument().toObject(CategoryBean.class);
                        categoryBeanArrayList.add(categoryBean);
                    }
                }

                categoryAdapter = new CategoryAdapter(AddFeeHeadsActivity.this, R.layout.adapter_categories, categoryBeanArrayList, 2);
                listViewCategories.setAdapter(categoryAdapter);
            }
        });

    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        feeType = position;
        if (position>0){
            for (int i = 0; i < categoryBeanArrayList.size(); i++)
                categoryBeanArrayList.get(i).setCost(0);
            categoryAdapter.notifyDataSetChanged();
        }
        if (position==4){
            listViewCategories.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

   /* @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("countOT",AdminMainActivity.countOneTime).putExtra("countAN",AdminMainActivity.countAnnual).putExtra("countMN",AdminMainActivity.countMonthly).putExtra("countTR",AdminMainActivity.counTransportation);
        setResult(4,intent);
        finish();
    }*/
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
       menu.add(0,1,0,"Add").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
       return super.onCreateOptionsMenu(menu);
   }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        if (item.getItemId() ==1){
            if (valid()){
                progressDialog = new SpotsDialog(AddFeeHeadsActivity.this, R.style.Custom);
                progressDialog.show();
                setData();
                addFeeHead();
            }

        }

        return super.onOptionsItemSelected(item);
    }

     void addFeeHead() {
         db.collection(Constants.feeHeadCollection).document(String.valueOf(feeHeadBeanArrayList.get(feeHeadBeanArrayList.size()-1).getHeadId()+1))
                 .set(feeheadBean).addOnSuccessListener(new OnSuccessListener<Void>() {
             @Override
             public void onSuccess(Void aVoid) {
                 addCost();
             }
         });
    }

     void addCost() {
         db.collection(Constants.feeCostCollection).document(String.valueOf(8))
                 .set(feecostBean).addOnSuccessListener(new OnSuccessListener<Void>() {
             @Override
             public void onSuccess(Void aVoid) {
                 progressDialog.dismiss();
                 Toast.makeText(AddFeeHeadsActivity.this,"Added",Toast.LENGTH_SHORT).show();

             }
         });
    }

    void setData(){
        feeheadBean.setHeadName(editTextHeadName.getText().toString().trim());
        feeheadBean.setHeadId(feeHeadBeanArrayList.get(feeHeadBeanArrayList.size()-1).getHeadId()+1);
        feeheadBean.setBranchId(branchId);
        feeheadBean.setAdminId(feeHeadBeanArrayList.get(0).getAdminId());
        feeheadBean.setFeeType(feeType);

        feecostBean.setBranchId(branchId);
        feecostBean.setHeadId(feeHeadBeanArrayList.get(feeHeadBeanArrayList.size()-1).getHeadId()+1);
        feecostBean.setCost(categoryBeanArrayList.get(0).getCost());
        feecostBean.setCategoryId(categoryBeanArrayList.get(0).getFeeCategoryId());
    }

    private boolean valid() {
        boolean valid = true;
        if (editTextHeadName.getText().toString().trim().isEmpty()) {
            valid = false;
            editTextHeadName.setError("Head name cannot be empty");
            Toast.makeText(this, "Head name cannot be empty.", Toast.LENGTH_LONG).show();
        }
        if (feeType == 0) {
            valid = false;
            Toast.makeText(this, "Select a Fee Type to continue.", Toast.LENGTH_LONG).show();
        }
        if (feeType == 4){
            try {
                if( Integer.parseInt(feeTypeCountArrayList.get(feeType-1))>0) {
                    valid = false;
                    Toast.makeText(this, "Only one fee head is allowed in Transportation Category.", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return valid;
    }
}
