package com.auribisesmyplayschool.myplayschool.adminApp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.auribisesmyplayschool.myplayschool.R;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.FeeCostBean;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.FeeHeadBean;

import java.util.ArrayList;

/**
 * Created by White Wolf on 8/29/2018.
 */

public class FeeHeadAdapter extends ArrayAdapter {
    private Context context;
    private int resource;
    private ArrayList<FeeHeadBean> feeHeadBeanArrayList;
    private ArrayList<String> feeTypeList = new ArrayList<>();

    public FeeHeadAdapter(Context context, int resource, ArrayList<FeeHeadBean> feeHeadBeanArrayList) {
        super(context, resource, feeHeadBeanArrayList);
        this.context = context;
        this.resource = resource;
        this.feeHeadBeanArrayList = feeHeadBeanArrayList;

        feeTypeList.add("--Select a fee type--");
        feeTypeList.add("One Time");
        feeTypeList.add("Annual");
        feeTypeList.add("Monthly");
        feeTypeList.add("Transportation");
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(resource, null);
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, feeTypeList);
        final LinearLayout linearLayout = rowView.findViewById(R.id.linearLayoutCategories);
        final FeeHeadBean feeHeadBean = feeHeadBeanArrayList.get(position);
        Log.i("test", feeHeadBeanArrayList.get(position).toString());
        final EditText editText = (EditText) rowView.findViewById(R.id.editTextName);
        Spinner spinner = (Spinner) rowView.findViewById(R.id.spinnerFeeType);
        spinner.setAdapter(adapter);
        int feeType = feeHeadBean.getFeeType();
        spinner.setSelection(feeType);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos > 0) {
                    feeHeadBeanArrayList.get(position).setFeeType(pos);
                    if (feeHeadBean.getFeeType()==4)
                        linearLayout.setVisibility(View.GONE);
                    else
                        linearLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editText.setText(feeHeadBean.getHeadName());
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editText.getText().toString().equals("")) {
                    editText.setError("Head name cannot be empty!");
                } else {
                    feeHeadBeanArrayList.get(position).setHeadName(editText.getText().toString().trim());
                }
            }
        });

        final ArrayList<FeeCostBean> feeCostBeanArrayList = feeHeadBean.getFeeCostBeanArrayList();

        if (feeHeadBean.getFeeType() != 4) {
            linearLayout.setVisibility(View.GONE);
        }

        for (int i = 0; i < feeCostBeanArrayList.size(); i++) {
//            Log.i("test",feeHeadBeanArrayList.get(i).toString());
            final int index = i;
            TextView textView = new TextView(context);
            textView.setText(feeCostBeanArrayList.get(i).getCategoryName());
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            final EditText editTextCost = new EditText(context);
            editTextCost.setInputType(InputType.TYPE_CLASS_NUMBER);
            editTextCost.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            editTextCost.setText("" + feeCostBeanArrayList.get(i).getCost());
            linearLayout.addView(textView);
            linearLayout.addView(editTextCost);
           // editTextCost.setSelection(index);
            editTextCost.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int cost;
                    if (editTextCost.getText().toString().equals("")) {
                        cost = 0;
                        editTextCost.setText("0");
                        feeCostBeanArrayList.get(index).setCost(cost);
                    } else {
                        try {
                            cost = Integer.parseInt(editTextCost.getText().toString());
                            feeCostBeanArrayList.get(index).setCost(cost);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            //}
        }
        return rowView;
    }
}


