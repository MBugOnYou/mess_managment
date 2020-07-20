package com.example.mealmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmanagement.R;
import com.example.mealmanagement.model.DepositAmount;
import com.example.mealmanagement.model.PreviousMonth;

import java.util.ArrayList;


public class CalculateMillRateAdapter extends RecyclerView.Adapter<CalculateMillRateAdapter.MyViewHolder> {

    private ArrayList<PreviousMonth> previousMonthArrayList;
    private onSelectedPlaceListener m_onSelectedPlaceListener;
    Context context;
    String YoutubeAPiKey;
    int isAddmember = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtname1,txtmealRate,txtDeposit,txtCost,txtExtra,txtGiven;


        public MyViewHolder(View view) {
            super(view);
            txtname1 = (TextView) view.findViewById(R.id.txtname1);
            txtmealRate = (TextView) view.findViewById(R.id.txtmealRate);
            txtDeposit = (TextView) view.findViewById(R.id.txtDeposit);

            txtCost = (TextView) view.findViewById(R.id.txtCost);
            txtExtra = (TextView) view.findViewById(R.id.txtExtra);
            txtGiven = (TextView) view.findViewById(R.id.txtGiven);

        }
    }


    public CalculateMillRateAdapter(ArrayList<PreviousMonth> previousMonthArrayList, Context context, onSelectedPlaceListener m_onSelectedPlaceListener) {
        this.previousMonthArrayList = previousMonthArrayList;
        this.context = context;
        this.m_onSelectedPlaceListener = m_onSelectedPlaceListener;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_calculate_millrate, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final PreviousMonth previousMonth = previousMonthArrayList.get(position);

        holder.txtname1.setText(previousMonth.getUser_id()+"");
        holder.txtmealRate.setText(previousMonth.getMeal_rate()+"");
        holder.txtDeposit.setText(previousMonth.getTotal_deposit()+"");

        holder.txtCost.setText(previousMonth.getTotal_cost()+"");
        holder.txtExtra.setText(previousMonth.getExtra_money()+"");
        holder.txtGiven.setText(previousMonth.getGiven_money()+"");



    }

    public void setData(ArrayList<PreviousMonth> lst) {
        this.previousMonthArrayList = lst;
    }

    @Override
    public int getItemCount() {
        return previousMonthArrayList.size();
    }

    public ArrayList<PreviousMonth> getData() {
        return this.previousMonthArrayList;
    }


    public static double toRad(double value) {
        return value * Math.PI / 180;
    }

    public interface onSelectedPlaceListener {
        void onClick(PreviousMonth place);
    }
}