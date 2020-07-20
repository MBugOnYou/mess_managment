package com.example.mealmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmanagement.R;
import com.example.mealmanagement.model.DailyCost;
import com.example.mealmanagement.model.DepositAmount;

import java.util.ArrayList;


public class DailyCostAdapter extends RecyclerView.Adapter<DailyCostAdapter.MyViewHolder> {

    private ArrayList<DailyCost> dailyCostArrayList;
    private onSelectedPlaceListener m_onSelectedPlaceListener;
    Context context;
    String YoutubeAPiKey;
    int isAddmember = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtamount,txtdate;


        public MyViewHolder(View view) {
            super(view);

            txtamount = (TextView) view.findViewById(R.id.txtamount);
            txtdate = (TextView) view.findViewById(R.id.txtdate);

        }
    }


    public DailyCostAdapter(ArrayList<DailyCost> dailyCostArrayList, Context context, onSelectedPlaceListener m_onSelectedPlaceListener) {
        this.dailyCostArrayList = dailyCostArrayList;
        this.context = context;
        this.m_onSelectedPlaceListener = m_onSelectedPlaceListener;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_daily_cost, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final DailyCost dailyCost = dailyCostArrayList.get(position);

        holder.txtamount.setText(dailyCost.getCost()+"");
        holder.txtdate.setText(dailyCost.getDate()+"");



    }

    public void setData(ArrayList<DailyCost> lst) {
        this.dailyCostArrayList = lst;
    }

    @Override
    public int getItemCount() {
        return dailyCostArrayList.size();
    }

    public ArrayList<DailyCost> getData() {
        return this.dailyCostArrayList;
    }


    public static double toRad(double value) {
        return value * Math.PI / 180;
    }

    public interface onSelectedPlaceListener {
        void onClick(DailyCost place);
    }
}