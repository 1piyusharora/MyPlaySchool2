package com.auribisesmyplayschool.myplayschool.adminApp.activity;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.auribisesmyplayschool.myplayschool.Helper.Constants;
import com.auribisesmyplayschool.myplayschool.R;
import com.auribisesmyplayschool.myplayschool.adminApp.adapter.BranchListAdapter;
import com.auribisesmyplayschool.myplayschool.adminApp.adapter.RoutesAdapter;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.BranchBean;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.RoutesBean;
import com.auribisesmyplayschool.myplayschool.adminApp.classes.AdminUtil;
import com.auribisesmyplayschool.myplayschool.classes.MyResponseConnectivity;
import com.auribisesmyplayschool.myplayschool.classes.RecyclerItemClickListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONObject;

import java.util.ArrayList;

import javax.annotation.Nullable;

import static com.auribisesmyplayschool.myplayschool.R.id.listView;
import static com.auribisesmyplayschool.myplayschool.adminApp.activity.RouteBusesActivity.branch_id;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoutesFragment extends Fragment implements MyResponseConnectivity {


    static RoutesFragment fragment = new RoutesFragment();
    static Context context;

    static boolean updateMode = false;
    RecyclerView rvRoutes;
    View View , em_view;
    Dialog dialogProduct;
    EditText edRouteName;
    EditText edRouteDesc;
    RoutesBean routesBean;

    RoutesAdapter routesAdapter;
    RoutesBean posRouteBean;

    FirebaseFirestore db;
    ArrayList<RoutesBean> routesBeenArrayList;

    private int responseSignal=0 ,reqCode = 0;
    int pos_activate=0;
    int pos;

    public RoutesFragment() {
        // Required empty public constructor
    }


    public static RoutesFragment newInstance(Context context1)
    {
        context = context1;
        return fragment;
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View = inflater.inflate(R.layout.admin_fragment_routes, container, false);

        db=FirebaseFirestore.getInstance();

        initViews();





//        if(RouteBusesActivity.routesBeansArrayList.isEmpty())
//            empty_routes();


        rvRoutes.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                        new RecyclerItemClickListener.OnItemClickListener()
                        {
                            @Override
                            public void onItemClick(View view, int position)
                            {
                                pos =position;
                                showOptions();

//                        Toast.makeText(context,"on click",Toast.LENGTH_LONG).show();

                            }
                        })

        );

        return View;
    }

    void initViews()
    {
//        FloatingActionMenu menu =(FloatingActionMenu)View.findViewById(R.id.fab_route);
//        if(menu.isOpened())
//        {
//            menu.close(true);
//        }

        em_view = View.findViewById(R.id.em_routes);
        rvRoutes = View.findViewById(R.id.lvFragmentRoutes);

        // init recycler view
        rvRoutes.setLayoutManager(new LinearLayoutManager(rvRoutes.getContext()));
        rvRoutes.setItemAnimator(new DefaultItemAnimator());

    }

    void empty_routes()
    {
        em_view.setVisibility(View.VISIBLE);
        rvRoutes.setVisibility(View.GONE);
    }

    public void init_list()
    {

        Log.i("show","init list");
        routesAdapter = new RoutesAdapter(RouteBusesActivity.routesBeansArrayList,context);

        rvRoutes.setAdapter(routesAdapter);

        for (RoutesBean i : RouteBusesActivity.routesBeansArrayList)
        {
            Log.i("show",i.toString());
        }
    }

    public void showOptions()
    {
        String text;

        posRouteBean = RouteBusesActivity.routesBeansArrayList.get(pos);

        if(posRouteBean.getActivate()==0)
            text="Activate";
        else
            text="Deactivate";

        String[] options = {"View","Update",text};

        //        for (String i : options) {
//
//            Log.i("show", i.toString());
//        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setItems(options, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

                switch (which)
                {
                    case 0:
                        showRouteDialog();
                        break;
                    case 1:
                        updateMode = true;
                        showInsertDialogRoute();
                        break;
                    case 2:
                        if (AdminUtil.isNetworkConnected(getActivity()))
                            start_deactivate();
                        else
                            activateNetConnect();
                        break;
                }
            }
        });
        builder.create().show();



    }


    void showRouteDialog()
    {
        final Dialog dialogProduct;
        dialogProduct = new Dialog(getActivity());
        dialogProduct.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogProduct.setContentView(R.layout.admin_dialog_show_routes_info);
        dialogProduct.show();

        TextView txtProductCode = (TextView)dialogProduct.findViewById(R.id.productCode);
        TextView txtProductCategory= (TextView)dialogProduct.findViewById(R.id.productCategory);
        TextView txtProductDesc = (TextView)dialogProduct.findViewById(R.id.productDesc);
        TextView txtClose = (TextView)dialogProduct.findViewById(R.id.txtClose);

        txtProductCode.setText(String.valueOf(posRouteBean.getRouteId()));
        txtProductCategory.setText(posRouteBean.getRouteName());
        txtProductDesc.setText(posRouteBean.getRouteDesc());

        txtClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                dialogProduct.dismiss();
            }
        });
    }

    void start_deactivate()
    {
        // firestore

        AdminUtil.progressDialog(getActivity());
        AdminUtil.pd(1);

        if(posRouteBean.getActivate()==0)
        {
            db.collection(Constants.routesCollection).document(String.valueOf(posRouteBean.getRouteId()))
                    .update("activate",1)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(getContext(),"Activation Successfull",Toast.LENGTH_LONG).show();
                            //Toast.makeText(getContext(),String.valueOf(posRouteBean.getRouteId()),Toast.LENGTH_LONG).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),"Activation Failed",Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(),String.valueOf(posRouteBean.getRouteId()),Toast.LENGTH_LONG).show();
                }
            });
        }
        else
        {
            db.collection(Constants.routesCollection).document(String.valueOf(posRouteBean.getRouteId()))
                    .update("activate",0)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(getContext(),"Update Successfull",Toast.LENGTH_LONG).show();
                            //Toast.makeText(getContext(),String.valueOf(posRouteBean.getRouteId()),Toast.LENGTH_LONG).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),"Update Failed",Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(),String.valueOf(posRouteBean.getRouteId()),Toast.LENGTH_LONG).show();
                }
            });
        }

        AdminUtil.pd(0);
        routesAdapter.notifyDataSetChanged();
    }

    void activateNetConnect()
    {
        reqCode = 1;
        AdminUtil utility = AdminUtil.getMyUtil();
        utility.showMsg(getActivity());
    }


    @Override
    public void onMyResponseConnectivity(int i)
    {
        if(reqCode==1)
            activateResNetConnect(i);
        if(reqCode==2)
            resendResNetConnect(i);

    }

    void resendResNetConnect(int i){

        if (i == 0)
        {
            resendNetConnect();
        }
    }


    void activateResNetConnect(int i)
    {
        if(i == 1)
        {
            start_deactivate();
        }else if (i == 0){
            activateNetConnect();
        }
    }
    void resendNetConnect()
    {
        reqCode = 2;
        AdminUtil utility = AdminUtil.getMyUtil();
        utility.showMsg(getActivity());
    }



    public  void showInsertDialogRoute()
    {

        try {
            Log.i("test","1");
            dialogProduct = new Dialog(getActivity());
            Log.i("test","2");
            dialogProduct.requestWindowFeature(Window.FEATURE_NO_TITLE);

            dialogProduct.setContentView(R.layout.admin_dialog_insert_route);
            dialogProduct.show();

            edRouteName= (EditText)dialogProduct.findViewById(R.id.ed_route_name);
            edRouteDesc = (EditText)dialogProduct.findViewById(R.id.ed_route_desc);
            Button btnUpdate = (Button)dialogProduct.findViewById(R.id.btn_insert_route);

            routesBean = new RoutesBean();

            if(updateMode)
            {
                btnUpdate.setText("Update");
                edRouteName.setText(posRouteBean.getRouteName());
                edRouteDesc.setText(posRouteBean.getRouteDesc());
            }

            btnUpdate.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(validation())
                    {
                        routesBean.setRouteName(edRouteName.getText().toString());
                        routesBean.setRouteDesc(edRouteDesc.getText().toString());
                        routesBean.setBranchId(branch_id);
                        routesBean.setRouteId(RouteBusesActivity.docId1+1);
                        if(updateMode)
                            routesBean.setActivate(routesBean.getActivate());
                        else
                            routesBean.setActivate(1);

                        insertUpdateRoute();
                    }

                }
            });
        }catch (Exception e){
            Log.i("test",e.toString());
        }

    }

    void insertUpdateRoute()
    {
        if(updateMode)
        {
            db.collection(Constants.routesCollection).document(String.valueOf(posRouteBean.getRouteId()))
                    .update("routeName",edRouteName.getText().toString().trim(), "routeDesc",edRouteDesc.getText().toString().trim())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(getContext(),"Update Successfull",Toast.LENGTH_LONG).show();
                dialogProduct.dismiss();

        }
        }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(getContext(),"Update Failed",Toast.LENGTH_LONG).show();
            dialogProduct.dismiss();
        }
    });

        }

        else {
            db.collection(Constants.routesCollection).document(String.valueOf(RouteBusesActivity.docId1 + 1)).set(routesBean)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(getContext(), "Insertion Successfull", Toast.LENGTH_LONG).show();
                            dialogProduct.dismiss();
                            routesAdapter.notifyDataSetChanged();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Insertion Failed", Toast.LENGTH_LONG).show();
                    dialogProduct.dismiss();
                }
            });

        }
    }


    boolean validation()
    {
        boolean flag = true;

        if(edRouteName.getText().toString().isEmpty())
        {   flag=false;
            edRouteName.setError("Enter Route Name");

        }
        if(edRouteDesc.getText().toString().isEmpty())
        {
            flag=false;
            edRouteDesc.setError("Enter Route Description");

        }

        return flag;
    }

}
