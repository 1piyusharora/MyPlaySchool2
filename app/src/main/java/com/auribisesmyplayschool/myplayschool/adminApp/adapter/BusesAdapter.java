package com.auribisesmyplayschool.myplayschool.adminApp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.auribisesmyplayschool.myplayschool.R;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.BusesBean;

import java.util.ArrayList;

/**
 * Created by White Wolf on 8/14/2018.
 */

public class BusesAdapter extends RecyclerView.Adapter<BusesAdapter.ViewHolder>{

    ArrayList<BusesBean> busesBeanArrayList , tempBusesList;
    Context context;

    public BusesAdapter( ArrayList<BusesBean> busesBeans , Context context)
    {
        busesBeanArrayList = busesBeans;
        this.context = context;
        tempBusesList = new ArrayList<>();
        tempBusesList.addAll(busesBeans);
    }

    @Override
    public BusesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_adapter_buses_listitem,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BusesAdapter.ViewHolder holder, int position)
    {
        holder.imageView.setImageResource(R.drawable.ic_bus);
        holder.activate.setText(String.valueOf(busesBeanArrayList.get(position).getActivate()));
        holder.route_bus_name.setText(busesBeanArrayList.get(position).getBusNumber());

    }

    @Override
    public int getItemCount()
    {
        return busesBeanArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView route_bus_name;
        TextView activate;
        ImageView imageView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            route_bus_name = (TextView)itemView.findViewById(R.id.route_bus_name);
            activate = (TextView) itemView.findViewById(R.id.route_bus_status);
            imageView = (ImageView)itemView.findViewById(R.id.image_view);

        }
    }


    public void filter(String str)
    {
        // Optimise Search Algo here
        busesBeanArrayList.clear();

        if(str.length()==0){
            busesBeanArrayList.addAll(tempBusesList);
        }else{
            for(BusesBean busesBean : tempBusesList){
                if(busesBean.getBusNumber().toLowerCase().contains(str.toLowerCase())
                        ||busesBean.getBusDesc().toLowerCase().contains(str.toLowerCase()))
                {
                    busesBeanArrayList.add(busesBean);
                }
            }
        }

        notifyDataSetChanged();

        // Optimise Search Algo here
        if (str.isEmpty())
        {
            busesBeanArrayList.clear();
            busesBeanArrayList.addAll(tempBusesList);
        }
        else
        {
            busesBeanArrayList.clear();
            for (int i = 0; i < tempBusesList.size(); i++)
            {
                String name = tempBusesList.get(i).getBusNumber();
                String desc= tempBusesList.get(i).getBusDesc();

                try
                {
                    name = String.valueOf(tempBusesList.get(i).getBusNumber());
                    desc= String.valueOf(tempBusesList.get(i).getBusDesc());;
                }
                catch(Exception e)
                {
                    name="";
                    desc="";
                }


                if (name.toLowerCase().contains(str.toLowerCase())||
                        desc.toLowerCase().contains(str.toLowerCase()))

                {
                    busesBeanArrayList.add(tempBusesList.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }


}
