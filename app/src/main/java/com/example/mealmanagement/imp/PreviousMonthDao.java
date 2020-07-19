package com.example.mealmanagement.imp;

import android.content.Context;

import com.example.mealmanagement.dao.IPreviousMonthDao;
import com.example.mealmanagement.model.PreviousMonth;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Hafiz_Hp on 12/18/2015.
 */
public class PreviousMonthDao implements IPreviousMonthDao {

    public PreviousMonthDao(Context activity) {


    }


    @Override
    public ArrayList<PreviousMonth> GetAppdataFromJSONObject(JSONObject json) throws Exception {

        ArrayList<PreviousMonth> AppDataArrayList = new ArrayList<>();
        //JSONArray jsonArray = null;
        PreviousMonth appData = null;
        JSONObject jsonObject = null;
        try {
            JSONArray jsonArray = json.getJSONArray("data");

                jsonArray = json.getJSONArray("data");

                if (jsonArray != null && !jsonArray.equals("")) {

                    for (int k = 0; k < jsonArray.length(); k++) {

                        jsonObject = jsonArray.getJSONObject(k);
                        appData = new PreviousMonth();


                        try {
                            appData.setId(jsonObject.getInt("id"));
                        } catch (Exception ex) {
                        }
                        try {
                            appData.setUser_id(jsonObject.getInt("user_id"));
                        } catch (Exception ex) {
                        }
                        try {
                            appData.setMeal_rate(jsonObject.getString("meal_rate"));
                        } catch (Exception ex) {
                        }

                        try {
                            appData.setTotal_deposit(jsonObject.getInt("total_deposit"));
                        } catch (Exception ex) {
                        }
                        try {
                            appData.setTotal_cost(jsonObject.getInt("total_cost"));
                        } catch (Exception ex) {
                        }

                        try {
                            appData.setExtra_money(jsonObject.getInt("extra_money"));
                        } catch (Exception ex) {
                        }
                        try {
                            appData.setGiven_money(jsonObject.getInt("given_money"));
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
