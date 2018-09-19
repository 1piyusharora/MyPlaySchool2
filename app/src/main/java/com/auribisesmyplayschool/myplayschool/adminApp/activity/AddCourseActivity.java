package com.auribisesmyplayschool.myplayschool.adminApp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.auribisesmyplayschool.myplayschool.Helper.Constants;
import com.auribisesmyplayschool.myplayschool.LoginActivity;
import com.auribisesmyplayschool.myplayschool.R;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.CourseBean;
import com.auribisesmyplayschool.myplayschool.adminApp.classes.AdminUtil;
import com.auribisesmyplayschool.myplayschool.bean.AdminBean;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

import dmax.dialog.SpotsDialog;

public class AddCourseActivity extends AppCompatActivity {
    EditText edCourseName;
    Button btnAddcourse;
    TextView txtCourseName;
    boolean bool = false;
    CourseBean courseBean, rcvCourseBean;
    FirebaseFirestore db;
    AdminBean adminBean;
    SpotsDialog progressDialog;
    int size;
    Intent rcvi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
         rcvi = getIntent();
        adminBean = (AdminBean) rcvi.getSerializableExtra(AdminUtil.TAG_ADMIN_BEAN);
        db = FirebaseFirestore.getInstance();

        if(rcvi.hasExtra(AdminUtil.TAG_COURSE)){
            bool=true;
            getSupportActionBar().setTitle("Update Class Details");
        }else
            getSupportActionBar().setTitle("Add Class");

        if(bool){
            rcvCourseBean = (CourseBean) rcvi.getSerializableExtra(AdminUtil.TAG_COURSE);
            courseBean = rcvCourseBean;
            edCourseName.setText(courseBean.getCourseName().toString());
            txtCourseName.setText("Edit Class Details");
            btnAddcourse.setText("Update");
        }

        Toast.makeText(AddCourseActivity.this,""+adminBean.getAdminId(),Toast.LENGTH_SHORT).show();

    }

    public void initViews(){
        rcvCourseBean = new CourseBean();
        edCourseName = (EditText) findViewById(R.id.edcourseName);
        btnAddcourse = (Button) findViewById(R.id.button);
        txtCourseName = (TextView) findViewById(R.id.textView2);
        btnAddcourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edCourseName.getText().toString().length() != 0 ) {
                    courseBean.setCourseName(edCourseName.getText().toString().trim());
                    /*if (AdminUtil.isNetworkConnected(AddCourseActivity.this)){
                        //add_course_thread();
                    }else{
                        //addNetConnect();
                    }*/
                    if(bool){
                        progressDialog = new SpotsDialog(AddCourseActivity.this, R.style.Update_Course);
                        progressDialog.show();
                        updateCourse();
                    }else {
                        progressDialog = new SpotsDialog(AddCourseActivity.this, R.style.Update_Course);
                        progressDialog.show();
                        addCourse();
                    }
                }else{
                    Toast.makeText(AddCourseActivity.this,
                            "Error:\nEmpty Class Name", Toast.LENGTH_SHORT).show();
                }
            }
        });
        courseBean = new CourseBean();
    }

    void updateCourse(){
        db.collection(Constants.adminCollection).document(String.valueOf(adminBean.getAdminId())).collection(Constants.coursesCollection).document(String.valueOf(courseBean.getCourseId())).update("courseName",edCourseName.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();
                Toast.makeText(AddCourseActivity.this, "Class Updated ", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.putExtra(AdminUtil.TAG_COURSE,courseBean);
                setResult(102,intent);
                finish();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddCourseActivity.this, "Something Went Wrong!! ", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void addCourse(){
        size = rcvi.getIntExtra("size",0);
        courseBean.setAdminId(adminBean.getAdminId());
        courseBean.setCourseName(edCourseName.getText().toString().trim());
        courseBean.setCourseId(size+1);
        db.collection(Constants.adminCollection).document(String.valueOf(adminBean.getAdminId())).collection(Constants.coursesCollection).document(String.valueOf(size+1)).set(courseBean).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddCourseActivity.this, "New Class Added ", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.putExtra(AdminUtil.TAG_COURSE,courseBean);
                setResult(AdminUtil.RES_CODE,intent);
                finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




}
