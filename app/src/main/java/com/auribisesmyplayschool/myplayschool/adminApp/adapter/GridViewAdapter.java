package com.auribisesmyplayschool.myplayschool.adminApp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.auribisesmyplayschool.myplayschool.R;

public class GridViewAdapter extends BaseAdapter {

    String nameList[];
    int img[];
    Context context;

    public GridViewAdapter(Context context, String[] nameList, int[] img){
        this.nameList=nameList;
        this.img=img;
        this.context=context;
    }

    @Override
    public int getCount() {
        return nameList.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView;
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView=inflater.inflate(R.layout.admin_grid_item_layout,null);
        ImageView imgs=(ImageView)rowView.findViewById(R.id.imageViewItem);
        TextView txt=(TextView)rowView.findViewById(R.id.textViewItem);
        /*if(position==8)
            txt.setText(R.string.app_name);
        else*/
        txt.setText(nameList[position]);
        imgs.setImageResource(img[position]);
        /*rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:
                        context.startActivity(new Intent(context,ManageBranchActivity.class));
                        break;
                    case 1:
                        context.startActivity(new Intent(context,ManageCourseActivity.class));
                        break;
                    case 2:
                        context.startActivity(new Intent(context,ManageBranchManagerActivity.class));
                        break;
                    case 3:
                        context.startActivity(new Intent(context,CollectionGraphActivity.class));
                        break;
                    case 4:
                        context.startActivity(new Intent(context,ChangeProfileActivity.class));
                        break;
                    case 5:
                        context.startActivity(new Intent(context,EnquiryGraphActivity.class));
                        break;
                    case 6:
                        context.startActivity(new Intent(context,AdmissionGraphActivity.class));
                        break;
                    case 7:
                        context.startActivity(new Intent(context,DigitalMessageActivity.class));
                        break;
                    case 8:
                        context.startActivity(new Intent(context,AdminAboutUsActivity.class));
                        break;
                }
            }
        });*/
        return rowView;
    }

}
