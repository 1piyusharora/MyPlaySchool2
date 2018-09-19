package com.auribisesmyplayschool.myplayschool.adminApp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.auribisesmyplayschool.myplayschool.Helper.Constants;
import com.auribisesmyplayschool.myplayschool.LoginActivity;
import com.auribisesmyplayschool.myplayschool.R;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.BranchBean;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.UserBean;
import com.auribisesmyplayschool.myplayschool.adminApp.classes.AdminUtil;
import com.auribisesmyplayschool.myplayschool.adminApp.fragment.CounsellorFragment;
import com.auribisesmyplayschool.myplayschool.adminApp.fragment.DriverFragment;
import com.auribisesmyplayschool.myplayschool.adminApp.fragment.InstructorFragment;
import com.auribisesmyplayschool.myplayschool.adminApp.fragment.MaidFragment;
import com.auribisesmyplayschool.myplayschool.adminApp.fragment.ManagerFragment;
import com.auribisesmyplayschool.myplayschool.bean.AdminBean;
import com.auribisesmyplayschool.myplayschool.classes.AttUtil;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

import dmax.dialog.SpotsDialog;

public class ManageBranchManagerActivity extends AppCompatActivity implements View.OnClickListener {
    public static ArrayList<UserBean> managerBeanArrayList, instrucBeanArrayList, counsellorBeanArrayList, driverBeanArrayList, maidBeanArrayList;
    private ArrayList<UserBean> userBeanArrayList;
    private UserBean userBean;
    private SharedPreferences preferences;
    AdminBean adminBean;
    ArrayList<String> branchNameArrayList;
    ArrayList<BranchBean> branchBeanArrayList;
    Intent rcv;

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager mViewPager;
    private CounsellorFragment counsellorFragment;
    private ManagerFragment managerFragment;
    private InstructorFragment instructorFragment;
    private DriverFragment driverFragment;
    private MaidFragment maidFragment;
    FirebaseFirestore db;
    ListenerRegistration registration;
    DocumentSnapshot lastVisible;
    int userId;
    SpotsDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_branch_manager);
        rcv = getIntent();
        adminBean = (AdminBean) rcv.getSerializableExtra(AdminUtil.TAG_ADMIN_BEAN);
        //branchNameArrayList = (ArrayList<String>) rcv.getSerializableExtra(AdminUtil.TAG_BRANCHARRAYLIST);
        branchBeanArrayList = (ArrayList<BranchBean>) rcv.getSerializableExtra(AdminUtil.TAG_BRANCHARRAYLIST);
        Toast.makeText(ManageBranchManagerActivity.this,""+branchBeanArrayList.size(),Toast.LENGTH_SHORT).show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = FirebaseFirestore.getInstance();
        preferences = getSharedPreferences(AttUtil.shpREG, Context.MODE_PRIVATE);
        counsellorFragment = new CounsellorFragment();
        if (preferences.getInt(AttUtil.shpLoginType, 0) == 1)
            managerFragment = new ManagerFragment();
        instructorFragment = new InstructorFragment();
        driverFragment = new DriverFragment();
        maidFragment = new MaidFragment();
        initViews();
        progressDialog = new SpotsDialog(ManageBranchManagerActivity.this, R.style.Custom);
        progressDialog.show();
        getUsers();
    }

    public void initViews(){
        managerBeanArrayList = new ArrayList<>();
        instrucBeanArrayList = new ArrayList<>();
        counsellorBeanArrayList = new ArrayList<>();
        driverBeanArrayList = new ArrayList<>();
        maidBeanArrayList = new ArrayList<>();

        userBeanArrayList = new ArrayList<>();
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        if (preferences.getInt(AttUtil.shpLoginType, 0) == 1)
            mViewPager.setOffscreenPageLimit(4);
        else
            mViewPager.setOffscreenPageLimit(3);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (preferences.getInt(AttUtil.shpLoginType, 0) == 1) {
                    if (position == 0)
                        getSupportActionBar().setTitle("Managers(" + managerBeanArrayList.size() + ")");
                    if (position == 1)
                        getSupportActionBar().setTitle("Counsellors(" + counsellorBeanArrayList.size() + ")");
                    if (position == 2)
                        getSupportActionBar().setTitle("Instructors(" + instrucBeanArrayList.size() + ")");
                    if (position == 3)
                        getSupportActionBar().setTitle("Drivers(" + driverBeanArrayList.size() + ")");
                    if (position == 4)
                        getSupportActionBar().setTitle("Maids(" + maidBeanArrayList.size() + ")");
                } else {
                    if (position == 0)
                        getSupportActionBar().setTitle("Counsellors(" + counsellorBeanArrayList.size() + ")");
                    if (position == 1)
                        getSupportActionBar().setTitle("Instructors(" + instrucBeanArrayList.size() + ")");
                    if (position == 2)
                        getSupportActionBar().setTitle("Drivers(" + driverBeanArrayList.size() + ")");
                    if (position == 3)
                        getSupportActionBar().setTitle("Maids(" + maidBeanArrayList.size() + ")");

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void getUsers(){

            registration =   db.collection(Constants.usersCollection).whereEqualTo("adminId",adminBean.getAdminId()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    lastVisible = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() -1);
                    userId =lastVisible.getLong("userId").intValue();
                    if(userBeanArrayList.size()>0){
                        userBeanArrayList.clear();
                        managerFragment.userListAdapter.notifyDataSetChanged();
                    }else{
                        for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                userBean = doc.getDocument().toObject(UserBean.class);
                                userBeanArrayList.add(userBean);
                                //userAdapter.notifyDataSetChanged();
                            }
                        }
                        progressDialog.dismiss();



                        if (userBeanArrayList.size() > 0 && userBeanArrayList != null){
                            for (int i = 0; i < userBeanArrayList.size(); i++) {
                                if (preferences.getInt(AttUtil.shpLoginType, 0) == 1) {
                                    if (userBeanArrayList.get(i).getUserType() == 1)
                                        managerBeanArrayList.add(userBeanArrayList.get(i));
                                }

                                if (userBeanArrayList.get(i).getUserType() == 2)
                                    counsellorBeanArrayList.add(userBeanArrayList.get(i));
                                if (userBeanArrayList.get(i).getUserType() == 3)
                                    instrucBeanArrayList.add(userBeanArrayList.get(i));
                                if (userBeanArrayList.get(i).getUserType() == 4)
                                    driverBeanArrayList.add(userBeanArrayList.get(i));
                                if (userBeanArrayList.get(i).getUserType() == 5)
                                    maidBeanArrayList.add(userBeanArrayList.get(i));
                            }
                            if (preferences.getInt(AttUtil.shpLoginType, 0) == 1) {
                                if (managerBeanArrayList.size() > 0)
                                    managerFragment.initlist();
                            }
                            if (counsellorBeanArrayList.size() > 0)
                                counsellorFragment.initlist();
                            if (instrucBeanArrayList.size() > 0)
                                instructorFragment.initlist();
                            if (driverBeanArrayList.size() > 0)
                                driverFragment.initlist();
                            if (maidBeanArrayList.size() > 0)
                                maidFragment.initlist();

                            getSupportActionBar().setTitle("Counsellor" + " (" + counsellorBeanArrayList.size() + ")");
                        }else {
                            getSupportActionBar().setTitle("Employee(0)");
                            Toast.makeText(ManageBranchManagerActivity.this, "No Employee(s)  found", Toast.LENGTH_LONG).show();
                        }
                    }

                }

            });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == AdminUtil.REQ_CODE) && (resultCode == 1)) {
            userBean = (UserBean) data.getSerializableExtra(AdminUtil.TAG_USER);
            if (userBeanArrayList.size() > 0 && userBeanArrayList != null) {
                if (userBean.getUserType() == 1) {
                    managerBeanArrayList.add(userBean);
                    userId = userBean.getUserId();
                    managerFragment.updateListFunction();
                }
                if (userBean.getUserType() == 2) {
                    counsellorBeanArrayList.add(userBean);
                    counsellorFragment.updateListFunction();
                }
                if (userBean.getUserType() == 3) {
                    instrucBeanArrayList.add(userBean);
                    instructorFragment.updateListFunction();
                }
                if (userBean.getUserType() == 4) {
                    driverBeanArrayList.add(userBean);
                    driverFragment.updateListFunction();
                }
                if (userBean.getUserType() == 5) {
                    maidBeanArrayList.add(userBean);
                    maidFragment.updateListFunction();
                }
            } else {
                if (userBean.getUserType() == 1) {
                    managerBeanArrayList.add(userBean);
                    managerFragment.updateListFunction();
                }
                if (userBean.getUserType() == 2) {
                    counsellorBeanArrayList.add(userBean);
                    counsellorFragment.updateListFunction();
                }
                if (userBean.getUserType() == 3) {
                    instrucBeanArrayList.add(userBean);
                   // instructorFragment.updateListFunction();
                }
                if (userBean.getUserType() == 4) {
                    driverBeanArrayList.add(userBean);
                    driverFragment.updateListFunction();
                }
                if (userBean.getUserType() == 5) {
                    maidBeanArrayList.add(userBean);
                    maidFragment.updateListFunction();
                }
            }
        }
        FloatingActionMenu menu = findViewById(R.id.branchManagerFabMenu);
        if (menu.isOpened()) {
            menu.close(true);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab1:
                Intent intent = new Intent(ManageBranchManagerActivity.this, AddUserActivity.class);
                intent.putExtra(AdminUtil.TAG_ADMIN_BEAN,adminBean);
                intent.putExtra(AdminUtil.TAG_BRANCHARRAYLIST,branchBeanArrayList);
                intent.putExtra("lastUserId",userId);
                startActivityForResult(intent, AdminUtil.REQ_CODE);
                break;

            default:
                break;
        }
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = null;
            if (preferences.getInt(AttUtil.shpLoginType, 0) == 1){
                switch (position) {
                    case 0:
                        f = managerFragment;
                        break;
                    case 1:
                        f = counsellorFragment;
                        break;
                    case 2:
                        f = instructorFragment;
                        break;
                    case 3:
                        f = driverFragment;
                        break;
                    case 4:
                        f = maidFragment;
                        break;
                }
            }else{
                switch (position) {
                    case 0:
                        f = counsellorFragment;
                        break;
                    case 1:
                        f = instructorFragment;
                        break;
                    case 2:
                        f = driverFragment;
                        break;
                    case 3:
                        f = maidFragment;
                        break;

                }
            }
            return f;
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            if (preferences.getInt(AttUtil.shpLoginType, 0) == 1)
                return 5;
            else
                return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (preferences.getInt(AttUtil.shpLoginType, 0) == 1) {
                switch (position) {
                    case 0:
                        return "Managers";
                    case 1:
                        return "Counsellors";
                    case 2:
                        return "Instructors";
                    case 3:
                        return "Drivers";
                    case 4:
                        return "Maids";
                }
            } else {
                switch (position) {
                    case 0:
                        return "Counsellors";
                    case 1:
                        return "Instructors";
                    case 2:
                        return "Drivers";
                    case 3:
                        return "Maids";
                }
            }
            return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        registration.remove();
    }
}
