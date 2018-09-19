package com.auribisesmyplayschool.myplayschool.adminApp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.auribisesmyplayschool.myplayschool.R;
import com.auribisesmyplayschool.myplayschool.adminApp.activity.ManageCourseActivity;
import com.auribisesmyplayschool.myplayschool.adminApp.bean.CourseBean;

import java.util.ArrayList;

public class CourseAdapter extends ArrayAdapter {
    Context context;
    ArrayList<CourseBean> list,searchlist,tmpList;
    CourseBean courseBean;
    int resource;
    public CourseAdapter(Context context, int resource, ArrayList<CourseBean> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
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
                if(searchlist.get(i).getCourseName().toLowerCase().contains(search.toLowerCase())){
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

        TextView courseId, courseName, courseFees;
        View view;

        //courseId = (TextView) myView.findViewById(R.id.courseId);
        courseName = (TextView) myView.findViewById(R.id.courseName);
        view = (View)myView.findViewById(R.id.view);

        courseBean = list.get(position);

        /*if (course.getcourseId() == 0)
            courseId.setText("NA");
        else
            courseId.setText(String.valueOf(course.getcourseId()));*/

        /*if(course.getCourseStatus() == 0){
            Log.i("branchst",String.valueOf(course.getCourseStatus()));
            view.setBackgroundColor(Color.parseColor("#ff0000"));
        } else {
            view.setBackgroundColor(Color.parseColor("#2196f3"));
        }*/

        if (courseBean.getCourseName() == null)
            courseName.setText("NA");
        else
            courseName.setText(courseBean.getCourseName());

        return myView;
    }

}
