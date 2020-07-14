package com.example.mealmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmanagement.R;
import com.example.mealmanagement.model.DepositAmount;
import com.example.mealmanagement.model.UserInfo;

import java.util.ArrayList;


public class DepositMoneyAdapter1 extends RecyclerView.Adapter<DepositMoneyAdapter1.MyViewHolder> {

    private ArrayList<DepositAmount> placelist;
    private onSelectedPlaceListener m_onSelectedPlaceListener;
    Context context;
    String YoutubeAPiKey;
    int isAddmember = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtname1,txtamount;


        public MyViewHolder(View view) {
            super(view);
            txtname1 = (TextView) view.findViewById(R.id.txtname1);
            txtamount = (TextView) view.findViewById(R.id.txtamount);

        }
    }


    public DepositMoneyAdapter1( ArrayList<DepositAmount> placelist, Context context, onSelectedPlaceListener m_onSelectedPlaceListener) {
        this.placelist = placelist;
        this.context = context;
        this.m_onSelectedPlaceListener = m_onSelectedPlaceListener;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_deposit_money, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final DepositAmount place = placelist.get(position);

        holder.txtname1.setText(place.getUser_id()+"");
        holder.txtamount.setText(place.getAmount()+"");




    }

    public void setData(ArrayList<DepositAmount> lst) {
        this.placelist = lst;
    }

    @Override
    public int getItemCount() {
        return placelist.size();
    }

    public ArrayList<DepositAmount> getData() {
        return this.placelist;
    }


    public static double toRad(double value) {
        return value * Math.PI / 180;
    }

    public interface onSelectedPlaceListener {
        void onClick(DepositAmount place);
    }
}