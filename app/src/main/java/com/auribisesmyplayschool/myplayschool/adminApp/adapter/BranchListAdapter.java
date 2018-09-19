package com.auribisesmyplayschool.myplayschool.adminApp.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.auribisesmyplayschool.myplayschool.R;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.BranchBean;

import java.util.ArrayList;

public class BranchListAdapter extends ArrayAdapter {
    Context context;
    ArrayList<BranchBean> list,searchlist,tmpList;
    BranchBean branchBean;
    int resource;
    public BranchListAdapter(Context context, int resource, ArrayList<BranchBean> objects) {
        super(context, resource, objects);
        this.context = context;
        list = objects;
        tmpList = new ArrayList<>();
        tmpList.addAll(list);
        searchlist=new ArrayList<>();
        searchlist.addAll(list);
        this.resource=resource;
    }
    public void filter(String search){

        if(search.isEmpty()){
            list.clear();
            list.addAll(searchlist);
        }else{
            list.clear();
            for(int i = 0; i < searchlist.size() ; i++ ){
                if(searchlist.get(i).getBranchName().toLowerCase().contains(search.toLowerCase())){
                    list.add(searchlist.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View myView = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myView = inflater.inflate(resource, parent, false);

        TextView branchName,branchAddress,branchContact,branchUserName;
        View view;

        //branchId = (TextView) myView.findViewById(R.id.branchId);
        branchName = (TextView) myView.findViewById(R.id.branchName);
        branchAddress = (TextView) myView.findViewById(R.id.branchAddress);
        branchContact = (TextView) myView.findViewById(R.id.branchContact);
        branchUserName = (TextView) myView.findViewById(R.id.branchUserName);
        view = (View)myView.findViewById(R.id.view);

        branchBean = list.get(position);
        if(branchBean.getBranchStatus() == 0){
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorOrange));
        } else {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreen));
        }

        if (branchBean.getBranchName() == null)
            branchName.setText("NA");
        else
            branchName.setText(branchBean.getBranchName());

        if (branchBean.getBranchContact() == null)
            branchContact.setText("NA");
        else
            branchContact.setText(branchBean.getBranchContact());

        if (branchBean.getBranchAddress() == null)
            branchAddress.setText("NA");
        else
            branchAddress.setText(branchBean.getBranchAddress());

        if (branchBean.getUserName() == null)
            branchUserName.setText("NA");
        else
            branchUserName.setText(branchBean.getUserName());

        return myView;
    }
}
