package com.example.mealmanagement.imp;

import android.content.Context;

import com.example.mealmanagement.dao.IDailyCostDao;
import com.example.mealmanagement.model.DailyCost;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Hafiz_Hp on 12/18/2015.
 */
public class DailyCostDao implements IDailyCostDao {

    public DailyCostDao(Context activity) {


    }


    @Override
    public ArrayList<DailyCost> GetAppdataFromJSONObject(JSONObject json) throws Exception {

        ArrayList<DailyCost> AppDataArrayList = new ArrayList<>();
        //JSONArray jsonArray = null;
        DailyCost appData = null;
        JSONObject jsonObject = null;
        try {
            JSONArray jsonArray = json.getJSONArray("data");

                jsonArray = json.getJSONArray("data");

                if (jsonArray != null && !jsonArray.equals("")) {

                    for (int k = 0; k < jsonArray.length(); k++) {

                        jsonObject = jsonArray.getJSONObject(k);
                        appData = new DailyCost();


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
                            appData.setCost(jsonObject.getInt("cost"));
                        } catch (Exception ex) {
                        }

                        try {
                            appData.setYr_month(jsonObject.getString("yr_month"));
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
