package com.example.mealmanagement.imp;

import android.content.Context;

import com.example.mealmanagement.dao.IDailyCostDao;
import com.example.mealmanagement.dao.IDepositAmount;
import com.example.mealmanagement.model.DailyCost;
import com.example.mealmanagement.model.DepositAmount;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Hafiz_Hp on 12/18/2015.
 */
public class DepositAmountDao implements IDepositAmount {

    public DepositAmountDao(Context activity) {


    }


    @Override
    public ArrayList<DepositAmount> GetAppdataFromJSONObject(JSONObject json) throws Exception {

        ArrayList<DepositAmount> AppDataArrayList = new ArrayList<>();
        //JSONArray jsonArray = null;
        DepositAmount appData = null;
        JSONObject jsonObject = null;
        try {
            JSONArray jsonArray = json.getJSONArray("data");

                jsonArray = json.getJSONArray("data");

                if (jsonArray != null && !jsonArray.equals("")) {

                    for (int k = 0; k < jsonArray.length(); k++) {

                        jsonObject = jsonArray.getJSONObject(k);
                        appData = new DepositAmount();


                        try {
                            appData.setId(jsonObject.getInt("id"));
                        } catch (Exception ex) {
                        }
                        try {
                            appData.setUser_id(jsonObject.getInt("user_id"));
                        } catch (Exception ex) {
                        }
                        try {
                            appData.setAmount(jsonObject.getInt("amount"));
                        } catch (Exception ex) {
                        }

                        try {
                            appData.setCreation_date(jsonObject.getString("creation_date"));
                        } catch (Exception ex) {
                        }

                        try {
                            appData.setYr_month(jsonObject.getString("yr_month"));
                        } catch (Exception ex) {
                        }

                        try {
                            appData.setName(jsonObject.getString("name"));
                        } catch (Exception ex) {
                        }


                        AppDataArrayList.add(appData);
                    }
                }



        } catch (Exception ex) {
            ex.getMessage();
        }

        return AppDataArrayList;
    }

    @Override
    public ArrayList<DepositAmount> GetAppdataFromJSONArray(JSONArray json) throws Exception {

        ArrayList<DepositAmount> AppDataArrayList = new ArrayList<>();
        //JSONArray jsonArray = null;
        DepositAmount appData = null;
        JSONObject jsonObject = null;
        try {

            if (json != null && !json.equals("")) {

                for (int k = 0; k < json.length(); k++) {

                    jsonObject = json.getJSONObject(k);
                    appData = new DepositAmount();


                    try {
                        appData.setId(jsonObject.getInt("id"));
                    } catch (Exception ex) {
                    }
                    try {
                        appData.setUser_id(jsonObject.getInt("user_id"));
                    } catch (Exception ex) {
                    }
                    try {
                        appData.setAmount(jsonObject.getInt("amount"));
                    } catch (Exception ex) {
                    }

                    try {
                        appData.setCreation_date(jsonObject.getString("creation_date"));
                    } catch (Exception ex) {
                    }

                    try {
                        appData.setYr_month(jsonObject.getString("yr_month"));
                    } catch (Exception ex) {
                    }

                    try {
                        appData.setName(jsonObject.getString("name"));
                    } catch (Exception ex) {
                    }


                    AppDataArrayList.add(appData);
                }
            }



        } catch (Exception ex) {
            ex.getMessage();
        }

        return AppDataArrayList;
    }

}
