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
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.auribisesmyplayschool.myplayschool.Helper.Constants;
import com.auribisesmyplayschool.myplayschool.R;
import com.auribisesmyplayschool.myplayschool.adminApp.adapter.BusesAdapter;
import com.auribisesmyplayschool.myplayschool.adminApp.adapter.RoutesAdapter;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.BusesBean;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.RoutesBean;
import com.auribisesmyplayschool.myplayschool.adminApp.classes.AdminUtil;
import com.auribisesmyplayschool.myplayschool.classes.AttUtil;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class RouteBusesActivity extends AppCompatActivity {

    //ListView listView;

    FirebaseFirestore db1,db2;

    RoutesBean routesBean;
    RoutesAdapter routesAdapter;

    BusesBean busesBean;
    BusesAdapter busesAdapter;

    public static ArrayList<BusesBean> busesBeansArrayList;
    public static ArrayList<RoutesBean> routesBeansArrayList;

    RecyclerView rvRoutes;
    RecyclerView rvBuses;

    boolean isRoute=true;
    static public int docId1,docId2;

//    private ArrayList<UserBean> userBeanArrayList;
//    private UserBean userBean;

    private int reqCode = 0;
    private SharedPreferences prefs;
    private int responseSignal=0;

    private RouteBusesActivity.SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager mViewPager;
    private BusesFragment busesFragment;
    private RoutesFragment routesFragment;

    ListenerRegistration listenerRegistration;

    public static int branch_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_route_buses);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db1=FirebaseFirestore.getInstance();
        db2=FirebaseFirestore.getInstance();

        busesFragment = BusesFragment.newInstance(getApplicationContext());
        routesFragment = RoutesFragment.newInstance(getApplicationContext());

        Intent rcv = getIntent();
        branch_id = rcv.getIntExtra("branchId",0);

        prefs = getSharedPreferences(AttUtil.shpREG, Context.MODE_PRIVATE);

        initViews();
        get_branch_manager();

        if (AdminUtil.isNetworkConnected(this)){
            //routesBeansArrayList.clear();

        }

        else
            retrieveNetConnect();
    }

    public void initViews()
    {

        FloatingActionMenu menu =(FloatingActionMenu)findViewById(R.id.fab_bus_route);
        if(menu.isOpened())
        {
            menu.close(true);
        }

        busesBeansArrayList = new ArrayList<>();

        routesBeansArrayList = new ArrayList<>();


        sectionsPagerAdapter = new RouteBusesActivity .SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0)
                {   isRoute=true;
                    getSupportActionBar().setTitle("Routes("+routesBeansArrayList.size()+")");}
                if(position==1)
                {   isRoute=false;
                    getSupportActionBar().setTitle("Buses("+busesBeansArrayList.size()+")");}


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    void get_branch_manager()
    {
        //firestore

        db1.collection(Constants.routesCollection).whereEqualTo("branchId",branch_id).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                            //size = queryDocumentSnapshots.size();
                            routesBean = doc.getDocument().toObject(RoutesBean.class);
                            docId1=routesBean.getRouteId();
                            routesBeansArrayList.add(routesBean);
                            routesAdapter = new RoutesAdapter(routesBeansArrayList,RouteBusesActivity.this);
                            routesFragment.init_list();
                            routesAdapter.notifyDataSetChanged();
                           // getSupportActionBar().setTitle("Routes("+routesBeansArrayList.size()+")");
                    }else {
                       // getSupportActionBar().setTitle("Routes(0)");
                        //Toast.makeText(RouteBusesActivity.this, "No Route(s) found", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        db2.collection(Constants.busesCollection).whereEqualTo("branchId",branch_id).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            //size = queryDocumentSnapshots.size();
                            busesBean = doc.getDocument().toObject(BusesBean.class);

                            docId2=busesBean.getBusId();

                            busesBeansArrayList.add(busesBean);
                            busesAdapter = new BusesAdapter(busesBeansArrayList,RouteBusesActivity.this);
                            busesFragment.init_list();
                            busesAdapter.notifyDataSetChanged();
                           // getSupportActionBar().setTitle("Buses("+busesBeansArrayList.size()+")");
                        }else {
                           // getSupportActionBar().setTitle("Buses(0)");
                           // Toast.makeText(RouteBusesActivity.this, "No Bus(es) found", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

    }

    void retrieveNetConnect()
    {
        reqCode = 2;
        AdminUtil utility = AdminUtil.getMyUtil();
        utility.showMsg(RouteBusesActivity.this);
    }

    void retrieveResNetConnect(int i)
    {
        if(i == 1){
           // get_branch_manager();
        }else if (i == 0){
            retrieveNetConnect();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            Fragment f = null;

            switch (position)
            {
                case 0:
                    f = routesFragment;
                    break;
                case 1:
                    f = busesFragment;
                    break;


            }
            return f;
        }

        @Override
        public int getCount()
        {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Routes";

                case 1:
                    return "Buses";

            }
            return null;
        }

    }

    public void onClick(View view)
    {
        int id = view.getId();
        Log.i("test",""+id);
        if(id==R.id.fab1)
        {
            if(isRoute){
                RoutesFragment.updateMode=false;
                routesFragment.showInsertDialogRoute();
            }
            else{
              //  BusesFragment.updateMode=false;
                busesFragment.showInsertDialogBus();
        }

        }

    }

}
