package com.example.mealmanagement.imp;

import android.content.Context;

import com.example.mealmanagement.dao.IDailyMealDao;
import com.example.mealmanagement.model.DailyMeal;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Hafiz_Hp on 12/18/2015.
 */
public class DailyMealDao implements IDailyMealDao {

    public DailyMealDao(Context activity) {


    }


    @Override
    public ArrayList<DailyMeal> GetAppdataFromJSONObject(JSONObject json) throws Exception {

        ArrayList<DailyMeal> AppDataArrayList = new ArrayList<>();
        //JSONArray jsonArray = null;
        DailyMeal appData = null;
        JSONObject jsonObject = null;
        try {
            JSONArray jsonArray = json.getJSONArray("data");

                jsonArray = json.getJSONArray("data");

                if (jsonArray != null && !jsonArray.equals("")) {

                    for (int k = 0; k < jsonArray.length(); k++) {

                        jsonObject = jsonArray.getJSONObject(k);
                        appData = new DailyMeal();


                        try {
                            appData.setId(jsonObject.getInt("id"));
                        } catch (Exception ex) {
                        }
                        try {
                            appData.setUser_id(jsonObject.getInt("user_id"));
                        } catch (Exception ex) {
                        }
                        try {
                            appData.setBreakfast(jsonObject.getInt("breakfast"));
                        } catch (Exception ex) {
                        }

                        try {
                            appData.setLunch(jsonObject.getInt("lunch"));
                        } catch (Exception ex) {
                        }

                        try {
                            appData.setDinner(jsonObject.getInt("dinner"));
                        } catch (Exception ex) {
                        }

                        try {
                            appData.setCreation_date(jsonObject.getString("creation_date"));
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
