package com.example.mealmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmanagement.R;
import com.example.mealmanagement.model.DailyMeal;
import com.example.mealmanagement.model.DepositAmount;

import java.util.ArrayList;


public class ActivityTotalMealAdapter extends RecyclerView.Adapter<ActivityTotalMealAdapter.MyViewHolder> {

    private ArrayList<DailyMeal> dailyMealArrayList;
    private onSelectedPlaceListener m_onSelectedPlaceListener;
    Context context;
    String YoutubeAPiKey;
    int isAddmember = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtCreationDate,txttotalmeal,txtname;


        public MyViewHolder(View view) {
            super(view);
            txtCreationDate = (TextView) view.findViewById(R.id.txtCreationDate);
            txttotalmeal = (TextView) view.findViewById(R.id.txttotalmeal);
            txtname = (TextView) view.findViewById(R.id.txtname);

        }
    }


    public ActivityTotalMealAdapter(ArrayList<DailyMeal> dailyMealArrayList, Context context, onSelectedPlaceListener m_onSelectedPlaceListener) {
        this.dailyMealArrayList = dailyMealArrayList;
        this.context = context;
        this.m_onSelectedPlaceListener = m_onSelectedPlaceListener;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_activity_total_meal, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final DailyMeal dailyMeal = dailyMealArrayList.get(position);

        holder.txtCreationDate.setText(dailyMeal.getCreation_date()+"");
        holder.txttotalmeal.setText(dailyMeal.getTotal_meal()+"");
        holder.txtname.setText(dailyMeal.getName()+"");


    }

    public void setData(ArrayList<DailyMeal> lst) {
        this.dailyMealArrayList = lst;
    }

    @Override
    public int getItemCount() {
        return dailyMealArrayList.size();
    }

    public ArrayList<DailyMeal> getData() {
        return this.dailyMealArrayList;
    }


    public static double toRad(double value) {
        return value * Math.PI / 180;
    }

    public interface onSelectedPlaceListener {
        void onClick(DailyMeal place);
    }
}