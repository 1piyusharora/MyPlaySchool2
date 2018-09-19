package com.auribisesmyplayschool.myplayschool.adminApp.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.auribisesmyplayschool.myplayschool.Helper.Constants;
import com.auribisesmyplayschool.myplayschool.R;
import com.auribisesmyplayschool.myplayschool.adminApp.adapter.CourseAdapter;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.CourseBean;
import com.auribisesmyplayschool.myplayschool.adminApp.classes.AdminUtil;
import com.auribisesmyplayschool.myplayschool.bean.AdminBean;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONObject;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class ManageCourseActivity extends AppCompatActivity implements View.OnClickListener {

    ListView listView;
    ArrayList<CourseBean> courseBeanArrayList;
    CourseAdapter courseAdapter;
    CourseBean courseBean;
    FirebaseFirestore db;
    Intent rcv;
    AdminBean adminBean;
    //int adminId;
    int pos,size;


    public void initViews() {
        courseBean = new CourseBean();
        courseBeanArrayList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.lvCourse);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                courseBean = courseBeanArrayList.get(position);
                Intent passBeanData = new Intent(ManageCourseActivity.this, AddCourseActivity.class);
                passBeanData.putExtra(AdminUtil.TAG_COURSE, courseBean);
                passBeanData.putExtra(AdminUtil.TAG_ADMIN_BEAN, adminBean);
                startActivityForResult(passBeanData, AdminUtil.REQ_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode == AdminUtil.REQ_CODE)&&(resultCode == AdminUtil.RES_CODE)){
            courseBean = (CourseBean) data.getSerializableExtra(AdminUtil.TAG_COURSE);
            if(courseBeanArrayList==null||courseBeanArrayList.size()==0){
                courseBeanArrayList.add(courseBean);
                courseAdapter = new CourseAdapter(ManageCourseActivity.this, R.layout.admin_adapter_course_listitem,
                        courseBeanArrayList);
                listView.setAdapter(courseAdapter);
            }else{
                courseBeanArrayList.add(courseBean);
                courseAdapter.notifyDataSetChanged();
            }
        }else if((requestCode == AdminUtil.REQ_CODE)&&(resultCode == 102)){
            courseBean = (CourseBean) data.getSerializableExtra(AdminUtil.TAG_COURSE);
            courseBeanArrayList.set(pos,courseBean);
            courseAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_course);
        rcv = getIntent();
        //adminId = rcv.getIntExtra("adminId", 0);
        adminBean = (AdminBean) rcv.getSerializableExtra(AdminUtil.TAG_ADMIN_BEAN);



        initViews();
        db = FirebaseFirestore.getInstance();
        get_course();

        //Toast.makeText(ManageCourseActivity.this, "" + adminBean.getAdminId(), Toast.LENGTH_SHORT).show();
    }

    SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            courseAdapter.filter(newText.toString());
            courseAdapter.notifyDataSetChanged();
            return false;
        }
    };


    public void get_course() {
        db.collection(Constants.adminCollection).document(String.valueOf(adminBean.getAdminId())).collection(Constants.coursesCollection).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (!queryDocumentSnapshots.getDocuments().isEmpty()) {
                        courseBean = doc.getDocument().toObject(CourseBean.class);
                        courseBeanArrayList.add(courseBean);
                        courseAdapter = new CourseAdapter(ManageCourseActivity.this, R.layout.admin_adapter_course_listitem, courseBeanArrayList);
                        courseAdapter.notifyDataSetChanged();
                        listView.setAdapter(courseAdapter);
                        size = queryDocumentSnapshots.size();
                    } else {
                        Toast.makeText(ManageCourseActivity.this, "No Classes", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab1:
                Intent intent = new Intent(ManageCourseActivity.this, AddCourseActivity.class);
                intent.putExtra(AdminUtil.TAG_ADMIN_BEAN, adminBean);
                intent.putExtra("size",realTimeSizeCheck());
                startActivityForResult(intent, AdminUtil.REQ_CODE);
                break;

            default:
                break;
        }
    }

    public int realTimeSizeCheck(){
        db.collection(Constants.adminCollection).document(String.valueOf(adminBean.getAdminId())).collection(Constants.coursesCollection).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                size = queryDocumentSnapshots.size();
            }
        });
        return size;
    }

    void floatingbutton(){
        FloatingActionMenu menu =(FloatingActionMenu)findViewById(R.id.fab);
        if(menu.isOpened()){
            menu.close(true);
        }else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        floatingbutton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_admin,menu);
        MenuItem searchitem=menu.findItem(R.id.menu_search);
        MenuItem mItem = menu.findItem(R.id.action_settings);
        mItem.setVisible(false);
        SearchManager searchManager = (SearchManager) ManageCourseActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchitem != null) {
            searchView = (SearchView) searchitem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(ManageCourseActivity.this.getComponentName()));
        }
        searchView.setOnQueryTextListener(onQueryTextListener);
        return true;
    }
}
