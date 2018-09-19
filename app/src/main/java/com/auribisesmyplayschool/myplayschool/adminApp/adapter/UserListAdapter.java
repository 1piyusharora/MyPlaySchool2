package com.auribisesmyplayschool.myplayschool.adminApp.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.auribisesmyplayschool.myplayschool.R;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.UserBean;

import java.util.ArrayList;

public class UserListAdapter extends ArrayAdapter {
    Context context;
    ArrayList<UserBean> list,searchlist,tmpList;
    UserBean userBean;
    int resource;
    public UserListAdapter(Context context, int resource, ArrayList<UserBean> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource =resource;
        list = objects;
        tmpList = new ArrayList<>();
        tmpList.addAll(list);
        searchlist=new ArrayList<>();
        searchlist.addAll(list);
    }
    public void filter(String search){

        if(search.isEmpty()){
            list.clear();
            list.addAll(searchlist);
        }else{
            list.clear();
            for(int i = 0; i < searchlist.size() ; i++ ){
                if(searchlist.get(i).getUserName().toLowerCase().contains(search.toLowerCase())){
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

        TextView branchManagerName,branchMName,branchMEmail,branchMContact,tvBranchName;
        View view;

        //branchId = (TextView) myView.findViewById(R.id.branchId);
        branchManagerName = (TextView) myView.findViewById(R.id.branchManagerName);
        //branchMName = (TextView) myView.findViewById(R.id.branchMName);
        branchMEmail = (TextView) myView.findViewById(R.id.branchMEmail);
        branchMContact = (TextView) myView.findViewById(R.id.branchMContact);
        //tvUserType = (TextView)myView.findViewById(R.id.tvUserType);
        tvBranchName = (TextView)myView.findViewById(R.id.tvBranchName);
        view = (View)myView.findViewById(R.id.view);

        userBean = list.get(position);


        if(userBean.getUserStatus() == 1){
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreen));
        } else {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorOrange));
        }

        if (userBean.getUserName() == null)
            branchManagerName.setText("N/A");
        else
            branchManagerName.setText(userBean.getUserName());

        if (userBean.getUserEmail() == null)
            branchMEmail.setText("N/A");
        else
            branchMEmail.setText(userBean.getUserEmail());

        if (userBean.getUserContact() == null)
            branchMContact.setText("N/A");
        else
            branchMContact.setText(userBean.getUserContact());

        if(userBean.getBranchId()>0)
            tvBranchName.setText(userBean.getBranchName());
        else
            tvBranchName.setText("N/A");

        return myView;
    }

}
