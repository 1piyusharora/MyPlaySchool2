package com.auribisesmyplayschool.myplayschool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.auribisesmyplayschool.myplayschool.Helper.Constants;
import com.auribisesmyplayschool.myplayschool.adminApp.activity.AdminMainActivity;
import com.auribisesmyplayschool.myplayschool.adminApp.classes.AdminUtil;
import com.auribisesmyplayschool.myplayschool.bean.AdminBean;
import com.auribisesmyplayschool.myplayschool.bean.SignInBean;
import com.auribisesmyplayschool.myplayschool.classes.AttUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText username, password;
    CheckBox chkShowPass;
    Button login;
    Spinner spinnerUser;
    SignInBean s_in_bean;
    int selectedUser;
    ArrayAdapter adapter;
    ArrayList<String> userList = new ArrayList<>();
    FirebaseFirestore db;
    AdminBean adminBean;
    String email[];
    String pass[];
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    int login_status;
    SpotsDialog progressDialog;

   void initViews(){
       spinnerUser = (Spinner) findViewById(R.id.spinnerUserType);
       userList.add("--Select a user type to continue--");
       userList.add("Admin");
       userList.add("Manager");
       userList.add("Teacher");
       userList.add("Counsellor");
       adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, userList);
       spinnerUser.setAdapter(adapter);
       spinnerUser.setOnItemSelectedListener(this);
       username =  findViewById(R.id.etxt_username);
       password =  findViewById(R.id.etxt_password);
       chkShowPass =  findViewById(R.id.cbShowPwd);
       login =  findViewById(R.id.btn_login);
       login.setOnClickListener(onClickListener);
       pref = getSharedPreferences(AttUtil.shpREG, MODE_PRIVATE);
       login_status = pref.getInt(AttUtil.spLoginStatus, 0);
       editor = pref.edit();
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getSharedPreferences(AttUtil.spREG, MODE_PRIVATE);
        editor = pref.edit();
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        initViews();
        db = FirebaseFirestore.getInstance();
        chkShowPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!isChecked) {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

    }

    boolean validateField() {

        boolean check = true;
        if ( username.getText().toString().isEmpty()) {       //s_in_bean.getUserEmail().isEmpty()
            check = false;
            username.setError("Email cannot be left blank");
        }
        /*if (!username.getText().toString().isEmpty()) {       //s_in_bean.getUserEmail().isEmpty()
            if (username.getText().toString().matches("[a-zA-Z0-9_.-]+@[a-z]+\\.+[a-z]+")) {
                check = false;
                username.setError("Invalid Email ID");
            }
        }*/

        if (password.getText().toString().isEmpty()) {      //s_in_bean.getUserPassword().isEmpty()
            check = false;
            password.setError("Password cannot be left blank");
        }

        if (!(password.getText().toString().isEmpty())) {      //s_in_bean.getUserPassword().isEmpty()
            if (password.length() < 4) {
                check = false;
                password.setError("Password length should be four or above");
            }
        }
        if (selectedUser == 0) {
            check = false;
            Toast.makeText(this, "Please Select User Type to Continue", Toast.LENGTH_LONG).show();
        }
        return check;
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            //s_in_bean.setUserEmail(username.getText().toString().trim());
            //s_in_bean.setUserPassword(password.getText().toString().trim());

            if (validateField()) {
                //if (AttUtil.isNetworkConnected(LoginActivity.this))
                progressDialog = new SpotsDialog(LoginActivity.this, R.style.Custom);
                progressDialog.show();
                    loginFunction();
                //else
                    //loginNetConnect();
            }

        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position > 0) {
            selectedUser = position;
        } else {
            selectedUser = 0;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    void loginFunction(){

        if (selectedUser == 1){

            db.collection(Constants.adminCollection).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    progressDialog.dismiss();

                    email = new String[queryDocumentSnapshots.size()];
                    pass = new String[queryDocumentSnapshots.size()];

                    for(int i=0;i<queryDocumentSnapshots.size();i++){
                         email[i] = queryDocumentSnapshots.getDocuments().get(i).getString("adminEmail");
                         pass[i] = queryDocumentSnapshots.getDocuments().get(i).getString("adminPassword");
                    }

                    for (int i=0;i<queryDocumentSnapshots.size();i++){
                        if(username.getText().toString().equals(email[i]) && password.getText().toString().equals(pass[i])){
                            //adminName = queryDocumentSnapshots.getDocuments().get(i).getString("adminName");
                            //adminId = queryDocumentSnapshots.getDocuments().get(i).getLong("adminId").intValue();
                            adminBean = queryDocumentSnapshots.getDocuments().get(i).toObject(AdminBean.class);
                            afterAdminLogin();

                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    void afterAdminLogin() {
        editor.putInt(AttUtil.shpLoginStatus, 1);
        editor.putInt(AttUtil.shpLoginType, 1);
        editor.commit();
        finish();
        Intent intent = new Intent(LoginActivity.this,AdminMainActivity.class);
        intent.putExtra(AdminUtil.TAG_ADMIN_BEAN,adminBean);
        startActivity(intent);

    }


}
