package com.example.mealmanagement.dao;


import com.example.mealmanagement.model.DailyMeal;
import com.example.mealmanagement.model.UserInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Hafiz_Hp on 12/18/2015.
 */
public interface IDailyMealDao {

    ArrayList<DailyMeal> GetAppdataFromJSONObject(JSONObject json) throws Exception;
    ArrayList<DailyMeal> GetAppdataFromJSONArray(JSONArray json) throws Exception;



}
