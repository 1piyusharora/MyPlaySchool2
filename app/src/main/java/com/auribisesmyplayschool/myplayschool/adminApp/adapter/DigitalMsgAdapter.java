package com.auribisesmyplayschool.myplayschool.adminApp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.auribisesmyplayschool.myplayschool.R;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.MessageBean;

import java.util.ArrayList;

/**
 * Created by White Wolf on 8/13/2018.
 */

public class DigitalMsgAdapter extends ArrayAdapter {
    Context context;
    ArrayList<MessageBean> messageList;

    public DigitalMsgAdapter(Context context, int resource, ArrayList<MessageBean> messageList) {
        super(context, resource, messageList);
        this.context=context;
        this.messageList=messageList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.admin_adapter_digital_msg,null);
        TextView msg=(TextView)rowView.findViewById(R.id.textViewMsg);
        TextView date=(TextView)rowView.findViewById(R.id.textViewDate);
        msg.setText(messageList.get(position).getMessage().toString().replace("$&*","'"));
        date.setText(messageList.get(position).getDate());
        return rowView;
    }
}
