package com.auribisesmyplayschool.myplayschool.adminApp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.auribisesmyplayschool.myplayschool.R;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.RoutesBean;

import java.util.ArrayList;

/**
 * Created by White Wolf on 8/14/2018.
 */

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.ViewHolder> {

    ArrayList<RoutesBean> routesBeanArrayList,tempRoutesList;
    Context context;

    public RoutesAdapter(ArrayList<RoutesBean> routesBeanArrayList , Context context)
    {
        this.routesBeanArrayList =routesBeanArrayList;
        this.context = context;
        tempRoutesList = new ArrayList<>();
        tempRoutesList.addAll(routesBeanArrayList);
    }

    @Override
    public RoutesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_adapter_routes_listitem,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RoutesAdapter.ViewHolder holder, int position)
    {
        holder.imageView.setImageResource(R.drawable.ic_route1);
        holder.activate.setText(String.valueOf(routesBeanArrayList.get(position).getActivate()));
        holder.route_bus_name.setText(routesBeanArrayList.get(position).getRouteName());
    }

    @Override
    public int getItemCount()
    {
        return routesBeanArrayList.size();
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
        if (str.isEmpty())
        {
            routesBeanArrayList.clear();
            routesBeanArrayList.addAll(tempRoutesList);
        }
        else
        {
            routesBeanArrayList.clear();
            for (int i = 0; i < tempRoutesList.size(); i++)
            {
                String name = tempRoutesList.get(i).getRouteName();
                String desc= tempRoutesList.get(i).getRouteDesc();

                try
                {
                    name = String.valueOf(tempRoutesList.get(i).getRouteName());
                    desc= String.valueOf(tempRoutesList.get(i).getRouteDesc());;
                }
                catch(Exception e)
                {
                    name="";
                    desc="";
                }


                if (name.toLowerCase().contains(str.toLowerCase())||
                        desc.toLowerCase().contains(str.toLowerCase()))

                {
                    routesBeanArrayList.add(tempRoutesList.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }
}
