package com.auribisesmyplayschool.myplayschool.adminApp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.auribisesmyplayschool.myplayschool.R;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.CategoryBean;

import java.util.ArrayList;

/**
 * Created by kshitij on 30/05/17.
 */

public class CategoryAdapter extends ArrayAdapter {
    private ArrayList<CategoryBean> categoryBeanArrayList;
    private Context context;
    private int resource, useCase;
    //useCase-1: Displaying Categories
    //useCase-2: Adding Fee Heads

    public CategoryAdapter(Context context, int resource, ArrayList<CategoryBean> categoryBeanArrayList, int useCase) {
        super(context, resource, categoryBeanArrayList);
        this.categoryBeanArrayList = categoryBeanArrayList;
        this.context = context;
        this.resource = resource;
        this.useCase = useCase;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(resource, null);
        TextView txtName = (TextView) rowView.findViewById(R.id.txtName);
        txtName.setText(categoryBeanArrayList.get(position).getCategoryName());
        if (useCase == 2) {
            LinearLayout linearLayout =  rowView.findViewById(R.id.linearEditTexts);
            final EditText editTextCost = new EditText(context);
            editTextCost.setHint(categoryBeanArrayList.get(position).getCategoryName() + " Price");
            editTextCost.setInputType(InputType.TYPE_CLASS_NUMBER);
            editTextCost.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            editTextCost.setText("" + categoryBeanArrayList.get(position).getCost());
            linearLayout.addView(editTextCost);
            editTextCost.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {

                        if (editTextCost.getText().toString().equals("")) {
                            editTextCost.setText("0");
                            categoryBeanArrayList.get(position).setCost(0);
                        } else {
                            try {
                                categoryBeanArrayList.get(position).setCost(Integer.parseInt(editTextCost.getText().toString()));
                            } catch (Exception e) {
                                Log.i("test", e.toString());
                            }
                        }
                    } catch (Exception e) {
                        Log.i("test", e.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
        return rowView;
    }
}
