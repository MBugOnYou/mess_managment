package com.example.mealmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmanagement.R;
import com.example.mealmanagement.model.UserInfo;

import java.util.ArrayList;


public class AddOrRemoveMemberAdapter extends RecyclerView.Adapter<AddOrRemoveMemberAdapter.MyViewHolder> {

    private ArrayList<UserInfo> userInfoArrayList;
    private onSelectedPlaceListener m_onSelectedPlaceListener;
    Context context;
    String YoutubeAPiKey;
    int isAddmember = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtname;
        Button btnaddOrRemove;


        public MyViewHolder(View view) {
            super(view);
            txtname = (TextView) view.findViewById(R.id.txtname);
            btnaddOrRemove = (Button) view.findViewById(R.id.btnaddOrRemove);

        }
    }


    public AddOrRemoveMemberAdapter(int isAddmember, ArrayList<UserInfo> userInfoArrayList, Context context, onSelectedPlaceListener m_onSelectedPlaceListener) {
        this.userInfoArrayList = userInfoArrayList;
        this.context = context;
        this.m_onSelectedPlaceListener = m_onSelectedPlaceListener;
        this.isAddmember=isAddmember;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_addorremovemember, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final UserInfo userInfo = userInfoArrayList.get(position);

        holder.txtname.setText(userInfo.getName());
       // holder.thum.setTag(place);
        holder.txtname.setTag(userInfo);

        if(isAddmember==1){
            holder.btnaddOrRemove.setText("Add");
        }else{

            holder.btnaddOrRemove.setText("Remove");
        }


       // holder.itemView.setTag(place);
        holder.btnaddOrRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo place = (UserInfo) holder.txtname.getTag();
                m_onSelectedPlaceListener.onClick(place);
            }
        });




    }

    public void setData(ArrayList<UserInfo> lst) {
        this.userInfoArrayList = lst;
    }

    @Override
    public int getItemCount() {
        return userInfoArrayList.size();
    }

    public ArrayList<UserInfo> getData() {
        return this.userInfoArrayList;
    }


    public static double toRad(double value) {
        return value * Math.PI / 180;
    }

    public interface onSelectedPlaceListener {
        void onClick(UserInfo place);
    }
}