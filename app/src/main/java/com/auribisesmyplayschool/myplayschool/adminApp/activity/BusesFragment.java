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
import com.auribisesmyplayschool.myplayschool.adminApp.adapter.BusesAdapter;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.BusesBean;
import com.auribisesmyplayschool.myplayschool.adminApp.classes.AdminUtil;
import com.auribisesmyplayschool.myplayschool.classes.MyResponseConnectivity;
import com.auribisesmyplayschool.myplayschool.classes.RecyclerItemClickListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.auribisesmyplayschool.myplayschool.adminApp.activity.RouteBusesActivity.branch_id;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusesFragment extends Fragment implements MyResponseConnectivity {

    static BusesFragment fragment = new BusesFragment();
    static Context context;

    RecyclerView rvBuses;
    View view , em_view;

    FirebaseFirestore db;
    BusesAdapter busesAdapter;
    BusesBean posBusBean;

    Dialog dialogProduct;
    boolean updateMode = false;
    EditText edBusName;
    EditText edBusDesc;
    BusesBean busesBean;


    private int responseSignal=0 ,reqCode = 0;
    int pos ,pos_activate =1;;



    public BusesFragment()
    {
        // Required empty public constructor
    }

    public static BusesFragment newInstance(Context context1)
    {
        context = context1;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.admin_fragment_buses, container, false);

        db=FirebaseFirestore.getInstance();

        initViews();

//        if(RouteBusesActivity.busesBeansArrayList.isEmpty())
//            empty_buses();


        rvBuses.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
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



        return  view;

    }

    void initViews()
    {
//        FloatingActionMenu menu =(FloatingActionMenu)view.findViewById(R.id.fab_bus);
//        if(menu.isOpened()){
//            menu.close(true);
//        }

        em_view = view.findViewById(R.id.em_bus);
        rvBuses = view.findViewById(R.id.lvFragmentBuses);

        // init recycler view
        rvBuses.setLayoutManager(new LinearLayoutManager(rvBuses.getContext()));
        rvBuses.setItemAnimator(new DefaultItemAnimator());

    }

    public void showOptions()
    {
        posBusBean = RouteBusesActivity.busesBeansArrayList.get(pos);

        AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
        String text;

        if(posBusBean.getActivate()==0)
            text="Activate";
        else
            text="Deactivate";

        String[] options = {"View","Update",text};

//        for (String i : options) {
//
//            Log.i("show", i.toString());
//        }


        build.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case 0:
                        showBusDialog();
                        break;

                    case 1:
                        updateMode = true;
                        showInsertDialogBus();
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

        build.create().show();
    }

    public void showBusDialog()
    {
        final Dialog dialogProduct;
        dialogProduct = new Dialog(getActivity());
        dialogProduct.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogProduct.setContentView(R.layout.admin_dialog_show_buses_info);
        dialogProduct.show();


        TextView txtProductCode = (TextView)dialogProduct.findViewById(R.id.productCode);
        TextView txtProductCategory= (TextView)dialogProduct.findViewById(R.id.productCategory);
        TextView txtProductDesc = (TextView)dialogProduct.findViewById(R.id.productDesc);
        TextView txtClose = (TextView)dialogProduct.findViewById(R.id.txtClose);

        txtProductCode.setText(String.valueOf(posBusBean.getBusId()));
        txtProductCategory.setText(posBusBean.getBusNumber());
        txtProductDesc.setText(posBusBean.getBusDesc());

        txtClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                dialogProduct.dismiss();
            }
        });
    }

    public void init_list()
    {
        Log.i("show","init list");


        busesAdapter = new BusesAdapter(RouteBusesActivity.busesBeansArrayList,context);
        rvBuses.setAdapter(busesAdapter);

//        for (BusesBean i : RouteBusesActivity.busesBeansArrayList)
//        {
//            Log.i("show",i.toString());
//        }
    }

    public  void showInsertDialogBus()
    {
        dialogProduct = new Dialog(getActivity());
        dialogProduct.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogProduct.setContentView(R.layout.admin_dialog_insert_bus);
        dialogProduct.show();

        edBusName = dialogProduct.findViewById(R.id.ed_bus_number);
        edBusDesc = (EditText)dialogProduct.findViewById(R.id.ed_bus_description);
        Button btnUpdate = (Button)dialogProduct.findViewById(R.id.btn_insert_bus);

        busesBean = new BusesBean();

        if(updateMode)
        {
            btnUpdate.setText("Update");
            edBusName.setText(posBusBean.getBusNumber());
            edBusDesc.setText(posBusBean.getBusDesc());
        }



        btnUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(validation())
                {
                    busesBean.setBusNumber(edBusName.getText().toString());
                    busesBean.setBusDesc(edBusDesc.getText().toString());
                    busesBean.setBranchId(branch_id);
                    busesBean.setRouteId(RouteBusesActivity.docId2 + 1);
                    if(updateMode)
                        busesBean.setActivate(posBusBean.getActivate());
                    else
                        busesBean.setActivate(1);
                    insertUpdateBus();
                }

            }
        });

    }

    void insertUpdateBus(){

        if(updateMode)
        {
            db.collection(Constants.busesCollection).document(String.valueOf(posBusBean.getRouteId()))
                    .update("routeName",edBusName.getText().toString().trim(), "routeDesc",edBusDesc.getText().toString().trim())
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
            db.collection(Constants.busesCollection).document(String.valueOf(RouteBusesActivity.docId2 + 1)).set(busesBean)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(getContext(), "Insertion Successfull", Toast.LENGTH_LONG).show();
                            dialogProduct.dismiss();
                            busesAdapter.notifyDataSetChanged();
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

    @Override
    public void onMyResponseConnectivity(int i) {

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

    void activateResNetConnect(int i){
        if(i == 1){
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

    void activateNetConnect()
    {
        reqCode = 1;
        AdminUtil utility = AdminUtil.getMyUtil();
        utility.showMsg(getActivity());
    }

    void start_deactivate(){
        // firestore

        AdminUtil.progressDialog(getActivity());
        AdminUtil.pd(1);

        if(posBusBean.getActivate()==0)
        {
            db.collection(Constants.busesCollection).document(String.valueOf(posBusBean.getBusId()))
                    .update("activate",1)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(getContext(),"Activation Successfull",Toast.LENGTH_LONG).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),"Activation Failed",Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(),String.valueOf(posBusBean.getRouteId()),Toast.LENGTH_LONG).show();
                }
            });
        }
        else
        {
            db.collection(Constants.busesCollection).document(String.valueOf(posBusBean.getBusId()))
                    .update("activate",0)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(getContext(),"Deactivaion Successfull",Toast.LENGTH_LONG).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),"Deactivation Failed",Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(),String.valueOf(posBusBean.getRouteId()),Toast.LENGTH_LONG).show();
                }
            });
        }

        AdminUtil.pd(0);
        busesAdapter.notifyDataSetChanged();
    }

    boolean validation()
    {
        boolean flag = true;

        if(edBusName.getText().toString().isEmpty())
        {
            edBusName.setError("Enter Bus Number");
            flag=false;
        }
        if(edBusDesc.getText().toString().isEmpty())
        {
            edBusDesc.setError("Enter Bus Description");
            flag=false;
        }

        return flag;
    }
}
