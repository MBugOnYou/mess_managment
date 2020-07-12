package com.example.mealmanagement.imp;

import android.content.Context;

import com.example.mealmanagement.dao.IMonthlyEstimateDao;
import com.example.mealmanagement.model.MonthlyEstimate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Hafiz_Hp on 12/18/2015.
 */
public class MonthlyEstimateDao implements IMonthlyEstimateDao {

    public MonthlyEstimateDao(Context activity) {


    }


    @Override
    public ArrayList<MonthlyEstimate> GetAppdataFromJSONObject(JSONObject json) throws Exception {

        ArrayList<MonthlyEstimate> AppDataArrayList = new ArrayList<>();
        //JSONArray jsonArray = null;
        MonthlyEstimate appData = null;
        JSONObject jsonObject = null;
        try {
            JSONArray jsonArray = json.getJSONArray("data");

                jsonArray = json.getJSONArray("data");

                if (jsonArray != null && !jsonArray.equals("")) {

                    for (int k = 0; k < jsonArray.length(); k++) {

                        jsonObject = jsonArray.getJSONObject(k);
                        appData = new MonthlyEstimate();


                        try {
                            appData.setId(jsonObject.getInt("id"));
                        } catch (Exception ex) {
                        }
                        try {
                            appData.setUser_id(jsonObject.getInt("user_id"));
                        } catch (Exception ex) {
                        }
                        try {
                            appData.setDate(jsonObject.getString("date"));
                        } catch (Exception ex) {
                        }
                        try {
                            appData.setMeal(jsonObject.getInt("meal"));
                        } catch (Exception ex) {
                        }
                        try {
                            appData.setDeposita(jsonObject.getInt("deposita"));
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
